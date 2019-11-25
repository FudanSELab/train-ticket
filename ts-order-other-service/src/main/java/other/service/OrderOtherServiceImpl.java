package other.service;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import other.entity.*;
import other.repository.OrderOtherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



@Service
@Slf4j
public class OrderOtherServiceImpl implements OrderOtherService {

    private static final String NFOUND = "Order Not Found";
    private static final String SUC = "Success.";
    private static final String SUC2 = "Success" ;

    private static final Logger mylogger = Logger.getLogger("infomation");

    @Autowired
    private OrderOtherRepository orderOtherRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response getSoldTickets(Seat seatRequest, HttpHeaders headers) {
        ArrayList<Order> list;

        list = orderOtherRepository.findByTravelDateAndTrainNumber(seatRequest.getTravelDate(), seatRequest.getTrainNumber());

        if (list != null && list.size() > 0 ) {
            Set ticketSet = new HashSet();
            for (Order tempOrder : list) {
                Ticket ticket = new Ticket();
                ticket.setSeatNo(Integer.parseInt(tempOrder.getSeatNumber()));
                ticket.setStartStation(tempOrder.getFrom());
                ticket.setDestStation(tempOrder.getTo());
                ticketSet.add(ticket);
            }

            LeftTicketInfo leftTicketInfo = new LeftTicketInfo();
            leftTicketInfo.setSoldTickets(ticketSet);

            mylogger.log(Level.INFO , () -> "Left ticket info is: " + leftTicketInfo.toString());

            return new Response<>(1, SUC2,leftTicketInfo);
        } else {
            return new Response<>(0, "Seat is Null.", null);
        }
    }

    public void testInit(OrderOtherRepository repository, RestTemplate restTemplate) {
        this.orderOtherRepository = repository;
        this.restTemplate = restTemplate;
    }


    @Override
    public Response findOrderById(UUID id, HttpHeaders headers) {
        Order order = orderOtherRepository.findById(id);
        if (order == null) {
            return new Response<UUID>(0, "No Content by this id", id);
        } else {
            return new Response<Order>(1, SUC2, order);
        }
    }

    @Override
    public Response create(Order order, HttpHeaders headers) {
        mylogger.log(Level.INFO , () -> "[Order Other Service][Create Order] Ready Create Order");

        ArrayList<Order> accountOrders = orderOtherRepository.findByAccountId(order.getAccountId());

        if (accountOrders.contains(order)) {
            mylogger.log(Level.INFO , () -> "[Order Other Service][Order Create] Fail.Order already exists.");

            return new Response<Order>(0, "Order already exist", order);
        } else {
            order.setId(UUID.randomUUID());
            orderOtherRepository.save(order);
            mylogger.log(Level.INFO , () -> "[Order Other Service][Order Create] Success.");
            mylogger.log(Level.INFO , () -> "[Order Other Service][Order Create] Price:" + order.getPrice());

            return new Response<>(1, SUC2, order);
        }
    }

    @Override
    public void initOrder(Order order, HttpHeaders headers) {
        Order orderTemp = orderOtherRepository.findById(order.getId());
        if (orderTemp == null) {
            orderOtherRepository.save(order);
        } else {
            mylogger.log(Level.INFO , () -> "[Order Other Service][Init Order] Order Already Exists ID:" + order.getId());

        }
    }


    @Override
    public Response alterOrder(OrderAlterInfo oai, HttpHeaders headers) {

        UUID oldOrderId = oai.getPreviousOrderId();
        Order oldOrder = orderOtherRepository.findById(oldOrderId);
        if (oldOrder == null) {

            mylogger.log(Level.INFO , () ->"[Order Other Service][Alter Order] Fail.Order do not exist.");
            return new Response<>(0, "Old Order Does Not Exists", null);
        }
        oldOrder.setStatus(OrderStatus.CANCEL.getCode());
        saveChanges(oldOrder, headers);
        Order newOrder = oai.getNewOrderInfo();
        newOrder.setId(UUID.randomUUID());
        Response cor = create(oai.getNewOrderInfo(), headers);
        if (cor.getStatus() == 1) {

            mylogger.log(Level.INFO , () ->"[Order Other Service][Alter Order] Success.");
            return new Response<>(1, "Alter Order Success", newOrder);
        } else {
            return new Response<>(0, cor.getMsg(), null);
        }
    }

    public Boolean checkEnableState(QueryInfo qi,Order tempOrder){
        boolean stateflag = false;
        if(qi.isEnableStateQuery()){
            if (tempOrder.getStatus() != qi.getState()) {
                stateflag = false;
            } else {
                stateflag = true;
            }
        } else {
            stateflag = true;
        }

        return stateflag;
    }

    public Boolean checkEnableTravel(QueryInfo qi,Order tempOrder){
        boolean travelFlag = false;
        if (qi.isEnableTravelDateQuery()) {
            if (tempOrder.getTravelDate().before(qi.getTravelDateEnd()) &&
                    tempOrder.getTravelDate().after(qi.getBoughtDateStart())) {
                travelFlag = true;
            } else {
                travelFlag = false;
            }
        } else {
            travelFlag = true;
        }

        return travelFlag;
    }

    public Boolean checkBoughtState(QueryInfo qi,Order tempOrder){
        boolean boughtDateFlag = false;
        if (qi.isEnableBoughtDateQuery()) {
            if (tempOrder.getBoughtDate().before(qi.getBoughtDateEnd()) &&
                    tempOrder.getBoughtDate().after(qi.getBoughtDateStart())) {
                boughtDateFlag = true;
            } else {
                boughtDateFlag = false;
            }
        } else {
            boughtDateFlag = true;
        }

        return boughtDateFlag;
    }

    @Override
    public Response<ArrayList<Order>> queryOrders(QueryInfo qi, String accountId, HttpHeaders headers) {
        //1.Get all orders of the user
        ArrayList<Order> list = orderOtherRepository.findByAccountId(UUID.fromString(accountId));

        mylogger.log(Level.INFO , () ->"[Order Other Service][Query Order][Step 1] Get Orders Number of Account:" + list.size());
        //2.Check is these orders fit the requirement/
        if (qi.isEnableStateQuery() || qi.isEnableBoughtDateQuery() || qi.isEnableTravelDateQuery()) {
            ArrayList<Order> finalList = new ArrayList<>();
            for (Order tempOrder : list) {
                boolean statePassFlag = false;
                boolean boughtDatePassFlag = false;
                boolean travelDatePassFlag = false;
                //3.Check order state requirement.
                statePassFlag = checkEnableState(qi,tempOrder);


                mylogger.log(Level.INFO , () -> "[Order Other Service][Query Order][Step 2][Check Status Fits End]");
                //4.Check order travel date requirement.
                travelDatePassFlag = checkEnableTravel(qi,tempOrder);


                //5.Check order bought date requirement.
                mylogger.log(Level.INFO , () -> "[Order Other Service][Query Order][Step 2][Check Travel Date End]");
                boughtDatePassFlag = checkBoughtState(qi,tempOrder);


                mylogger.log(Level.INFO , () -> "[Order Other Service][Query Order][Step 2][Check Bought Date End]");
                //6.check if all requirement fits.
                if (statePassFlag && boughtDatePassFlag && travelDatePassFlag) {
                    finalList.add(tempOrder);
                }

                mylogger.log(Level.INFO , () -> "[Order Other Service][Query Order][Step 2][Check All Requirement End]");
            }
            mylogger.log(Level.INFO , () -> "[Order Other Service][Query Order] Get order num:" +
                    finalList.size() );

            return new Response<>(1, "Get order num", finalList);
        } else {
            mylogger.log(Level.INFO , () -> "[Order Other Service][Query Order] Get order num:" +
                    list.size() );

            return new Response<>(1, "Get order num", list);
        }
    }

    @Override
    public Response queryOrdersForRefresh(QueryInfo qi, String accountId, HttpHeaders headers) {
        ArrayList<Order> orders = queryOrders(qi, accountId, headers).getData();
        ArrayList<String> stationIds = new ArrayList<>();
        for (Order order : orders) {
            stationIds.add(order.getFrom());
            stationIds.add(order.getTo());
        }
        List<String> names = queryForStationId(stationIds, headers);
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setFrom(names.get(i * 2));
            orders.get(i).setTo(names.get(i * 2 + 1));
        }
        return new Response<>(1, SUC2, orders);
    }

    public List<String> queryForStationId(List<String> ids, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(ids, headers);
        ResponseEntity<Response<List<String>>> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/namelist",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<List<String>>>() {
                });

        mylogger.log(Level.INFO , () -> "Stations name list is : " + re.getBody().toString() );
        return re.getBody().getData();
    }

    @Override
    public  Response saveChanges(Order order, HttpHeaders headers) {
        Order oldOrder = orderOtherRepository.findById(order.getId());

        if (oldOrder == null) {
            mylogger.log(Level.INFO , () -> "[Order Other Service][Modify Order] Fail.Order not found.");

            return new Response<>(0, NFOUND, null);
        } else {
            oldOrder.setAccountId(order.getAccountId());
            oldOrder.setBoughtDate(order.getBoughtDate());
            oldOrder.setTravelDate(order.getTravelDate());
            oldOrder.setTravelTime(order.getTravelTime());
            oldOrder.setSeatClass(order.getSeatClass());
            oldOrder.setCoachNumber(order.getCoachNumber());

            oldOrder.setSeatNumber(order.getSeatNumber());
            oldOrder.setTo(order.getTo());
            oldOrder.setFrom(order.getFrom());
            oldOrder.setStatus(order.getStatus());
            oldOrder.setTrainNumber(order.getTrainNumber());
            oldOrder.setPrice(order.getPrice());
            oldOrder.setContactsName(order.getContactsName());
            oldOrder.setDocumentType(order.getDocumentType());
            oldOrder.setContactsDocumentNumber(order.getContactsDocumentNumber());

            orderOtherRepository.save(oldOrder);

            mylogger.log(Level.INFO , () ->  "[Order Other Service] Success."  );
            return new Response(1, SUC2, oldOrder);
        }
    }

    @Override
    public Response cancelOrder(UUID accountId, UUID orderId, HttpHeaders headers) {

        Order oldOrder = orderOtherRepository.findById(orderId);
        if (oldOrder == null) {

            mylogger.log(Level.INFO , () ->  "[Order Other Service][Cancel Order] Fail.Order not found."  );
            return new Response(0, NFOUND, null);
        } else {
            oldOrder.setStatus(OrderStatus.CANCEL.getCode());
            orderOtherRepository.save(oldOrder);
            mylogger.log(Level.INFO , () ->  "[Order Other Service][Cancel Order] Success." );

            return new Response<Order>(1, SUC2, oldOrder);
        }
    }

    @Override
    public Response queryAlreadySoldOrders(Date travelDate, String trainNumber, HttpHeaders headers) {
        ArrayList<Order> orders = orderOtherRepository.findByTravelDateAndTrainNumber(travelDate, trainNumber);
        SoldTicket cstr = new SoldTicket();
        cstr.setTravelDate(travelDate);
        cstr.setTrainNumber(trainNumber);

        mylogger.log(Level.INFO , () ->  "[Order Other Service][Calculate Sold Ticket] Get Orders Number:" + orders.size());
        for (Order order : orders) {
            if (order.getStatus() >= OrderStatus.CHANGE.getCode()) {
                continue;
            }
            if (order.getSeatClass() == SeatClass.NONE.getCode()) {
                cstr.setNoSeat(cstr.getNoSeat() + 1);
            } else if (order.getSeatClass() == SeatClass.BUSINESS.getCode()) {
                cstr.setBusinessSeat(cstr.getBusinessSeat() + 1);
            } else if (order.getSeatClass() == SeatClass.FIRSTCLASS.getCode()) {
                cstr.setFirstClassSeat(cstr.getFirstClassSeat() + 1);
            } else if (order.getSeatClass() == SeatClass.SECONDCLASS.getCode()) {
                cstr.setSecondClassSeat(cstr.getSecondClassSeat() + 1);
            } else if (order.getSeatClass() == SeatClass.HARDSEAT.getCode()) {
                cstr.setHardSeat(cstr.getHardSeat() + 1);
            } else if (order.getSeatClass() == SeatClass.SOFTSEAT.getCode()) {
                cstr.setSoftSeat(cstr.getSoftSeat() + 1);
            } else if (order.getSeatClass() == SeatClass.HARDBED.getCode()) {
                cstr.setHardBed(cstr.getHardBed() + 1);
            } else if (order.getSeatClass() == SeatClass.SOFTBED.getCode()) {
                cstr.setSoftBed(cstr.getSoftBed() + 1);
            } else if (order.getSeatClass() == SeatClass.HIGHSOFTBED.getCode()) {
                cstr.setHighSoftBed(cstr.getHighSoftBed() + 1);
            } else {

                mylogger.log(Level.INFO , () ->  "[Order Other Service][Calculate Sold Tickets] Seat class not exists. Order ID:" + order.getId());
            }
        }
        return new Response<>(1, SUC2, cstr);
    }

    @Override
    public Response getAllOrders(HttpHeaders headers) {
        ArrayList<Order> orders = orderOtherRepository.findAll();
        if (orders == null) {
            return new Response<>(0, "No Content", null);
        } else {
            return new Response<ArrayList<Order>>(1, SUC, orders);
        }
    }

    @Override
    public Response modifyOrder(String orderId, int status, HttpHeaders headers) {
        Order order = orderOtherRepository.findById(UUID.fromString(orderId));

        if (order == null) {
            return new Response<>(0,NFOUND, null);
        } else {
            order.setStatus(status);
            orderOtherRepository.save(order);
            return new Response<>(1, SUC2, order);
        }
    }

    @Override
    public Response getOrderPrice(String orderId, HttpHeaders headers) {
        Order order = orderOtherRepository.findById(UUID.fromString(orderId));
        if (order == null) {
            return new Response<>(0, NFOUND, "-1.0");
        } else {

            mylogger.log(Level.INFO , () ->  "[Order Other Service][Get Order Price] Price:" + order.getPrice());
            return new Response<>(1, SUC2, order.getPrice());
        }
    }

    @Override
    public Response payOrder(String orderId, HttpHeaders headers) {
        Order order = orderOtherRepository.findById(UUID.fromString(orderId));
        if (order == null) {
            return new Response<>(0, NFOUND, null);
        } else {
            order.setStatus(OrderStatus.PAID.getCode());
            orderOtherRepository.save(order);
            return new Response<>(1, SUC, order);
        }
    }

    @Override
    public Response getOrderById(String orderId, HttpHeaders headers) {
        Order order = orderOtherRepository.findById(UUID.fromString(orderId));

        if (order == null) {
            return new Response<>(0, NFOUND, null);
        } else {
            return new Response<>(1, SUC2, order);
        }
    }

    @Override
    public Response checkSecurityAboutOrder(Date dateFrom, String accountId, HttpHeaders headers) {
        OrderSecurity result = new OrderSecurity();
        ArrayList<Order> orders = orderOtherRepository.findByAccountId(UUID.fromString(accountId));
        int countOrderInOneHour = 0;
        int countTotalValidOrder = 0;
        Calendar ca = Calendar.getInstance();
        ca.setTime(dateFrom);
        ca.add(Calendar.HOUR_OF_DAY, -1);
        dateFrom = ca.getTime();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.NOTPAID.getCode() ||
                    order.getStatus() == OrderStatus.PAID.getCode() ||
                    order.getStatus() == OrderStatus.COLLECTED.getCode()) {
                countTotalValidOrder += 1;
            }
            if (order.getBoughtDate().after(dateFrom)) {
                countOrderInOneHour += 1;
            }
        }
        result.setOrderNumInLastOneHour(countOrderInOneHour);
        result.setOrderNumOfValidOrder(countTotalValidOrder);
        return new Response<>(1, "Success . ", result);
    }

    @Override
    public Response deleteOrder(String orderId, HttpHeaders headers) {
        UUID orderUuid = UUID.fromString(orderId);
        Order order = orderOtherRepository.findById(orderUuid);
        if (order == null) {
            return new Response<>(0, "Order Not Exist.", null);
        } else {
            orderOtherRepository.deleteById(orderUuid);
            return new Response<>(1, SUC, orderUuid);
        }
    }

    @Override
    public Response addNewOrder(Order order, HttpHeaders headers) {

        mylogger.log(Level.INFO , () ->  "[Order Service][Admin Add Order] Ready Add Order.");
        ArrayList<Order> accountOrders = orderOtherRepository.findByAccountId(order.getAccountId());

        if (accountOrders.contains(order)) {

            mylogger.log(Level.INFO , () ->  "[Order Service][Admin Add Order] Fail.Order already exists.");
            return new Response<>(0, "Order already exist", null);
        } else {
            order.setId(UUID.randomUUID());
            orderOtherRepository.save(order);
            mylogger.log(Level.INFO , () -> "[Order Service][Admin Add Order] Success.");
            mylogger.log(Level.INFO , () ->  "[Order Service][Admin Add Order] Price:" + order.getPrice());
            return new Response<>(1, SUC2, order);
        }
    }

    @Override
    public Response updateOrder(Order order, HttpHeaders headers) {
        System.out.println("UPDATE ORDER INFO :" + order.toString());
        Order oldOrder = orderOtherRepository.findById(order.getId());
        if (oldOrder == null) {
            String a = null;
            mylogger.log(Level.INFO , () -> "[Order Service][Admin Update Order] Fail.Order not found.");
            return new Response<>(0, NFOUND, null);
        } else {

            mylogger.log(Level.INFO , () -> oldOrder.toString());
            oldOrder.setAccountId(order.getAccountId());
            oldOrder.setBoughtDate(order.getBoughtDate());
            oldOrder.setTravelDate(order.getTravelDate());
            oldOrder.setTravelTime(order.getTravelTime());
            oldOrder.setCoachNumber(order.getCoachNumber());
            oldOrder.setSeatClass(order.getSeatClass());
            oldOrder.setSeatNumber(order.getSeatNumber());
            oldOrder.setFrom(order.getFrom());
            oldOrder.setTo(order.getTo());
            oldOrder.setStatus(order.getStatus());
            oldOrder.setTrainNumber(order.getTrainNumber());
            oldOrder.setPrice(order.getPrice());
            oldOrder.setContactsName(order.getContactsName());
            oldOrder.setContactsDocumentNumber(order.getContactsDocumentNumber());
            oldOrder.setDocumentType(order.getDocumentType());
            orderOtherRepository.save(oldOrder);

            mylogger.log(Level.INFO , () -> "[Order Service] [Admin Update Order] Success.");
            return new Response<>(1, SUC2, oldOrder);
        }
    }
}

