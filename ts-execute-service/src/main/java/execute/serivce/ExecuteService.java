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

package execute.serivce;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface ExecuteService {

    /**
     * ticket execute by order id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response ticketExecute(String orderId, HttpHeaders headers);

    /**
     * ticker collect
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response ticketCollect(String orderId, HttpHeaders headers);

}
