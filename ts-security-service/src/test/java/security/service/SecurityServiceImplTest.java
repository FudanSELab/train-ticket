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

package security.service;

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
import security.entity.OrderSecurity;
import security.entity.SecurityConfig;
import security.repository.SecurityRepository;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(JUnit4.class)
public class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityServiceImpl;

    @Mock
    private SecurityRepository securityRepository;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllSecurityConfig1() {
        ArrayList<SecurityConfig> securityConfigs = new ArrayList<>();
        securityConfigs.add(new SecurityConfig());
        Mockito.when(securityRepository.findAll()).thenReturn(securityConfigs);
        Response result = securityServiceImpl.findAllSecurityConfig(headers);
        Assert.assertEquals(new Response<>(1, "Success", securityConfigs), result);
    }

    @Test
    public void testFindAllSecurityConfig2() {
        Mockito.when(securityRepository.findAll()).thenReturn(null);
        Response result = securityServiceImpl.findAllSecurityConfig(headers);
        Assert.assertEquals(new Response<>(0, "No Content", null), result);
    }

    @Test
    public void testAddNewSecurityConfig1() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findByName(Mockito.anyString())).thenReturn(sc);
        Response result = securityServiceImpl.addNewSecurityConfig(sc, headers);
        Assert.assertEquals(new Response<>(0, "Security Config Already Exist", null), result);
    }

    @Test
    public void testAddNewSecurityConfig2() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findByName(Mockito.anyString())).thenReturn(null);
        Mockito.when(securityRepository.save(Mockito.any(SecurityConfig.class))).thenReturn(null);
        Response result = securityServiceImpl.addNewSecurityConfig(sc, headers);
        Assert.assertEquals("Success", result.getMsg());
    }

    @Test
    public void testModifySecurityConfig1() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Response result = securityServiceImpl.modifySecurityConfig(sc, headers);
        Assert.assertEquals(new Response<>(0, "Security Config Not Exist", null), result);
    }

    @Test
    public void testModifySecurityConfig2() {
        SecurityConfig sc = new SecurityConfig();
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(sc);
        Mockito.when(securityRepository.save(Mockito.any(SecurityConfig.class))).thenReturn(null);
        Response result = securityServiceImpl.modifySecurityConfig(sc, headers);
        Assert.assertEquals(new Response<>(1, "Success", sc), result);
    }

    @Test
    public void testDeleteSecurityConfig1() {
        UUID id = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(securityRepository).deleteById(Mockito.any(UUID.class));
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Response result = securityServiceImpl.deleteSecurityConfig(id.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Success", id.toString()), result);
    }

    @Test
    public void testDeleteSecurityConfig2() {
        UUID id = UUID.randomUUID();
        SecurityConfig sc = new SecurityConfig();
        Mockito.doNothing().doThrow(new RuntimeException()).when(securityRepository).deleteById(Mockito.any(UUID.class));
        Mockito.when(securityRepository.findById(Mockito.any(UUID.class))).thenReturn(sc);
        Response result = securityServiceImpl.deleteSecurityConfig(id.toString(), headers);
        Assert.assertEquals("Reason Not clear", result.getMsg());
    }

    @Test
    public void testCheck() {
        //mock getSecurityOrderInfoFromOrder() and getSecurityOrderOtherInfoFromOrder()
        OrderSecurity orderSecurity = new OrderSecurity(1, 1);
        Response<OrderSecurity> response1 = new Response<>(null, null, orderSecurity);
        ResponseEntity<Response<OrderSecurity>> re1 = new ResponseEntity<>(response1, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re1);

        SecurityConfig securityConfig = new SecurityConfig();
        securityConfig.setValue("2");
        Mockito.when(securityRepository.findByName(Mockito.anyString())).thenReturn(securityConfig);
        Response result = securityServiceImpl.check("account_id", headers);
        Assert.assertEquals(new Response<>(1, "Success.r", "account_id"), result);
    }

}
