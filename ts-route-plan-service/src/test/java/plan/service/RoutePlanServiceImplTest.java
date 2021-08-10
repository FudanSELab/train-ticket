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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import plan.entity.Route;
import plan.entity.RoutePlanInfo;
import plan.entity.Trip;
import plan.entity.TripResponse;

import java.util.ArrayList;
import java.util.Date;

@RunWith(JUnit4.class)
public class RoutePlanServiceImplTest {

    @InjectMocks
    private RoutePlanServiceImpl routePlanServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchCheapestResult() {
        RoutePlanInfo info = new RoutePlanInfo("form_station", "to_station", new Date(), 1);
        //mock getTripFromHighSpeedTravelServive() and getTripFromNormalTrainTravelService()
        ArrayList<TripResponse> tripResponses = new ArrayList<>();
        Response<ArrayList<TripResponse>> response1 = new Response<>(null, null, tripResponses);
        ResponseEntity<Response<ArrayList<TripResponse>>> re1 = new ResponseEntity<>(response1, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re1);
        Response result = routePlanServiceImpl.searchCheapestResult(info, headers);
        Assert.assertEquals(new Response<>(1, "Success", new ArrayList<>()), result);
    }

    @Test
    public void testSearchQuickestResult() {
        RoutePlanInfo info = new RoutePlanInfo("form_station", "to_station", new Date(), 1);
        //mock getTripFromHighSpeedTravelServive() and getTripFromNormalTrainTravelService()
        ArrayList<TripResponse> tripResponses = new ArrayList<>();
        Response<ArrayList<TripResponse>> response1 = new Response<>(null, null, tripResponses);
        ResponseEntity<Response<ArrayList<TripResponse>>> re1 = new ResponseEntity<>(response1, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re1);
        Response result = routePlanServiceImpl.searchQuickestResult(info, headers);
        Assert.assertEquals(new Response<>(1, "Success", new ArrayList<>()), result);
    }

    @Test
    public void testSearchMinStopStations() {
        RoutePlanInfo info = new RoutePlanInfo("form_station", "to_station", new Date(), 1);

        Response<String> response = new Response(null, null, "");
        ResponseEntity<Response<String>> re = new ResponseEntity<>(response, HttpStatus.OK);

        ArrayList<Route> routeArrayList = new ArrayList<>();
        Response<ArrayList<Route>> response2 = new Response<>(null, null, routeArrayList);
        ResponseEntity<Response<ArrayList<Route>>> re2 = new ResponseEntity<>(response2, HttpStatus.OK);

        ArrayList<ArrayList<Trip>> tripLists = new ArrayList<>();
        Response<ArrayList<ArrayList<Trip>>> response3 = new Response<>(null, null, tripLists);
        ResponseEntity<Response<ArrayList<ArrayList<Trip>>>> re3 = new ResponseEntity<>(response3, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re).thenReturn(re).thenReturn(re2).thenReturn(re3).thenReturn(re3);
        Response result = routePlanServiceImpl.searchMinStopStations(info, headers);
        Assert.assertEquals("Success.", result.getMsg());
    }

}
