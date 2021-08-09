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

package fdse.microservice.service;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.*;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface StationService {
    //CRUD
    Response create(Station info, HttpHeaders headers);

    boolean exist(String stationName, HttpHeaders headers);

    Response update(Station info, HttpHeaders headers);

    Response delete(Station info, HttpHeaders headers);

    Response query(HttpHeaders headers);

    Response queryForId(String stationName, HttpHeaders headers);

    Response queryForIdBatch(List<String> nameList, HttpHeaders headers);

    Response queryById(String stationId, HttpHeaders headers);

    Response queryByIdBatch(List<String> stationIdList, HttpHeaders headers);

}
