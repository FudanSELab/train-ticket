package preserveother.service;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import preserveother.entity.*;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fdu
 */
@Service
@Slf4j
public class PreserveOtherServiceImpl implements PreserveOtherService {
    private static Logger logger = Logger.getLogger(PreserveOtherServiceImpl.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response preserve(OrderTicketsInfo oti, HttpHeaders httpHeaders) {

        logger.info("[Preserve Other Service][Verify Login] Success");
        //1.黄牛检测
        logger.info("[Preserve Service] [Step 1] Check Security");

        Response result = checkSecurity(oti.getAccountId(), httpHeaders);

        if (result.getStatus() == 0) {
            logger.info("[Preserve Service] [Step 1] Check Security Fail. Return soon.");
            return new Response<>(0, result.getMsg(), null);
        }
        logger.info("[Preserve Service] [Step 1] Check Security Complete.");
        //2.查询联系人信息 -- 修改，通过基础信息微服务作为中介
        logger.info("[Preserve Other Service] [Step 2] Find contacts");

        logger.log(Level.INFO, () -> "[Preserve Other Service] [Step 2] Contacts Id: " + oti.getContactsId());

        Response<Contacts> gcr = getContactsById(oti.getContactsId(), httpHeaders);
        if (gcr.getStatus() == 0) {
            logger.log(Level.INFO, () -> "[Preserve Service][Get Contacts] Fail." + gcr.getMsg());
            return new Response<>(0, gcr.getMsg(), null);
        }

        logger.info("[Preserve Other Service][Step 2] Complete");
        //3.查询座位余票信息和车次的详情
        logger.info("[Preserve Other Service] [Step 3] Check tickets num");
        TripAllDetailInfo gtdi = new TripAllDetailInfo();

        gtdi.setFrom(oti.getFrom());
        gtdi.setTo(oti.getTo());

        gtdi.setTravelDate(oti.getDate());
        gtdi.setTripId(oti.getTripId());
        logger.log(Level.INFO, () -> "[Preserve Other Service] [Step 3] TripId:" + oti.getTripId());
        Response<TripAllDetail> response = getTripAllDetailInformation(gtdi, httpHeaders);
        TripAllDetail gtdr = response.getData();
        log.info("TripAllDetail : " + gtdr.toString());
        if (response.getStatus() == 0) {
            logger.log(Level.INFO, () -> "[Preserve Service][Search For Trip Detail Information] " + response.getMsg());
            return new Response<>(0, response.getMsg(), null);
        } else {
            TripResponse tripResponse = gtdr.getTripResponse();
            logger.log(Level.INFO, () -> "TripResponse : " + tripResponse.toString());
            if (oti.getSeatType() == SeatClass.FIRSTCLASS.getCode() && tripResponse.getConfortClass() == 0) {
                logger.info("[Preserve Service][Check seat is enough]");
                return new Response<>(0, "Seat Not Enough", null);
            } else {
                if (tripResponse.getEconomyClass() == SeatClass.SECONDCLASS.getCode() && tripResponse.getConfortClass() == 0) {
                    logger.info("[Preserve Service][Check seat is Not enough] ");
                    return new Response<>(0, "Check Seat Not Enough", null);
                }
            }
        }
        Trip trip = gtdr.getTrip();
        logger.info("[Preserve Other Service] [Step 3] Tickets Enough");
        //4.下达订单请求 设置order的各个信息
        logger.info("[Preserve Other Service] [Step 4] Do Order");
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
        logger.log(Level.INFO, () -> "[Preserve Other Service][Order] Order Travel Date:" + oti.getDate().toString());
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
        logger.log(Level.INFO, () -> "[Preserve Other Service][Order Price] Price is: " + order.getPrice());

        Response<Order> cor = createOrder(order, httpHeaders);
        if (cor.getStatus() == 0) {
            logger.log(Level.INFO, () -> "[Preserve Other Service][Create Order Fail] Create Order Fail." +
                    "Reason:" + cor.getMsg());
            return new Response<>(0, cor.getMsg(), null);
        }

        logger.info("[Preserve Other Service] [Step 4] Do Order Complete");
        Response returnResponse = new Response<>(1, "Success.", cor.getMsg());
        //5.检查保险的选择
        if (oti.getAssurance() == 0) {
            logger.info("[Preserve Service][Step 5] Do not need to buy assurance");
        } else {
            Response<Assurance> addAssuranceResult = addAssuranceForOrder(
                    oti.getAssurance(), cor.getData().getId().toString(), httpHeaders);
            if (addAssuranceResult.getStatus() == 1) {
                logger.info("[Preserve Service][Step 5] Preserve Buy Assurance Success");
            } else {
                logger.info("[Preserve Service][Step 5] Buy Assurance Fail.");
                returnResponse.setMsg("Success.But Buy Assurance Fail.");
            }
        }

        //6.增加订餐
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
                logger.info("[Preserve Service][Step 6] Buy Food Success");
            } else {
                logger.info("[Preserve Service][Step 6] Buy Food Fail.");
                returnResponse.setMsg("Success.But Buy Food Fail.");
            }
        } else {
            logger.info("[Preserve Service][Step 6] Do not need to buy food");
        }

        //7.增加托运
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
            logger.log(Level.INFO, () -> "CONSIGN INFO : " + consignRequest.toString());
            Response icresult = createConsign(consignRequest, httpHeaders);
            if (icresult.getStatus() == 1) {
                logger.info("[Preserve Service][Step 7] Consign Success");
            } else {
                logger.info("[Preserve Service][Step 7] Preserve Consign Fail.");
                returnResponse.setMsg("Consign Fail.");
            }
        } else {
            logger.info("[Preserve Service][Step 7] Do not need to consign");
        }

        //8.发送notification
        logger.info("[Preserve Service]");

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

        sendEmail(notifyInfo, httpHeaders);

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

        logger.info("[Preserve Service][Send Email]");
        HttpEntity requestEntitySendEmail = new HttpEntity(notifyInfo, httpHeaders);
        ResponseEntity<Boolean> reSendEmail = restTemplate.exchange(
                "http://ts-notification-service:17853/api/v1/notifyservice/notification/order_cancel_success",
                HttpMethod.POST,
                requestEntitySendEmail,
                Boolean.class);

        return reSendEmail.getBody();
    }

    public User getAccount(String accountId, HttpHeaders httpHeaders) {
        logger.info("[Cancel Order Service][Get Order By Id]");

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
        logger.info("[Preserve Service][Add Assurance Type For Order]");
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
        logger.info("[Preserve Other Service][Get Station By  Name]");


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
        logger.info("[Preserve Other Service][Check Account Security] Checking....");

        HttpEntity requestCheckResult = new HttpEntity(httpHeaders);
        ResponseEntity<Response> reCheckResult = restTemplate.exchange(
                "http://ts-security-service:11188/api/v1/securityservice/securityConfigs/" + accountId,
                HttpMethod.GET,
                requestCheckResult,
                Response.class);

        return reCheckResult.getBody();
    }


    private Response<TripAllDetail> getTripAllDetailInformation(TripAllDetailInfo gtdi, HttpHeaders httpHeaders) {
        logger.info("[Preserve Other Service][Get Trip All Detail Information] Getting....");

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
        logger.info("[Preserve Other Service][Get Contacts By Id is] Getting....");

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
        logger.info("[Preserve Other Service][Get Contacts By Id] Creating....");

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
        logger.info("[Preserve Service][Add Preserve food Order] Creating....");

        HttpEntity requestEntityAddFoodOrderResult = new HttpEntity(afi, httpHeaders);
        ResponseEntity<Response> reAddFoodOrderResult = restTemplate.exchange(
                "http://ts-food-service:18856/api/v1/foodservice/orders",
                HttpMethod.POST,
                requestEntityAddFoodOrderResult,
                Response.class);

        return reAddFoodOrderResult.getBody();
    }

    private Response createConsign(Consign cr, HttpHeaders httpHeaders) {
        logger.info("[Preserve Service][Add Condign] Creating Consign...");

        HttpEntity requestEntityResultForTravel = new HttpEntity(cr, httpHeaders);
        ResponseEntity<Response> reResultForTravel = restTemplate.exchange(
                "http://ts-consign-service:16111/api/v1/consignservice/consigns",
                HttpMethod.POST,
                requestEntityResultForTravel,
                Response.class);

        return reResultForTravel.getBody();
    }
}
