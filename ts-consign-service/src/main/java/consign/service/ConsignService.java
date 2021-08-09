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

package consign.service;

import consign.entity.Consign;
import org.springframework.http.HttpHeaders;

import edu.fudan.common.util.Response;

import java.util.UUID;

/**
 * @author fdse
 */
public interface ConsignService {

    /**
     * insert consign record
     *
     * @param consignRequest consign request
     * @param headers headers
     * @return Response
     */
    Response insertConsignRecord(Consign consignRequest, HttpHeaders headers);

    /**
     * update consign record
     *
     * @param consignRequest consign request
     * @param headers headers
     * @return Response
     */
    Response updateConsignRecord(Consign consignRequest, HttpHeaders headers);

    /**
     * query by account id
     *
     * @param accountId account id
     * @param headers headers
     * @return Response
     */
    Response queryByAccountId(UUID accountId, HttpHeaders headers);

    /**
     * query by order id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response queryByOrderId(UUID orderId, HttpHeaders headers);

    /**
     * query by consignee
     *
     * @param consignee consignee
     * @param headers headers
     * @return Response
     */
    Response queryByConsignee(String consignee, HttpHeaders headers);
}
