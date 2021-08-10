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

package user.service;

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
import user.dto.AuthDto;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;
import user.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        UserDto userDto = new UserDto(UUID.randomUUID(), "user_name", "xxx", 0, 1, "", "");
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(null);

        //mock createDefaultAuthUser()
        Response<ArrayList<AuthDto>> response1 = new Response<>();
        ResponseEntity<Response<ArrayList<AuthDto>>> re1 = new ResponseEntity<>(response1, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re1);

        User user = new User();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Response result = userServiceImpl.saveUser(userDto, headers);
        Assert.assertEquals(new Response<>(1, "REGISTER USER SUCCESS", user), result);
    }

    @Test
    public void testGetAllUsers1() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Response result = userServiceImpl.getAllUsers(headers);
        Assert.assertEquals(new Response<>(1, "Success", users), result);
    }

    @Test
    public void testGetAllUsers2() {
        Mockito.when(userRepository.findAll()).thenReturn(null);
        Response result = userServiceImpl.getAllUsers(headers);
        Assert.assertEquals(new Response<>(0, "NO User", null), result);
    }

    @Test
    public void testFindByUserName1() {
        User user = new User();
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(user);
        Response result = userServiceImpl.findByUserName("user_name", headers);
        Assert.assertEquals(new Response<>(1, "Find User Success", user), result);
    }

    @Test
    public void testFindByUserName2() {
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(null);
        Response result = userServiceImpl.findByUserName("user_name", headers);
        Assert.assertEquals(new Response<>(0, "No User", null), result);
    }

    @Test
    public void testFindByUserId1() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(user);
        Response result = userServiceImpl.findByUserId(userId.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Find User Success", user), result);
    }

    @Test
    public void testFindByUserId2() {
        UUID userId = UUID.randomUUID();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(null);
        Response result = userServiceImpl.findByUserId(userId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "No User", null), result);
    }

    @Test
    public void testDeleteUser1() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(user);
        HttpEntity<Response> httpEntity = new HttpEntity<>(headers);
        Mockito.when(restTemplate.exchange("http://ts-auth-service:12340/api/v1" + "/users/" + userId,
                HttpMethod.DELETE,
                httpEntity,
                Response.class)).thenReturn(null);
        Mockito.doNothing().doThrow(new RuntimeException()).when(userRepository).deleteByUserId(Mockito.any(UUID.class));
        Response result = userServiceImpl.deleteUser(userId, headers);
        Assert.assertEquals(new Response<>(1, "DELETE SUCCESS", null), result);
    }

    @Test
    public void testDeleteUser2() {
        UUID userId = UUID.randomUUID();
        Mockito.when(userRepository.findByUserId(Mockito.any(UUID.class))).thenReturn(null);
        Response result = userServiceImpl.deleteUser(userId, headers);
        Assert.assertEquals(new Response<>(0, "USER NOT EXISTS", null), result);
    }

    @Test
    public void testUpdateUser1() {
        UserDto userDto = new UserDto();
        User oldUser = new User();
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(oldUser);
        Mockito.doNothing().doThrow(new RuntimeException()).when(userRepository).deleteByUserId(Mockito.any(UUID.class));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);
        Response result = userServiceImpl.updateUser(userDto, headers);
        Assert.assertEquals("SAVE USER SUCCESS", result.getMsg());
    }

    @Test
    public void testUpdateUser2() {
        UserDto userDto = new UserDto();
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(null);
        Response result = userServiceImpl.updateUser(userDto, headers);
        Assert.assertEquals(new Response(0, "USER NOT EXISTS", null), result);
    }

    @Test
    public void testDeleteUserAuth() {
        UUID userId = UUID.randomUUID();
        HttpEntity<Response> httpEntity = new HttpEntity<>(headers);
        Mockito.when(restTemplate.exchange("http://ts-auth-service:12340/api/v1" + "/users/" + userId,
                HttpMethod.DELETE,
                httpEntity,
                Response.class)).thenReturn(null);
        userServiceImpl.deleteUserAuth(userId, headers);
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class));
    }

}
