/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package other.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import other.entity.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
public interface OrderOtherService {

    Response findOrderById(UUID id, HttpHeaders headers);

    Response create(Order newOrder, HttpHeaders headers);

    Response updateOrder(Order order, HttpHeaders headers);

    Response saveChanges(Order order, HttpHeaders headers);

    Response cancelOrder(UUID accountId, UUID orderId, HttpHeaders headers);

    Response addNewOrder(Order order, HttpHeaders headers);

    Response deleteOrder(String orderId, HttpHeaders headers);

    Response getOrderById(String orderId, HttpHeaders headers);

    Response payOrder(String orderId, HttpHeaders headers);

    Response getOrderPrice(String orderId, HttpHeaders headers);

    Response modifyOrder(String orderId, int status, HttpHeaders headers);

    Response getAllOrders(HttpHeaders headers);

    Response getSoldTickets(Seat seatRequest, HttpHeaders headers);

    Response queryOrders(QueryInfo qi, String accountId, HttpHeaders headers);

    Response queryOrdersForRefresh(QueryInfo qi, String accountId, HttpHeaders headers);

    Response alterOrder(OrderAlterInfo oai, HttpHeaders headers);

    Response queryAlreadySoldOrders(Date travelDate, String trainNumber, HttpHeaders headers);

    Response checkSecurityAboutOrder(Date checkDate, String accountId, HttpHeaders headers);

    void initOrder(Order order, HttpHeaders headers);
}
