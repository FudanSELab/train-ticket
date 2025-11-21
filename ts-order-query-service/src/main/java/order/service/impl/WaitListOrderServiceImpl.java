package order.service.impl;

import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import order.entity.WaitListOrder;
import order.entity.WaitListOrderStatus;
import order.repository.WaitListOrderRepository;
import order.service.WaitListOrderService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitListOrderServiceImpl implements WaitListOrderService {

    private static final String NO_CONTENT = "No Content.";
    private static final Set<Integer> ACTIVE_STATUS_CODES = new HashSet<>(
            Arrays.asList(WaitListOrderStatus.NOTPAID.getCode(), WaitListOrderStatus.PAID.getCode()));

    private final WaitListOrderRepository waitListOrderRepository;

    @Override
    public Response<List<WaitListOrder>> getAllWaitListOrders(HttpHeaders headers) {
        return fetchOrders("getAllOrders", Function.identity());
    }

    @Override
    public Response<List<WaitListOrder>> getAllActiveWaitListOrders(HttpHeaders headers) {
        return fetchOrders("getAllWaitListOrders", orders -> orders.stream()
                .filter(order -> ACTIVE_STATUS_CODES.contains(order.getStatus()))
                .collect(Collectors.toList()));
    }

    private Response<List<WaitListOrder>> fetchOrders(String logPrefix,
                                                      Function<List<WaitListOrder>, List<WaitListOrder>> transformer) {
        List<WaitListOrder> orderList = waitListOrderRepository.findAll();
        if (orderList == null || orderList.isEmpty()) {
            log.warn("[{}][Find All Wait List Orders Fail][{}]", logPrefix, "No content");
            return new Response<>(0, NO_CONTENT, null);
        }
        List<WaitListOrder> result = transformer.apply(orderList);
        log.warn("[{}][Find all orders Success][size:{}]", logPrefix, result.size());
        return new Response<>(1, "Success.", result);
    }
}
