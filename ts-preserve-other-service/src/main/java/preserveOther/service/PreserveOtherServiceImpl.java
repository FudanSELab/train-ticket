package preserveOther.service;

import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import preserveOther.entity.*;
import preserveOther.mq.RabbitSend;

import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Service
public class PreserveOtherServiceImpl implements PreserveOtherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitSend sendService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PreserveOtherServiceImpl.class);

    @Override
    public Response preserve(OrderTicketsInfo oti, HttpHeaders httpHeaders) {

        PreserveOtherServiceImpl.LOGGER.info("[Verify Login] Success");
        //1.detect ticket scalper
        PreserveOtherServiceImpl.LOGGER.info("[Step 1] Check Security");

        Response result = checkSecurity(oti.getAccountId(), httpHeaders);

        if (result.getStatus() == 0) {
            PreserveOtherServiceImpl.LOGGER.error("[Step 1] Check Security Fail, AccountId: {}",oti.getAccountId());
            return new Response<>(0, result.getMsg(), null);
        }
        PreserveOtherServiceImpl.LOGGER.info("[Step 1] Check Security Complete. ");
        //2.Querying contact information -- modification, mediated by the underlying information micro service
        PreserveOtherServiceImpl.LOGGER.info("[Step 2] Find contacts");

        PreserveOtherServiceImpl.LOGGER.info("[Step 2] Contacts Id: {}", oti.getContactsId());

        Response<Contacts> gcr = getContactsById(oti.getContactsId(), httpHeaders);
        if (gcr.getStatus() == 0) {
            PreserveOtherServiceImpl.LOGGER.error("[Get Contacts] Fail,ContactsId: {},message: {}",oti.getContactsId(),gcr.getMsg());
            return new Response<>(0, gcr.getMsg(), null);
        }

        PreserveOtherServiceImpl.LOGGER.info("[Step 2] Complete");
        //3.Check the info of train and the number of remaining tickets
        PreserveOtherServiceImpl.LOGGER.info("[Step 3] Check tickets num");
        TripAllDetailInfo gtdi = new TripAllDetailInfo();

        gtdi.setFrom(oti.getFrom());
        gtdi.setTo(oti.getTo());

        gtdi.setTravelDate(oti.getDate());
        gtdi.setTripId(oti.getTripId());
        PreserveOtherServiceImpl.LOGGER.info("[Step 3] TripId: {}", oti.getTripId());
        Response<TripAllDetail> response = getTripAllDetailInformation(gtdi, httpHeaders);
        TripAllDetail gtdr = response.getData();
        LOGGER.info("TripAllDetail : " + gtdr.toString());
        if (response.getStatus() == 0) {
            PreserveOtherServiceImpl.LOGGER.error("[Search For Trip Detail Information] error, TripId: {}, message: {}", gtdi.getTripId(), response.getMsg());
            return new Response<>(0, response.getMsg(), null);
        } else {
            TripResponse tripResponse = gtdr.getTripResponse();
            LOGGER.info("TripResponse : " + tripResponse.toString());
            if (oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
                if (tripResponse.getConfortClass() == 0) {
                    PreserveOtherServiceImpl.LOGGER.warn("[Check seat is enough], TripId: {}",oti.getTripId());
                    return new Response<>(0, "Seat Not Enough", null);
                }
            } else {
                if (tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode() && tripResponse.getConfortClass() == 0) {
                    PreserveOtherServiceImpl.LOGGER.warn("[Check seat is Not enough], TripId: {}",oti.getTripId());
                    return new Response<>(0, "Check Seat Not Enough", null);
                }
            }
        }
        Trip trip = gtdr.getTrip();
        PreserveOtherServiceImpl.LOGGER.info("[Step 3] Tickets Enough");
        //4.send the order request and set the order information
        PreserveOtherServiceImpl.LOGGER.info("[Step 4] Do Order");
        Contacts contacts = gcr.getData();
        Order order = new Order();
        UUID orderId = UUID.randomUUID();
        order.setId(orderId);
        order.setTrainNumber(oti.getTripId());
        order.setAccountId(UUID.fromString(oti.getAccountId()));

        String fromStationId = queryForStationId(oti.getFrom(), httpHeaders);
        String toStationId = queryForStationId(oti.getTo(), httpHeaders);

        order.setFrom(fromStationId);
        order.setTo(toStationId);
        order.setBoughtDate(new Date());
        order.setStatus(OrderStatus.NOTPAID.getCode());
        order.setContactsDocumentNumber(contacts.getDocumentNumber());
        order.setContactsName(contacts.getName());
        order.setDocumentType(contacts.getDocumentType());


        Travel query = new Travel();
        query.setTrip(trip);
        query.setStartingPlace(oti.getFrom());
        query.setEndPlace(oti.getTo());
        query.setDepartureTime(new Date());


        HttpEntity requestEntity = new HttpEntity(query, httpHeaders);
        ResponseEntity<Response<TravelResult>> re = restTemplate.exchange(
                "http://ts-ticketinfo-service:15681/api/v1/ticketinfoservice/ticketinfo",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<TravelResult>>() {
                });
        TravelResult resultForTravel = re.getBody().getData();

        order.setSeatClass(oti.getSeatType());
        PreserveOtherServiceImpl.LOGGER.info("[Order] Order Travel Date: {}", oti.getDate().toString());
        order.setTravelDate(oti.getDate());
        order.setTravelTime(gtdr.getTripResponse().getStartingTime());

        //Dispatch the seat
        if (oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            Ticket ticket =
                    dipatchSeat(oti.getDate(),
                            order.getTrainNumber(), fromStationId, toStationId,
                            SeatClass.FIRSTCLASS.getCode(), httpHeaders);
            order.setSeatClass(SeatClass.FIRSTCLASS.getCode());
            order.setSeatNumber("" + ticket.getSeatNo());
            order.setPrice(resultForTravel.getPrices().get("confortClass"));
        } else {
            Ticket ticket =
                    dipatchSeat(oti.getDate(),
                            order.getTrainNumber(), fromStationId, toStationId,
                            SeatClass.SECONDCLASS.getCode(), httpHeaders);
            order.setSeatClass(SeatClass.SECONDCLASS.getCode());
            order.setSeatNumber("" + ticket.getSeatNo());

            order.setPrice(resultForTravel.getPrices().get("economyClass"));
        }
        PreserveOtherServiceImpl.LOGGER.info("[Order Price] Price is: {}", order.getPrice());

        Response<Order> cor = createOrder(order, httpHeaders);
        if (cor.getStatus() == 0) {
            PreserveOtherServiceImpl.LOGGER.error("[Create Order Fail] Create Order Fail. OrderId: {},  Reason: {}", order.getId(), cor.getMsg());
            return new Response<>(0, cor.getMsg(), null);
        }

        PreserveOtherServiceImpl.LOGGER.info("[Step 4] Do Order Complete");
        Response returnResponse = new Response<>(1, "Success.", cor.getMsg());
        //5.Check insurance options
        if (oti.getAssurance() == 0) {
            PreserveOtherServiceImpl.LOGGER.info("[Step 5] Do not need to buy assurance");
        } else {
            Response<Assurance> addAssuranceResult = addAssuranceForOrder(
                    oti.getAssurance(), cor.getData().getId().toString(), httpHeaders);
            if (addAssuranceResult.getStatus() == 1) {
                PreserveOtherServiceImpl.LOGGER.info("[Step 5] Preserve Buy Assurance Success");
            } else {
                PreserveOtherServiceImpl.LOGGER.warn("[Step 5] Buy Assurance Fail, assurance: {}, OrderId: {}", oti.getAssurance(),cor.getData().getId());
                returnResponse.setMsg("Success.But Buy Assurance Fail.");
            }
        }

        //6.Increase the food order
        if (oti.getFoodType() != 0) {
            FoodOrder foodOrder = new FoodOrder();
            foodOrder.setOrderId(cor.getData().getId());
            foodOrder.setFoodType(oti.getFoodType());
            foodOrder.setFoodName(oti.getFoodName());
            foodOrder.setPrice(oti.getFoodPrice());
            if (oti.getFoodType() == 2) {
                foodOrder.setStationName(oti.getStationName());
                foodOrder.setStoreName(oti.getStoreName());
            }
            Response afor = createFoodOrder(foodOrder, httpHeaders);
            if (afor.getStatus() == 1) {
                PreserveOtherServiceImpl.LOGGER.info("[Step 6] Buy Food Success");
            } else {
                PreserveOtherServiceImpl.LOGGER.error("[Step 6] Buy Food Fail, OrderId: {}",cor.getData().getId());
                returnResponse.setMsg("Success.But Buy Food Fail.");
            }
        } else {
            PreserveOtherServiceImpl.LOGGER.info("[Step 6] Do not need to buy food");
        }

        //7.add consign
        if (null != oti.getConsigneeName() && !"".equals(oti.getConsigneeName())) {
            Consign consignRequest = new Consign();
            consignRequest.setOrderId(cor.getData().getId());
            consignRequest.setAccountId(cor.getData().getAccountId());
            consignRequest.setHandleDate(oti.getHandleDate());
            consignRequest.setTargetDate(cor.getData().getTravelDate().toString());
            consignRequest.setFrom(cor.getData().getFrom());
            consignRequest.setTo(cor.getData().getTo());
            consignRequest.setConsignee(oti.getConsigneeName());
            consignRequest.setPhone(oti.getConsigneePhone());
            consignRequest.setWeight(oti.getConsigneeWeight());
            consignRequest.setWithin(oti.isWithin());
            LOGGER.info("CONSIGN INFO : " + consignRequest.toString());
            Response icresult = createConsign(consignRequest, httpHeaders);
            if (icresult.getStatus() == 1) {
                PreserveOtherServiceImpl.LOGGER.info("[Step 7] Consign Success");
            } else {
                PreserveOtherServiceImpl.LOGGER.error("[Step 7] Preserve Consign Fail, OrderId: {}", cor.getData().getId());
                returnResponse.setMsg("Consign Fail.");
            }
        } else {
            PreserveOtherServiceImpl.LOGGER.info("[Step 7] Do not need to consign");
        }

        //8.send notification

        User getUser = getAccount(order.getAccountId().toString(), httpHeaders);

        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setDate(new Date().toString());

        notifyInfo.setEmail(getUser.getEmail());
        notifyInfo.setStartingPlace(order.getFrom());
        notifyInfo.setEndPlace(order.getTo());
        notifyInfo.setUsername(getUser.getUserName());
        notifyInfo.setSeatNumber(order.getSeatNumber());
        notifyInfo.setOrderNumber(order.getId().toString());
        notifyInfo.setPrice(order.getPrice());
        notifyInfo.setSeatClass(SeatClass.getNameByCode(order.getSeatClass()));
        notifyInfo.setStartingTime(order.getTravelTime().toString());

        // TODO: change to async message serivce
        // sendEmail(notifyInfo, httpHeaders);

        return returnResponse;
    }

    public Ticket dipatchSeat(Date date, String tripId, String startStationId, String endStataionId, int seatType, HttpHeaders httpHeaders) {
        Seat seatRequest = new Seat();
        seatRequest.setTravelDate(date);
        seatRequest.setTrainNumber(tripId);
        seatRequest.setStartStation(startStationId);
        seatRequest.setSeatType(seatType);
        seatRequest.setDestStation(endStataionId);

        HttpEntity requestEntityTicket = new HttpEntity(seatRequest, httpHeaders);
        ResponseEntity<Response<Ticket>> reTicket = restTemplate.exchange(
                "http://ts-seat-service:18898/api/v1/seatservice/seats",
                HttpMethod.POST,
                requestEntityTicket,
                new ParameterizedTypeReference<Response<Ticket>>() {
                });

        return reTicket.getBody().getData();
    }

    public boolean sendEmail(NotifyInfo notifyInfo, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Other Service][Send Email] send email to mq");

        try {
            String infoJson = JsonUtils.object2Json(notifyInfo);
            sendService.send(infoJson);
        } catch (Exception e) {
            PreserveOtherServiceImpl.LOGGER.error("[Preserve Other Service] send email to mq error, exception is:" + e);
            return false;
        }

        return true;
    }

    public User getAccount(String accountId, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Cancel Order Service][Get Order By Id]");

        HttpEntity requestEntitySendEmail = new HttpEntity(httpHeaders);
        ResponseEntity<Response<User>> getAccount = restTemplate.exchange(
                "http://ts-user-service:12342/api/v1/userservice/users/id/" + accountId,
                HttpMethod.GET,
                requestEntitySendEmail,
                new ParameterizedTypeReference<Response<User>>() {
                });
        Response<User> result = getAccount.getBody();
        return result.getData();


    }

    private Response<Assurance> addAssuranceForOrder(int assuranceType, String orderId, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Service][Add Assurance Type For Order]");
        HttpEntity requestAddAssuranceResult = new HttpEntity(httpHeaders);
        ResponseEntity<Response<Assurance>> reAddAssuranceResult = restTemplate.exchange(
                "http://ts-assurance-service:18888/api/v1/assuranceservice/assurances/" + assuranceType + "/" + orderId,
                HttpMethod.GET,
                requestAddAssuranceResult,
                new ParameterizedTypeReference<Response<Assurance>>() {
                });

        return reAddAssuranceResult.getBody();
    }


    private String queryForStationId(String stationName, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Other Service][Get Station By  Name]");


        HttpEntity requestQueryForStationId = new HttpEntity(httpHeaders);
        ResponseEntity<Response<String>> reQueryForStationId = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestQueryForStationId,
                new ParameterizedTypeReference<Response<String>>() {
                });
        return reQueryForStationId.getBody().getData();
    }

    private Response checkSecurity(String accountId, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Other Service][Check Account Security] Checking....");

        HttpEntity requestCheckResult = new HttpEntity(httpHeaders);
        ResponseEntity<Response> reCheckResult = restTemplate.exchange(
                "http://ts-security-service:11188/api/v1/securityservice/securityConfigs/" + accountId,
                HttpMethod.GET,
                requestCheckResult,
                Response.class);

        return reCheckResult.getBody();
    }


    private Response<TripAllDetail> getTripAllDetailInformation(TripAllDetailInfo gtdi, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Other Service][Get Trip All Detail Information] Getting....");

        HttpEntity requestGetTripAllDetailResult = new HttpEntity(gtdi, httpHeaders);
        ResponseEntity<Response<TripAllDetail>> reGetTripAllDetailResult = restTemplate.exchange(
                "http://ts-travel2-service:16346/api/v1/travel2service/trip_detail",
                HttpMethod.POST,
                requestGetTripAllDetailResult,
                new ParameterizedTypeReference<Response<TripAllDetail>>() {
                });

        return reGetTripAllDetailResult.getBody();
    }

    private Response<Contacts> getContactsById(String contactsId, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Other Service][Get Contacts By Id is] Getting....");

        HttpEntity requestGetContactsResult = new HttpEntity(httpHeaders);
        ResponseEntity<Response<Contacts>> reGetContactsResult = restTemplate.exchange(
                "http://ts-contacts-service:12347/api/v1/contactservice/contacts/" + contactsId,
                HttpMethod.GET,
                requestGetContactsResult,
                new ParameterizedTypeReference<Response<Contacts>>() {
                });

        return reGetContactsResult.getBody();
    }

    private Response<Order> createOrder(Order coi, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Other Service][Get Contacts By Id] Creating....");

        HttpEntity requestEntityCreateOrderResult = new HttpEntity(coi, httpHeaders);
        ResponseEntity<Response<Order>> reCreateOrderResult = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther",
                HttpMethod.POST,
                requestEntityCreateOrderResult,
                new ParameterizedTypeReference<Response<Order>>() {
                });


        return reCreateOrderResult.getBody();
    }

    private Response createFoodOrder(FoodOrder afi, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Service][Add Preserve food Order] Creating....");

        HttpEntity requestEntityAddFoodOrderResult = new HttpEntity(afi, httpHeaders);
        ResponseEntity<Response> reAddFoodOrderResult = restTemplate.exchange(
                "http://ts-food-service:18856/api/v1/foodservice/orders",
                HttpMethod.POST,
                requestEntityAddFoodOrderResult,
                Response.class);

        return reAddFoodOrderResult.getBody();
    }

    private Response createConsign(Consign cr, HttpHeaders httpHeaders) {
        PreserveOtherServiceImpl.LOGGER.info("[Preserve Service][Add Condign] Creating Consign...");

        HttpEntity requestEntityResultForTravel = new HttpEntity(cr, httpHeaders);
        ResponseEntity<Response> reResultForTravel = restTemplate.exchange(
                "http://ts-consign-service:16111/api/v1/consignservice/consigns",
                HttpMethod.POST,
                requestEntityResultForTravel,
                Response.class);


        return reResultForTravel.getBody();
    }
}
