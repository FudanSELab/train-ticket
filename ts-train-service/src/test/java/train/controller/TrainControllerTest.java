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

package train.controller;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import train.entity.TrainType;
import train.service.TrainService;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class TrainControllerTest {

    @InjectMocks
    private TrainController trainController;

    @Mock
    private TrainService trainService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/trainservice/trains/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Train Service ] !"));
    }

    @Test
    public void testCreate1() throws Exception {
        TrainType trainType = new TrainType();
        Mockito.when(trainService.create(Mockito.any(TrainType.class), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String requestJson = JSONObject.toJSONString(trainType);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/trainservice/trains").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(new Response(1, "create success", null), JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testCreate2() throws Exception {
        TrainType trainType = new TrainType();
        Mockito.when(trainService.create(Mockito.any(TrainType.class), Mockito.any(HttpHeaders.class))).thenReturn(false);
        String requestJson = JSONObject.toJSONString(trainType);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/trainservice/trains").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("train type already exist", JSONObject.parseObject(result, Response.class).getMsg());
    }

    @Test
    public void testRetrieve1() throws Exception {
        Mockito.when(trainService.retrieve(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(null);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/trainservice/trains/wrong_id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(new Response(0, "here is no TrainType with the trainType id: wrong_id", null), JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testRetrieve2() throws Exception {
        TrainType trainType = new TrainType();
        Mockito.when(trainService.retrieve(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(trainType);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/trainservice/trains/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("success", JSONObject.parseObject(result, Response.class).getMsg());
    }

    @Test
    public void testUpdate1() throws Exception {
        TrainType trainType = new TrainType();
        Mockito.when(trainService.update(Mockito.any(TrainType.class), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String requestJson = JSONObject.toJSONString(trainType);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/trainservice/trains").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(new Response(1, "update success", true), JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testUpdate2() throws Exception {
        TrainType trainType = new TrainType();
        Mockito.when(trainService.update(Mockito.any(TrainType.class), Mockito.any(HttpHeaders.class))).thenReturn(false);
        String requestJson = JSONObject.toJSONString(trainType);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/trainservice/trains").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(new Response(0, "there is no trainType with the trainType id", false), JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDelete1() throws Exception {
        Mockito.when(trainService.delete(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/trainservice/trains/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(new Response(1, "delete success", true), JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDelete2() throws Exception {
        Mockito.when(trainService.delete(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(false);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/trainservice/trains/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(new Response(0, "there is no train according to id", null), JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testQuery1() throws Exception {
        List<TrainType> trainTypes = new ArrayList<>();
        trainTypes.add(new TrainType());
        Mockito.when(trainService.query(Mockito.any(HttpHeaders.class))).thenReturn(trainTypes);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/trainservice/trains"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("success", JSONObject.parseObject(result, Response.class).getMsg());
    }

    @Test
    public void testQuery2() throws Exception {
        List<TrainType> trainTypes = new ArrayList<>();
        Mockito.when(trainService.query(Mockito.any(HttpHeaders.class))).thenReturn(trainTypes);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/trainservice/trains"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("no content", JSONObject.parseObject(result, Response.class).getMsg());
    }

}
