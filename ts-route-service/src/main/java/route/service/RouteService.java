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

package route.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import route.entity.*;


/**
 * @author fdse
 */
public interface RouteService {

    /**
     * get route with id
     *
     * @param startId start station id
     * @param terminalId terminal station id
     * @param headers headers
     * @return Response
     */
    Response getRouteByStartAndTerminal(String startId, String terminalId, HttpHeaders headers);

    /**
     * get all routes
     *
     * @param headers headers
     * @return Response
     */
    Response getAllRoutes(HttpHeaders headers);

    /**
     * get route by id
     *
     * @param routeId route id
     * @param headers headers
     * @return Response
     */
    Response getRouteById(String routeId, HttpHeaders headers);

    /**
     * delete route by id
     *
     * @param routeId route id
     * @param headers headers
     * @return Response
     */
    Response deleteRoute(String routeId, HttpHeaders headers);

    /**
     * create route and modify
     *
     * @param info info
     * @param headers headers
     * @return Response
     */
    Response createAndModify(RouteInfo info, HttpHeaders headers);

}
