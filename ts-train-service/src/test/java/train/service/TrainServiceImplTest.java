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

package train.service;

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
import train.entity.TrainType;
import train.repository.TrainTypeRepository;

@RunWith(JUnit4.class)
public class TrainServiceImplTest {

    @InjectMocks
    private TrainServiceImpl trainServiceImpl;

    @Mock
    private TrainTypeRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate1() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        Mockito.when(repository.save(Mockito.any(TrainType.class))).thenReturn(null);
        boolean result = trainServiceImpl.create(trainType, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testCreate2() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(trainType);
        boolean result = trainServiceImpl.create(trainType, headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testRetrieve1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        TrainType result = trainServiceImpl.retrieve("id", headers);
        Assert.assertNull(result);
    }

    @Test
    public void testRetrieve2() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(trainType);
        TrainType result = trainServiceImpl.retrieve("id", headers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdate1() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(trainType);
        Mockito.when(repository.save(Mockito.any(TrainType.class))).thenReturn(null);
        boolean result = trainServiceImpl.update(trainType, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testUpdate2() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        boolean result = trainServiceImpl.update(trainType, headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testDelete1() {
        TrainType trainType = new TrainType();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(trainType);
        Mockito.doNothing().doThrow(new RuntimeException()).when(repository).deleteById(Mockito.anyString());
        boolean result = trainServiceImpl.delete("id", headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testDelete2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        boolean result = trainServiceImpl.delete("id", headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testQuery() {
        Mockito.when(repository.findAll()).thenReturn(null);
        Assert.assertNull(trainServiceImpl.query(headers));
    }

}
