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

package other.repository;

import other.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Repository
public interface OrderOtherRepository extends MongoRepository<Order, String> {

    /**
     * find order by id
     *
     * @param id id
     * @return Order
     */
    @Query("{ 'id': ?0 }")
    Order findById(UUID id);

    /**
     * find all orders
     *
     * @return ArrayList<Order>
     */
    @Override
    ArrayList<Order> findAll();

    /**
     * find orders by account id
     *
     * @param accountId account id
     * @return ArrayList<Order>
     */
    @Query("{ 'accountId' : ?0 }")
    ArrayList<Order> findByAccountId(UUID accountId);

    /**
     * find orders by travel date and train number
     *
     * @param travelDate travel date
     * @param trainNumber train number
     * @return ArrayList<Order>
     */
    @Query("{ 'travelDate' : ?0 , trainNumber : ?1 }")
    ArrayList<Order> findByTravelDateAndTrainNumber(Date travelDate, String trainNumber);

    /**
     * delete order by id
     *
     * @param id id
     * @return null
     */
    void deleteById(UUID id);
}
