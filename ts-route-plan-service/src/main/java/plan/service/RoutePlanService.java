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

package plan.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import plan.entity.RoutePlanInfo;

/**
 * @author fdse
 */
public interface RoutePlanService {

    /**
     * search the cheapest result with route plan info
     *
     * @param info route plan info
     * @param headers headers
     * @return Response
     */
    Response searchCheapestResult(RoutePlanInfo info, HttpHeaders headers);

    /**
     * search the quickest result with route plan info
     *
     * @param info route plan info
     * @param headers headers
     * @return Response
     */
    Response searchQuickestResult(RoutePlanInfo info, HttpHeaders headers);

    /**
     * search min stop-station with route plan info
     *
     * @param info route plan info
     * @param headers headers
     * @return Response
     */
    Response searchMinStopStations(RoutePlanInfo info, HttpHeaders headers);

}
