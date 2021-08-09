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

package assurance.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

/**
 * @author fdse
 */
public interface AssuranceService {

    /**
     * find assurance by id
     *
     * @param id id
     * @param headers headers
     * @return Response
     */
    Response findAssuranceById(UUID id, HttpHeaders headers);

    /**
     * find assurance by order id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response findAssuranceByOrderId(UUID orderId, HttpHeaders headers);

    /**
     * find assurance by type index, order id
     *
     * @param typeIndex type index
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response create(int typeIndex,String orderId , HttpHeaders headers);

    /**
     * delete by order id
     *
     * @param assuranceId assurance id
     * @param headers headers
     * @return Response
     */
    Response deleteById(UUID assuranceId, HttpHeaders headers);

    /**
     * delete by order id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response deleteByOrderId(UUID orderId, HttpHeaders headers);

    /**
     * modify by assurance id, order id, type index
     *
     * @param assuranceId assurace id
     * @param orderId order id
     * @param typeIndex type index
     * @param headers headers
     * @return Response
     */
    Response modify(String assuranceId, String orderId, int typeIndex , HttpHeaders headers);

    /**
     * get all assurances
     *
     * @param headers headers
     * @return Response
     */
    Response getAllAssurances(HttpHeaders headers);

    /**
     * get all assurance types
     *
     * @param headers headers
     * @return Response
     */
    Response getAllAssuranceTypes(HttpHeaders headers);
}
