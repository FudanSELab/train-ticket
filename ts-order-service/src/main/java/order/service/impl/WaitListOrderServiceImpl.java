package order.service.impl;

import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import order.entity.WaitListOrder;
import order.repository.WaitListOrderRepository;
import order.service.WaitListOrderService;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitListOrderServiceImpl implements WaitListOrderService {

    private static final String SUCCESS = "Success";

    private final WaitListOrderRepository waitListOrderRepository;

    @Transactional
    @Override
    public Response<WaitListOrder> create(WaitListOrder newOrder, HttpHeaders headers) {
        log.info("[create][Create Wait Order][Ready to Create Wait Order]");
        Response<WaitListOrder> response = saveNewOrder(newOrder);
        return response.getStatus() == 0 ? response : new Response<>(1, "Success.", response.getData());
    }

    private Response<WaitListOrder> saveNewOrder(WaitListOrder order) {
        String userId = order.getUserId();
        if (userId == null) {
            log.error("[create][Create Wait Order Fail][Missing userId]");
            return new Response<>(0, "UserId is required", null);
        }
        ArrayList<WaitListOrder> userOrders = waitListOrderRepository.findByUserId(userId);
        if (waitListOrderExists(userOrders, order)) {
            log.error("[create][Create Wait Order Fail][Order already exists][UserId: {} , Train: {}]", userId,
                    order.getTrainNumber());
            return new Response<>(0, "Order already exist", null);
        }
        if (order.getId() == null) {
            order.setId(UUID.randomUUID().toString());
        }
        waitListOrderRepository.save(order);
        log.info("[create][Create Wait Order Success][UserId: {} , Train: {}]", userId, order.getTrainNumber());
        return new Response<>(1, SUCCESS, order);
    }

    private boolean waitListOrderExists(List<WaitListOrder> orderList, WaitListOrder newOrder) {
        List<WaitListOrder> safeList = orderList == null ? Collections.emptyList() : orderList;
        String userId = newOrder.getUserId();
        String contactsId = newOrder.getContactsId();
        String trainNumber = newOrder.getTrainNumber();
        Date travelTime = newOrder.getTravelTime();
        String fromStation = newOrder.getFromStation();
        String toStation = newOrder.getToStation();

        return safeList.stream().anyMatch(order ->
                Objects.equals(order.getUserId(), userId)
                        && Objects.equals(order.getContactsId(), contactsId)
                        && Objects.equals(order.getTrainNumber(), trainNumber)
                        && Objects.equals(order.getTravelTime(), travelTime)
                        && Objects.equals(order.getFromStation(), fromStation)
                        && Objects.equals(order.getToStation(), toStation));
    }
}
