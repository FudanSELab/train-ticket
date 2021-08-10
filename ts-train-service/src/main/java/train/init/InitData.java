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

package train.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import train.entity.TrainType;
import train.service.TrainService;


@Component
public class InitData implements CommandLineRunner {

    @Autowired
    TrainService service;

    @Override
    public void run(String... args) throws Exception {
        TrainType info = new TrainType();

        info.setId("GaoTieOne");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(250);
        service.create(info, null);

        info.setId("GaoTieTwo");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(200);
        service.create(info, null);

        info.setId("DongCheOne");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(180);
        service.create(info, null);

        info.setId("ZhiDa");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(120);
        service.create(info, null);

        info.setId("TeKuai");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(120);
        service.create(info, null);

        info.setId("KuaiSu");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(90);
        service.create(info, null);
    }
}
