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

package ticketinfo.service;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ticketinfo.entity.Travel;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
@Service
public class TicketInfoServiceImpl implements TicketInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response queryForTravel(Travel info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-basic-service:15680/api/v1/basicservice/basic/travel",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        return re.getBody();
    }

    @Override
    public Response queryForStationId(String name, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-basic-service:15680/api/v1/basicservice/basic/" + name,
                HttpMethod.GET,
                requestEntity,
                Response.class);

        return re.getBody();
    }
}
