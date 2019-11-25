/**
 * @author shenxinyao
 * @date 2019/11/19
 */
package other.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import other.entity.*;

import java.util.Date;
import java.util.UUID;

public interface OrderOtherService {

    /**
     * findOrderById
     * @param id:
     * @param headers:
     * @return :
     */
    Response findOrderById(UUID id, HttpHeaders headers);

    /**
     * create
     * @param newOrder:
     * @param headers:
     * @return :
     */
    Response create(Order newOrder, HttpHeaders headers);

    /**
     * updateOrder
     * @param order:
     * @param headers:
     * @return :
     */
    Response updateOrder(Order order, HttpHeaders headers);

    /**
     * saveChanges
     * @param order:
     * @param headers:
     * @return :
     */
    Response saveChanges(Order order, HttpHeaders headers);

    /**
     * cancelOrder
     * @param accountId:
     * @param orderId:
     * @param headers:
     * @return :
     */
    Response cancelOrder(UUID accountId, UUID orderId, HttpHeaders headers);

    /**
     * addNewOrder
     * @param order:
     * @param headers:
     * @return :
     */
    Response addNewOrder(Order order, HttpHeaders headers);

    /**
     * deleteOrder
     * @param orderId:
     * @param headers:
     * @return :
     */
    Response deleteOrder(String orderId, HttpHeaders headers);

    /**
     * getOrderById
     * @param orderId:
     * @param headers:
     * @return :
     */
    Response getOrderById(String orderId, HttpHeaders headers);

    /**
     * payOrder
     * @param orderId:
     * @param headers:
     * @return :
     */
    Response payOrder(String orderId, HttpHeaders headers);

    /**
     * getOrderPrice
     * @param orderId:
     * @param headers:
     * @return :
     */
    Response getOrderPrice(String orderId, HttpHeaders headers);

    /**
     * modifyOrder
     * @param orderId:
     * @param status:
     * @param headers:
     * @return :
     */
    Response modifyOrder(String orderId, int status, HttpHeaders headers);

    /**
     * getAllOrders
     * @param headers:
     * @return :
     */
    Response getAllOrders(HttpHeaders headers);

    /**
     * findOrderById
     * @param seatRequest:
     * @param headers:
     * @return :
     */
    Response getSoldTickets(Seat seatRequest, HttpHeaders headers);

    /**
     * queryOrders
     * @param qi:
     * @param accountId:
     * @param headers:
     * @return :
     */
    Response queryOrders(QueryInfo qi, String accountId, HttpHeaders headers);

    /**
     * queryOrdersForRefresh
     * @param qi:
     * @param accountId:
     * @param headers:
     * @return :
     */
    Response queryOrdersForRefresh(QueryInfo qi, String accountId, HttpHeaders headers);

    /**
     * alterOrder
     * @param oai:
     * @param headers:
     * @return :
     */
    Response alterOrder(OrderAlterInfo oai, HttpHeaders headers);

    /**
     * queryAlreadySoldOrders
     * @param travelDate:
     * @param trainNumber:
     * @param headers:
     * @return :
     */
    Response queryAlreadySoldOrders(Date travelDate, String trainNumber, HttpHeaders headers);

    /**
     * checkSecurityAboutOrder
     * @param checkDate:
     * @param accountId:
     * @param headers:
     * @return :
     */
    Response checkSecurityAboutOrder(Date checkDate, String accountId, HttpHeaders headers);

    /**
     * initOrder
     * @param order:
     * @param headers:
     * @return :
     */
    void initOrder(Order order, HttpHeaders headers);
}
