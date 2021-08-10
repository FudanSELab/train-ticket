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

package consignprice.controller;

import consignprice.entity.ConsignPrice;
import consignprice.service.ConsignPriceService;
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
@RequestMapping("/api/v1/consignpriceservice")
public class ConsignPriceController {

    @Autowired
    ConsignPriceService service;

    private static final Logger logger = LoggerFactory.getLogger(ConsignPriceController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ ConsignPrice Service ] !";
    }

    @GetMapping(value = "/consignprice/{weight}/{isWithinRegion}")
    public HttpEntity getPriceByWeightAndRegion(@PathVariable String weight, @PathVariable String isWithinRegion,
                                                @RequestHeader HttpHeaders headers) {
        logger.info("Get price by weight: {}, regin: {}", weight, isWithinRegion);
        return ok(service.getPriceByWeightAndRegion(Double.parseDouble(weight),
                Boolean.parseBoolean(isWithinRegion), headers));
    }

    @GetMapping(value = "/consignprice/price")
    public HttpEntity getPriceInfo(@RequestHeader HttpHeaders headers) {
        logger.info("Get price info");
        return ok(service.queryPriceInformation(headers));
    }

    @GetMapping(value = "/consignprice/config")
    public HttpEntity getPriceConfig(@RequestHeader HttpHeaders headers) {
        logger.info("Get price config");
        return ok(service.getPriceConfig(headers));
    }

    @PostMapping(value = "/consignprice")
    public HttpEntity modifyPriceConfig(@RequestBody ConsignPrice priceConfig,
                                        @RequestHeader HttpHeaders headers) {
        logger.info("Create and modify price, config: {}", priceConfig);
        return ok(service.createAndModifyPrice(priceConfig, headers));
    }
}
