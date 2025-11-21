package order.service.impl;

import edu.fudan.common.client.dto.order.OrderQueryDto;
import edu.fudan.common.client.dto.order.SoldOrderStatDto;
import edu.fudan.common.client.dto.order.TravelDateNumberDto;
import edu.fudan.common.entity.*;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import order.entity.Order;
import order.repository.OrderRepository;
import order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author fdse
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    String success = "Success";
    String orderNotFound = "Order Not Found";

    @Override
    public Response<ArrayList<Order>> queryOrders(OrderQueryDto qi, String accountId, HttpHeaders headers) {
        ArrayList<Order> list = orderRepository.findByUserId(accountId);
        log.info("[queryOrders][Step 1][Get Orders Number of Account][size: {}]", list.size());
        if (qi.isEnableStateQuery() || qi.isEnableBoughtDateQuery() || qi.isEnableTravelDateQuery()) {
            ArrayList<Order> filtered = list.stream()
                    .filter(matchesState(qi))
                    .filter(matchesTravelDate(qi))
                    .filter(matchesBoughtDate(qi))
                    .collect(Collectors.toCollection(ArrayList::new));
            log.info("[queryOrders][Get order num][size:{}]", filtered.size());
            return new Response<>(1, "Get order num", filtered);
        }
        log.warn("[queryOrders][Orders don't fit the requirement][loginId: {}]", qi.getLoginId());
        return new Response<>(1, "Get order num", list);
    }

    @Override
    public Response<SoldOrderStatDto> queryAlreadySoldOrders(TravelDateNumberDto request, HttpHeaders headers) {
        Date travelDate = StringUtils.String2Date(request.getTravelDate());
        String trainNumber = request.getTravelNumber();
        ArrayList<Order> orders = orderRepository.findByTravelDateAndTrainNumber(StringUtils.Date2String(travelDate), trainNumber);
        SoldOrderStatDto stats = new SoldOrderStatDto();
        stats.setTravelDate(travelDate);
        stats.setTrainNumber(trainNumber);
        log.info("[queryAlreadySoldOrders][Calculate Sold Ticket][Get Orders Number: {}]", orders.size());
        orders.stream()
                .filter(this::isCountableOrder)
                .forEach(order -> incrementSeat(stats, order.getSeatClass()));
        return new Response<>(1, success, stats);
    }

    @Override
    public Response<ArrayList<Order>> getAllOrders(HttpHeaders headers) {
        ArrayList<Order> orders = new ArrayList<>(orderRepository.findAll());
        if (orders.isEmpty()) {
            log.warn("[getAllOrders][Find all orders Fail][{}]","No content");
            return new Response<>(0, "No Content.", null);
        }
        log.warn("[getAllOrders][Find all orders Success][size:{}]",orders.size());
        return new Response<>(1, "Success.", orders);
    }


    @Override
    public Response<String> getOrderPrice(String orderId, HttpHeaders headers) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    log.info("[getOrderPrice][Get Order Price Success][OrderId: {} , Price: {}]",orderId ,order.getPrice());
                    return new Response<>(1, success, order.getPrice());
                })
                .orElseGet(() -> {
                    log.error("[getOrderPrice][Get order price Fail][Order not found][OrderId: {}]",orderId);
                    return new Response<>(0, orderNotFound, "-1.0");
                });
    }

    @Override
    public Response<Order> getOrderById(String orderId, HttpHeaders headers) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    log.info("[getOrderById][Get Order By ID Success][OrderId: {}]",orderId);
                    return new Response<>(1, "Success.", order);
                })
                .orElseGet(() -> {
                    log.warn("[getOrderById][Get Order By ID Fail][Order not found][OrderId: {}]",orderId);
                    return new Response<>(0, orderNotFound, null);
                });
    }

    @Override
    public Response<OrderSecurity> checkSecurityAboutOrder(Date dateFrom, String userId, HttpHeaders headers) {
        ArrayList<Order> orders = orderRepository.findByUserId(userId);
        Calendar ca = Calendar.getInstance();
        ca.setTime(dateFrom);
        ca.add(Calendar.HOUR_OF_DAY, -1);
        Date threshold = ca.getTime();

        long validCount = orders.stream()
                .filter(this::isValidOrderStatus)
                .count();

        long withinHour = orders.stream()
                .map(order -> StringUtils.String2Date(order.getBoughtDate()))
                .filter(Objects::nonNull)
                .filter(date -> date.after(threshold))
                .count();

        OrderSecurity result = new OrderSecurity();
        result.setOrderNumInLastOneHour((int) withinHour);
        result.setOrderNumOfValidOrder((int) validCount);
        return new Response<>(1, "Check Security Success . ", result);
    }

    private Predicate<Order> matchesState(OrderQueryDto qi) {
        return order -> !qi.isEnableStateQuery() || order.getStatus() == qi.getState();
    }

    private Predicate<Order> matchesTravelDate(OrderQueryDto qi) {
        if (!qi.isEnableTravelDateQuery()) {
            return order -> true;
        }
        Date travelDateEnd = StringUtils.String2Date(qi.getTravelDateEnd());
        Date travelDateStartBoundary = StringUtils.String2Date(qi.getBoughtDateStart());
        return order -> {
            Date travelDate = StringUtils.String2Date(order.getTravelDate());
            return travelDate != null &&
                    (travelDateEnd == null || travelDate.before(travelDateEnd)) &&
                    (travelDateStartBoundary == null || travelDate.after(travelDateStartBoundary));
        };
    }

    private Predicate<Order> matchesBoughtDate(OrderQueryDto qi) {
        if (!qi.isEnableBoughtDateQuery()) {
            return order -> true;
        }
        Date boughtDateStart = StringUtils.String2Date(qi.getBoughtDateStart());
        Date boughtDateEnd = StringUtils.String2Date(qi.getBoughtDateEnd());
        return order -> {
            Date boughtDate = StringUtils.String2Date(order.getBoughtDate());
            return boughtDate != null &&
                    (boughtDateEnd == null || boughtDate.before(boughtDateEnd)) &&
                    (boughtDateStart == null || boughtDate.after(boughtDateStart));
        };
    }

    private boolean isCountableOrder(Order order) {
        return order.getStatus() < OrderStatus.CHANGE.getCode();
    }

    private void incrementSeat(SoldOrderStatDto stats, int seatClass) {
        SeatClass clazz = resolveSeatClass(seatClass);
        if (clazz == null) {
            log.info("[queryAlreadySoldOrders][Calculate Sold Tickets][Seat class not exists][SeatClass: {}]", seatClass);
            return;
        }
        switch (clazz) {
            case NONE:
                stats.setNoSeat(stats.getNoSeat() + 1);
                break;
            case BUSINESS:
                stats.setBusinessSeat(stats.getBusinessSeat() + 1);
                break;
            case FIRSTCLASS:
                stats.setFirstClassSeat(stats.getFirstClassSeat() + 1);
                break;
            case SECONDCLASS:
                stats.setSecondClassSeat(stats.getSecondClassSeat() + 1);
                break;
            case HARDSEAT:
                stats.setHardSeat(stats.getHardSeat() + 1);
                break;
            case SOFTSEAT:
                stats.setSoftSeat(stats.getSoftSeat() + 1);
                break;
            case HARDBED:
                stats.setHardBed(stats.getHardBed() + 1);
                break;
            case SOFTBED:
                stats.setSoftBed(stats.getSoftBed() + 1);
                break;
            case HIGHSOFTBED:
                stats.setHighSoftBed(stats.getHighSoftBed() + 1);
                break;
            default:
        }
    }

    private SeatClass resolveSeatClass(int code) {
        for (SeatClass value : SeatClass.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    private boolean isValidOrderStatus(Order order) {
        int status = order.getStatus();
        return status == OrderStatus.NOTPAID.getCode()
                || status == OrderStatus.PAID.getCode()
                || status == OrderStatus.COLLECTED.getCode();
    }
}

