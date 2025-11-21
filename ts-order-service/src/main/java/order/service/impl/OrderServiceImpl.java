package order.service.impl;

import edu.fudan.common.entity.OrderStatus;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.Order;
import order.repository.OrderRepository;
import order.service.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author fdse
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String SUCCESS = "Success";
    private static final String ORDER_NOT_FOUND = "Order Not Found";

    private final OrderRepository orderRepository;

    @Override
    public Response<Order> create(Order order, HttpHeaders headers) {
        log.info("[create][Create Order][Ready to Create Order]");
        ArrayList<Order> accountOrders = orderRepository.findByAccountId(order.getUserId());
        if (accountOrders.contains(order)) {
            log.error("[create][Order Create Fail][Order already exists][OrderId: {}]", order.getId());
            return new Response<>(0, "Order already exist", null);
        }
        order.setId(UUID.randomUUID().toString());
        Order saved = orderRepository.save(order);
        log.info("[create][Order Create Success][Order Price][OrderId:{} , Price: {}]", saved.getId(), saved.getPrice());
        return success(saved);
    }

    @Override
    public Response<Order> update(Order order, HttpHeaders headers) {
        return orderRepository.findById(order.getId())
                .map(existing -> {
                    copyOrderFields(order, existing);
                    orderRepository.save(existing);
                    log.info("[saveChanges][Modify Order Success][OrderId: {}]", order.getId());
                    return success(existing);
                })
                .orElseGet(() -> {
                    log.error("[saveChanges][Modify Order Fail][Order not found][OrderId: {}]", order.getId());
                    return failure(ORDER_NOT_FOUND);
                });
    }

    @Override
    public Response<Order> updateStatus(String orderId, int status, HttpHeaders headers) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    orderRepository.save(order);
                    log.info("[modifyOrder][Modify order Success][OrderId: {}]", orderId);
                    return new Response<>(1, "Modify Order Success", order);
                })
                .orElseGet(() -> {
                    log.error("[modifyOrder][Modify order Fail][Order not found][OrderId: {}]", orderId);
                    return failure(ORDER_NOT_FOUND);
                });
    }

    @Override
    public Response<Order> pay(String orderId, HttpHeaders headers) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(OrderStatus.PAID.getCode());
                    orderRepository.save(order);
                    log.info("[payOrder][Pay order Success][OrderId: {}]", orderId);
                    return new Response<>(1, "Pay Order Success.", order);
                })
                .orElseGet(() -> {
                    log.error("[payOrder][Pay order Fail][Order not found][OrderId: {}]", orderId);
                    return failure(ORDER_NOT_FOUND);
                });
    }

    @Override
    public void ensureOrder(Order order, HttpHeaders headers) {
        if (orderRepository.existsById(order.getId())) {
            log.error("[initOrder][Init Order Fail][Order Already Exists][OrderId: {}]", order.getId());
            return;
        }
        orderRepository.save(order);
        log.info("[initOrder][Init Order Success][OrderId: {}]", order.getId());
    }

    @Override
    public Response<Order> delete(String orderId, HttpHeaders headers) {
        String orderUuid = normalizeOrderId(orderId);
        return orderRepository.findById(orderUuid)
                .map(order -> {
                    orderRepository.deleteById(orderUuid);
                    log.info("[deleteOrder][Delete order Success][OrderId: {}]", orderId);
                    return new Response<>(1, "Delete Order Success", order);
                })
                .orElseGet(() -> {
                    log.error("[deleteOrder][Delete order Fail][Order not found][OrderId: {}]", orderId);
                    return new Response<>(0, "Order Not Exist.", null);
                });
    }

    private Response<Order> success(Order order) {
        return new Response<>(1, SUCCESS, order);
    }

    private Response<Order> failure(String msg) {
        return new Response<>(0, msg, null);
    }

    private void copyOrderFields(Order source, Order target) {
        target.setUserId(source.getUserId());
        target.setBoughtDate(source.getBoughtDate());
        target.setTravelDate(source.getTravelDate());
        target.setTravelTime(source.getTravelTime());
        target.setCoachNumber(source.getCoachNumber());
        target.setSeatClass(source.getSeatClass());
        target.setSeatNumber(source.getSeatNumber());
        target.setFromStationId(source.getFromStationId());
        target.setToStationId(source.getToStationId());
        target.setStatus(source.getStatus());
        target.setTrainNumber(source.getTrainNumber());
        target.setPrice(source.getPrice());
        target.setContactsName(source.getContactsName());
        target.setContactsDocumentNumber(source.getContactsDocumentNumber());
        target.setDocumentType(source.getDocumentType());
    }

    private String normalizeOrderId(String orderId) {
        return UUID.fromString(orderId).toString();
    }
}

