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

package verifycode.controller;

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
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import verifycode.service.VerifyCodeService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.MarshalledObject;
import java.security.Principal;
import java.util.*;

@RunWith(JUnit4.class)
public class VerifyCodeControllerTest {

    @InjectMocks
    private VerifyCodeController verifyCodeController;

    @Mock
    private VerifyCodeService verifyCodeService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(verifyCodeController).build();
    }

    @Test
    public void testImageCode() throws Exception {
        Map<String, Object> map = new HashMap<>();
        BufferedImage image = new BufferedImage(60, 20, BufferedImage.TYPE_INT_RGB);
        map.put("strEnsure", "XYZ8");
        map.put("image", image);
        Mockito.when(verifyCodeService.getImageCode(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(OutputStream.class), Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.any(HttpHeaders.class))).thenReturn(map);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/verifycode/generate")).andReturn();
        Mockito.verify(verifyCodeService, Mockito.times(1)).getImageCode(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(OutputStream.class), Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void testVerifyCode() throws Exception {
        Mockito.when(verifyCodeService.verifyCode(Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/verifycode/verify/verifyCode"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(JSONObject.parseObject(result, Boolean.class));
    }

}
