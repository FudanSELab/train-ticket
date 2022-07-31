package preserve.service;

import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import edu.fudan.common.entity.*;
import preserve.mq.RabbitSend;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Service
public class PreserveServiceImpl implements PreserveService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitSend sendService;

    @Autowired
    private DiscoveryClient discoveryClient;


    private static final Logger LOGGER = LoggerFactory.getLogger(PreserveServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName; }

    @Override
    public Response preserve(OrderTicketsInfo oti, HttpHeaders headers) {
        //1.detect ticket scalper
        //PreserveServiceImpl.LOGGER.info("[Step 1] Check Security");

        Response result = checkSecurity(oti.getAccountId(), headers);
        if (result.getStatus() == 0) {
            PreserveServiceImpl.LOGGER.error("[preserve][Step 1][Check Security Fail][AccountId: {}]",oti.getAccountId());
            return new Response<>(0, result.getMsg(), null);
        }
        PreserveServiceImpl.LOGGER.info("[preserve][Step 1][Check Security Complete][AccountId: {}]",oti.getAccountId());
        //2.Querying contact information -- modification, mediated by the underlying information micro service
        //PreserveServiceImpl.LOGGER.info("[Step 2] Find contacts");
        //PreserveServiceImpl.LOGGER.info("[Step 2] Contacts Id: {}", oti.getContactsId());

        Response<Contacts> gcr = getContactsById(oti.getContactsId(), headers);
        if (gcr.getStatus() == 0) {
            PreserveServiceImpl.LOGGER.error("[preserve][Step 2][Find Contacts Fail][ContactsId: {},message: {}]",oti.getContactsId(),gcr.getMsg());
            return new Response<>(0, gcr.getMsg(), null);
        }
        PreserveServiceImpl.LOGGER.info("[preserve][Step 2][Find contacts Complete][ContactsId: {}]",oti.getContactsId());
        //3.Check the info of train and the number of remaining tickets
        //PreserveServiceImpl.LOGGER.info("[Step 3] Check tickets num");
        TripAllDetailInfo gtdi = new TripAllDetailInfo();

        gtdi.setFrom(oti.getFrom());
        gtdi.setTo(oti.getTo());

        gtdi.setTravelDate(oti.getDate());
        gtdi.setTripId(oti.getTripId());
        PreserveServiceImpl.LOGGER.info("[preserve][Step 3][Check tickets num][TripId: {}]", oti.getTripId());
        Response<TripAllDetail> response = getTripAllDetailInformation(gtdi, headers);
        TripAllDetail gtdr = response.getData();
        //LOGGER.info("TripAllDetail:" + gtdr.toString());
        if (response.getStatus() == 0) {
            PreserveServiceImpl.LOGGER.error("[preserve][Step 3][Check tickets num][Search For Trip Detail Information error][TripId: {}, message: {}]", gtdi.getTripId(), response.getMsg());
            return new Response<>(0, response.getMsg(), null);
        } else {
            TripResponse tripResponse = gtdr.getTripResponse();
            //LOGGER.info("TripResponse:" + tripResponse.toString());
            if (oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
                if (tripResponse.getConfortClass() == 0) {
                    PreserveServiceImpl.LOGGER.warn("[preserve][Step 3][Check seat][Check seat is enough][TripId: {}]",oti.getTripId());
                    return new Response<>(0, "Seat Not Enough", null);
                }
            } else {
                if (tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode() && tripResponse.getConfortClass() == 0) {
                    PreserveServiceImpl.LOGGER.warn("[preserve][Step 3][Check seat][Check seat is Not enough][TripId: {}]",oti.getTripId());
                    return new Response<>(0, "Seat Not Enough", null);
                }
            }
        }
        Trip trip = gtdr.getTrip();
        PreserveServiceImpl.LOGGER.info("[preserve][Step 3][Check tickets num][Tickets Enough]");
        //4.send the order request and set the order information
        //PreserveServiceImpl.LOGGER.info("[Step 4] Do Order");
        Contacts contacts = gcr.getData();
        Order order = new Order();
        UUID orderId = UUID.randomUUID();
        order.setId(orderId.toString());
        order.setTrainNumber(oti.getTripId());
        order.setAccountId(oti.getAccountId());

        String fromStationName = oti.getFrom();
        String toStationName = oti.getTo();

        order.setFrom(fromStationName);
        order.setTo(toStationName);
        order.setBoughtDate(StringUtils.Date2String(new Date()));
        order.setStatus(OrderStatus.NOTPAID.getCode());
        order.setContactsDocumentNumber(contacts.getDocumentNumber());
        order.setContactsName(contacts.getName());
        order.setDocumentType(contacts.getDocumentType());

        Travel query = new Travel();
        query.setTrip(trip);
        query.setStartPlace(oti.getFrom());
        query.setEndPlace(oti.getTo());
        query.setDepartureTime(StringUtils.Date2String(new Date()));

        HttpEntity requestEntity = new HttpEntity(query, headers);
        String basic_service_url = getServiceUrl("ts-basic-service");
        ResponseEntity<Response<TravelResult>> re = restTemplate.exchange(
                basic_service_url + "/api/v1/basicservice/basic/travel",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<TravelResult>>() {
                });
        if(re.getBody().getStatus() == 0){
            PreserveServiceImpl.LOGGER.info("[Preserve 3][Get basic travel response status is 0][response is: {}]", re.getBody());
            return new Response<>(0, re.getBody().getMsg(), null);
        }
        TravelResult resultForTravel = re.getBody().getData();

        order.setSeatClass(oti.getSeatType());
        PreserveServiceImpl.LOGGER.info("[preserve][Step 4][Do Order][Travel Date][Date is: {}]", oti.getDate().toString());
        order.setTravelDate(oti.getDate());
        order.setTravelTime(gtdr.getTripResponse().getStartTime());

        //Dispatch the seat
        List<String> stationList = resultForTravel.getRoute().getStations();
        if (oti.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            int firstClassTotalNum = resultForTravel.getTrainType().getConfortClass();
            Ticket ticket =
                    dipatchSeat(oti.getDate(),
                            order.getTrainNumber(), fromStationName, toStationName,
                            SeatClass.FIRSTCLASS.getCode(), firstClassTotalNum, stationList, headers);
            order.setSeatNumber("" + ticket.getSeatNo());
            order.setSeatClass(SeatClass.FIRSTCLASS.getCode());
            order.setPrice(resultForTravel.getPrices().get("confortClass"));
        } else {
            int secondClassTotalNum = resultForTravel.getTrainType().getEconomyClass();
            Ticket ticket =
                    dipatchSeat(oti.getDate(),
                            order.getTrainNumber(), fromStationName, toStationName,
                            SeatClass.SECONDCLASS.getCode(), secondClassTotalNum, stationList, headers);
            order.setSeatClass(SeatClass.SECONDCLASS.getCode());
            order.setSeatNumber("" + ticket.getSeatNo());
            order.setPrice(resultForTravel.getPrices().get("economyClass"));
        }

        PreserveServiceImpl.LOGGER.info("[preserve][Step 4][Do Order][Order Price][Price is: {}]", order.getPrice());

        Response<Order> cor = createOrder(order, headers);
        if (cor.getStatus() == 0) {
            PreserveServiceImpl.LOGGER.error("[preserve][Step 4][Do Order][Create Order Fail][OrderId: {},  Reason: {}]", order.getId(), cor.getMsg());
            return new Response<>(0, cor.getMsg(), null);
        }
        PreserveServiceImpl.LOGGER.info("[preserve][Step 4][Do Order][Do Order Complete]");

        Response returnResponse = new Response<>(1, "Success.", cor.getMsg());
        //5.Check insurance options
        if (oti.getAssurance() == 0) {
            PreserveServiceImpl.LOGGER.info("[preserve][Step 5][Buy Assurance][Do not need to buy assurance]");
        } else {
            Response addAssuranceResult = addAssuranceForOrder(
                    oti.getAssurance(), cor.getData().getId().toString(), headers);
            if (addAssuranceResult.getStatus() == 1) {
                PreserveServiceImpl.LOGGER.info("[preserve][Step 5][Buy Assurance][Preserve Buy Assurance Success]");
            } else {
                PreserveServiceImpl.LOGGER.warn("[preserve][Step 5][Buy Assurance][Buy Assurance Fail][assurance: {}, OrderId: {}]", oti.getAssurance(),cor.getData().getId());
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
                //PreserveServiceImpl.LOGGER.info("foodstore= {}   {}   {}", foodOrder.getFoodType(), foodOrder.getStationName(), foodOrder.getStoreName());
            }
            Response afor = createFoodOrder(foodOrder, headers);
            if (afor.getStatus() == 1) {
                PreserveServiceImpl.LOGGER.info("[preserve][Step 6][Buy Food][Buy Food Success]");
            } else {
                PreserveServiceImpl.LOGGER.error("[preserve][Step 6][Buy Food][Buy Food Fail][OrderId: {}]",cor.getData().getId());
                returnResponse.setMsg("Success.But Buy Food Fail.");
            }
        } else {
            PreserveServiceImpl.LOGGER.info("[preserve][Step 6][Buy Food][Do not need to buy food]");
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
            LOGGER.info("CONSIGN INFO : " +consignRequest.toString());
            Response icresult = createConsign(consignRequest, headers);
            if (icresult.getStatus() == 1) {
                PreserveServiceImpl.LOGGER.info("[preserve][Step 7][Add Consign][Consign Success]");
            } else {
                PreserveServiceImpl.LOGGER.error("[preserve][Step 7][Add Consign][Preserve Consign Fail][OrderId: {}]", cor.getData().getId());
                returnResponse.setMsg("Consign Fail.");
            }
        } else {
            PreserveServiceImpl.LOGGER.info("[preserve][Step 7][Add Consign][Do not need to consign]");
        }

        //8.send notification

        User getUser = getAccount(order.getAccountId().toString(), headers);

        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setDate(new Date().toString());

        notifyInfo.setEmail(getUser.getEmail());
        notifyInfo.setStartPlace(order.getFrom());
        notifyInfo.setEndPlace(order.getTo());
        notifyInfo.setUsername(getUser.getUserName());
        notifyInfo.setSeatNumber(order.getSeatNumber());
        notifyInfo.setOrderNumber(order.getId().toString());
        notifyInfo.setPrice(order.getPrice());
        notifyInfo.setSeatClass(SeatClass.getNameByCode(order.getSeatClass()));
        notifyInfo.setStartTime(order.getTravelTime().toString());

        // TODO: change to async message serivce
        // sendEmail(notifyInfo, headers);

        return returnResponse;
    }

    public Ticket dipatchSeat(String date, String tripId, String startStation, String endStataion, int seatType, int totalNum, List<String> stationList, HttpHeaders httpHeaders) {
        Seat seatRequest = new Seat();
        seatRequest.setTravelDate(date);
        seatRequest.setTrainNumber(tripId);
        seatRequest.setStartStation(startStation);
        seatRequest.setDestStation(endStataion);
        seatRequest.setSeatType(seatType);
        seatRequest.setTotalNum(totalNum);
        seatRequest.setStations(stationList);

        HttpEntity requestEntityTicket = new HttpEntity(seatRequest, httpHeaders);
        String seat_service_url = getServiceUrl("ts-seat-service");
        ResponseEntity<Response<Ticket>> reTicket = restTemplate.exchange(
                seat_service_url + "/api/v1/seatservice/seats",
                HttpMethod.POST,
                requestEntityTicket,
                new ParameterizedTypeReference<Response<Ticket>>() {
                });

        return reTicket.getBody().getData();
    }

    public boolean sendEmail(NotifyInfo notifyInfo, HttpHeaders httpHeaders) {
        try {
            String infoJson = JsonUtils.object2Json(notifyInfo);
            sendService.send(infoJson);
            PreserveServiceImpl.LOGGER.info("[sendEmail][Send email to mq success]");
        } catch (Exception e) {
            PreserveServiceImpl.LOGGER.error("[sendEmail][Send email to mq error] exception is:" + e);
            return false;
        }

        return true;
    }

    public User getAccount(String accountId, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[getAccount][Cancel Order Service][Get Order By Id]");

        HttpEntity requestEntitySendEmail = new HttpEntity(httpHeaders);
        String user_service_url = getServiceUrl("ts-user-service");
        ResponseEntity<Response<User>> getAccount = restTemplate.exchange(
                user_service_url + "/api/v1/userservice/users/id/" + accountId,
                HttpMethod.GET,
                requestEntitySendEmail,
                new ParameterizedTypeReference<Response<User>>() {
                });
        Response<User> result = getAccount.getBody();
        return result.getData();
    }

    private Response addAssuranceForOrder(int assuranceType, String orderId, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[addAssuranceForOrder][Preserve Service][Add Assurance Type For Order]");
        HttpEntity requestAddAssuranceResult = new HttpEntity(httpHeaders);
        String assurance_service_url = getServiceUrl("ts-assurance-service");
        ResponseEntity<Response> reAddAssuranceResult = restTemplate.exchange(
                assurance_service_url + "/api/v1/assuranceservice/assurances/" + assuranceType + "/" + orderId,
                HttpMethod.GET,
                requestAddAssuranceResult,
                Response.class);

        return reAddAssuranceResult.getBody();
    }

    private String queryForStationId(String stationName, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[queryForStationId][Preserve Other Service][Get Station By  Name]");


        HttpEntity requestQueryForStationId = new HttpEntity(httpHeaders);
        String station_service_url = getServiceUrl("ts-station-service");
        ResponseEntity<Response<String>> reQueryForStationId = restTemplate.exchange(
                station_service_url + "/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestQueryForStationId,
                new ParameterizedTypeReference<Response<String>>() {
                });

        return reQueryForStationId.getBody().getData();
    }

    private Response checkSecurity(String accountId, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[checkSecurity][Preserve Other Service][Check Account Security]");

        HttpEntity requestCheckResult = new HttpEntity(httpHeaders);
        String security_service_url = getServiceUrl("ts-security-service");
        ResponseEntity<Response> reCheckResult = restTemplate.exchange(
                security_service_url + "/api/v1/securityservice/securityConfigs/" + accountId,
                HttpMethod.GET,
                requestCheckResult,
                Response.class);

        return reCheckResult.getBody();
    }


    private Response<TripAllDetail> getTripAllDetailInformation(TripAllDetailInfo gtdi, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[getTripAllDetailInformation][Preserve Other Service][Get Trip All Detail Information]");

        HttpEntity requestGetTripAllDetailResult = new HttpEntity(gtdi, httpHeaders);
        String travel_service_url = getServiceUrl("ts-travel-service");
        ResponseEntity<Response<TripAllDetail>> reGetTripAllDetailResult = restTemplate.exchange(
                travel_service_url + "/api/v1/travelservice/trip_detail",
                HttpMethod.POST,
                requestGetTripAllDetailResult,
                new ParameterizedTypeReference<Response<TripAllDetail>>() {
                });

        return reGetTripAllDetailResult.getBody();
    }


    private Response<Contacts> getContactsById(String contactsId, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[getContactsById][Preserve Other Service][Get Contacts By Id is]");

        HttpEntity requestGetContactsResult = new HttpEntity(httpHeaders);
        String contacts_service_url = getServiceUrl("ts-contacts-service");
        ResponseEntity<Response<Contacts>> reGetContactsResult = restTemplate.exchange(
                contacts_service_url + "/api/v1/contactservice/contacts/" + contactsId,
                HttpMethod.GET,
                requestGetContactsResult,
                new ParameterizedTypeReference<Response<Contacts>>() {
                });

        return reGetContactsResult.getBody();
    }

    private Response createOrder(Order coi, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[createOrder][Preserve Other Service][Get Contacts By Id]");

        HttpEntity requestEntityCreateOrderResult = new HttpEntity(coi, httpHeaders);
        String order_service_url = getServiceUrl("ts-order-service");
        ResponseEntity<Response<Order>> reCreateOrderResult = restTemplate.exchange(
                order_service_url + "/api/v1/orderservice/order",
                HttpMethod.POST,
                requestEntityCreateOrderResult,
                new ParameterizedTypeReference<Response<Order>>() {
                });

        return reCreateOrderResult.getBody();
    }

    private Response createFoodOrder(FoodOrder afi, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[createFoodOrder][Preserve Service][Add Preserve food Order]");

        HttpEntity requestEntityAddFoodOrderResult = new HttpEntity(afi, httpHeaders);
        String food_service_url = getServiceUrl("ts-food-service");
        ResponseEntity<Response> reAddFoodOrderResult = restTemplate.exchange(
                food_service_url + "/api/v1/foodservice/orders",
                HttpMethod.POST,
                requestEntityAddFoodOrderResult,
                Response.class);

        return reAddFoodOrderResult.getBody();
    }

    private Response createConsign(Consign cr, HttpHeaders httpHeaders) {
        PreserveServiceImpl.LOGGER.info("[createConsign][Preserve Service][Add Condign");

        HttpEntity requestEntityResultForTravel = new HttpEntity(cr, httpHeaders);
        String consign_service_url = getServiceUrl("ts-consign-service");
        ResponseEntity<Response> reResultForTravel = restTemplate.exchange(
                consign_service_url + "/api/v1/consignservice/consigns",
                HttpMethod.POST,
                requestEntityResultForTravel,
                Response.class);
        return reResultForTravel.getBody();
    }

}
