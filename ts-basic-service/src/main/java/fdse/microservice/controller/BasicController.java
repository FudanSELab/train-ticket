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

package fdse.microservice.controller;

import fdse.microservice.entity.Travel;
import fdse.microservice.service.BasicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Chenjie
 * @date 2017/6/6.
 */
@RestController
@RequestMapping("/api/v1/basicservice")

public class BasicController {

    @Autowired
    BasicService service;

    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Basic Service ] !";
    }

    @PostMapping(value = "/basic/travel")
    public HttpEntity queryForTravel(@RequestBody Travel info, @RequestHeader HttpHeaders headers) {
        // TravelResult
        logger.info("Query for travel: {}", info.toString());
        return ok(service.queryForTravel(info, headers));
    }

    @GetMapping(value = "/basic/{stationName}")
    public HttpEntity queryForStationId(@PathVariable String stationName, @RequestHeader HttpHeaders headers) {
        // String id
        logger.info("Query for stationId by stationName: {}", stationName);
        return ok(service.queryForStationId(stationName, headers));
    }

}
