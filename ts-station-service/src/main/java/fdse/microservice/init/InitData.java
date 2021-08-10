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

package fdse.microservice.init;

import fdse.microservice.entity.Station;
import fdse.microservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    StationService service;

    @Override
    public void run(String... args) throws Exception{
        Station info = new Station();

        info.setId("shanghai");
        info.setName("Shang Hai");
        info.setStayTime(10);
        service.create(info,null);

        info.setId("shanghaihongqiao");
        info.setName("Shang Hai Hong Qiao");
        info.setStayTime(10);
        service.create(info,null);

        info.setId("taiyuan");
        info.setName("Tai Yuan");
        info.setStayTime(5);
        service.create(info,null);

        info.setId("beijing");
        info.setName("Bei Jing");
        info.setStayTime(10);
        service.create(info,null);

        info.setId("nanjing");
        info.setName("Nan Jing");
        info.setStayTime(8);
        service.create(info,null);

        info.setId("shijiazhuang");
        info.setName("Shi Jia Zhuang");
        info.setStayTime(8);
        service.create(info,null);

        info.setId("xuzhou");
        info.setName("Xu Zhou");
        info.setStayTime(7);
        service.create(info,null);


        info.setId("jinan");
        info.setName("Ji Nan");
        info.setStayTime(5);
        service.create(info,null);

        info.setId("hangzhou");
        info.setName("Hang Zhou");
        info.setStayTime(9);
        service.create(info,null);

        info.setId("jiaxingnan");
        info.setName("Jia Xing Nan");
        info.setStayTime(2);
        service.create(info,null);

        info.setId("zhenjiang");
        info.setName("Zhen Jiang");
        info.setStayTime(2);
        service.create(info,null);

        info.setId("wuxi");
        info.setName("Wu Xi");
        info.setStayTime(3);
        service.create(info,null);

        info.setId("suzhou");
        info.setName("Su Zhou");
        info.setStayTime(3);
        service.create(info,null);

    }
}
