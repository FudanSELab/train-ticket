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

package route.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import route.entity.Route;
import java.util.ArrayList;

/**
 * @author fdse
 */
@Repository
public interface RouteRepository extends MongoRepository<Route, String> {

    /**
     * find route by id
     *
     * @param id id
     * @return Route
     */
    @Query("{ 'id': ?0 }")
    Route findById(String id);

    /**
     * find all routes
     *
     * @return ArrayList<Route>
     */
    @Override
    ArrayList<Route> findAll();

    /**
     * remove route via id
     *
     * @param id id
     */
    void removeRouteById(String id);

    /**
     * return route with id from StartStationId to TerminalStationId
     *
     * @param startingId  Start Station Id
     * @param terminalId  Terminal Station Id
     * @return ArrayList<Route>
     */
    @Query("{ 'startStationId': ?0 , 'terminalStationId': ?1 }")
    ArrayList<Route> findByStartStationIdAndTerminalStationId(String startingId, String terminalId);

}
