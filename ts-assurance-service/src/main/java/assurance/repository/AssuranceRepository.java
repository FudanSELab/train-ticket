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

package assurance.repository;

import assurance.entity.Assurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author fdse
 */
@Repository
public interface AssuranceRepository  extends MongoRepository<Assurance, String> {

    /**
     * find by id
     *
     * @param id id
     * @return Assurance
     */
    Assurance findById(UUID id);

    /**
     * find by order id
     *
     * @param orderId order id
     * @return Assurance
     */
    @Query("{ 'orderId' : ?0 }")
    Assurance findByOrderId(UUID orderId);

    /**
     * delete by id
     *
     * @param id id
     * @return null
     */
    void deleteById(UUID id);

    /**
     * remove assurance by order id
     *
     * @param orderId order id
     * @return null
     */
    void removeAssuranceByOrderId(UUID orderId);

    /**
     * find all
     *
     * @return ArrayList<Assurance>
     */
    @Override
    ArrayList<Assurance> findAll();
}
