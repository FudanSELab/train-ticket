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

package execute.controller;

import execute.serivce.ExecuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/executeservice")
public class ExecuteControlller {

    @Autowired
    private ExecuteService executeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteControlller.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Execute Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/execute/execute/{orderId}")
    public HttpEntity executeTicket(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        ExecuteControlller.LOGGER.info("[Execute] Id: {}", orderId);
        // null
        return ok(executeService.ticketExecute(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/execute/collected/{orderId}")
    public HttpEntity collectTicket(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        ExecuteControlller.LOGGER.info("[Collect] Id: {}", orderId);
        // null
        return ok(executeService.ticketCollect(orderId, headers));
    }

}
