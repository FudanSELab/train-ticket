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

package adminroute.service;

import adminroute.entity.Route;
import adminroute.entity.RouteInfo;
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

@RunWith(JUnit4.class)
public class AdminRouteServiceImplTest {

    @InjectMocks
    private AdminRouteServiceImpl adminRouteServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);
    private Response response = new Response();
    private ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllRoutes() {
        Mockito.when(restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes",
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re);
        Response result = adminRouteServiceImpl.getAllRoutes(headers);
        Assert.assertEquals(new Response<>(null, null, null), result);
    }

    @Test
    public void testCreateAndModifyRoute() {
        RouteInfo request = new RouteInfo();
        HttpEntity requestEntity2 = new HttpEntity(request, headers);
        ResponseEntity<Response<Route>> re2 = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes",
                HttpMethod.POST,
                requestEntity2,
                new ParameterizedTypeReference<Response<Route>>() {
                })).thenReturn(re2);
        Response result = adminRouteServiceImpl.createAndModifyRoute(request, headers);
        Assert.assertEquals(new Response<>(null, null, null), result);
    }

    @Test
    public void testDeleteRoute() {
        Mockito.when(restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes/" + "routeId",
                HttpMethod.DELETE,
                requestEntity,
                Response.class)).thenReturn(re);
        Response result = adminRouteServiceImpl.deleteRoute("routeId", headers);
        Assert.assertEquals(new Response<>(null, null, null), result);
    }

}
