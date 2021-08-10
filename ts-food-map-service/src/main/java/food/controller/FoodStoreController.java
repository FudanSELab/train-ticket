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

package food.controller;

import food.service.FoodMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/foodmapservice")
public class FoodStoreController {

    @Autowired
    FoodMapService foodMapService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodStoreController.class);

    @GetMapping(path = "/foodstores/welcome")
    public String home() {
        return "Welcome to [ Food store Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/foodstores")
    public HttpEntity getAllFoodStores(@RequestHeader HttpHeaders headers) {
        FoodStoreController.LOGGER.info("[Food Map Service][Get All FoodStores]");
        return ok(foodMapService.listFoodStores(headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/foodstores/{stationId}")
    public HttpEntity getFoodStoresOfStation(@PathVariable String stationId, @RequestHeader HttpHeaders headers) {
        FoodStoreController.LOGGER.info("[Food Map Service][Get FoodStores By StationId]");
        return ok(foodMapService.listFoodStoresByStationId(stationId, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/foodstores")
    public HttpEntity getFoodStoresByStationIds(@RequestBody List<String> stationIdList) {
        FoodStoreController.LOGGER.info("[Food Map Service][Get FoodStores By StationIds]");
        return ok(foodMapService.getFoodStoresByStationIds(stationIdList));
    }
}
