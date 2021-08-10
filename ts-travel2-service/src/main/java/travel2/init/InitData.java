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

package travel2.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import travel2.entity.TravelInfo;
import travel2.service.Travel2Service;
import java.util.Date;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    Travel2Service service;

    String zhiDa = "ZhiDa";
    String shanghai = "shanghai";
    String nanjing = "nanjing";
    String beijing = "beijing";

    @Override
    public void run(String... args)throws Exception{
        TravelInfo info = new TravelInfo();

        info.setTripId("Z1234");
        info.setTrainTypeId(zhiDa);
        info.setRouteId("0b23bd3e-876a-4af3-b920-c50a90c90b04");
        info.setStartingStationId(shanghai);
        info.setStationsId(nanjing);
        info.setTerminalStationId(beijing);
        info.setStartingTime(new Date("Mon May 04 09:51:52 GMT+0800 2013")); //NOSONAR
        info.setEndTime(new Date("Mon May 04 15:51:52 GMT+0800 2013")); //NOSONAR
        service.create(info,null);

        info.setTripId("Z1235");
        info.setTrainTypeId(zhiDa);
        info.setRouteId("9fc9c261-3263-4bfa-82f8-bb44e06b2f52");
        info.setStartingStationId(shanghai);
        info.setStationsId(nanjing);
        info.setTerminalStationId(beijing);
        info.setStartingTime(new Date("Mon May 04 11:31:52 GMT+0800 2013")); //NOSONAR
        info.setEndTime(new Date("Mon May 04 17:51:52 GMT+0800 2013")); //NOSONAR
        service.create(info,null);

        info.setTripId("Z1236");
        info.setTrainTypeId(zhiDa);
        info.setRouteId("d693a2c5-ef87-4a3c-bef8-600b43f62c68");
        info.setStartingStationId(shanghai);
        info.setStationsId(nanjing);
        info.setTerminalStationId(beijing);
        info.setStartingTime(new Date("Mon May 04 7:05:52 GMT+0800 2013")); //NOSONAR
        info.setEndTime(new Date("Mon May 04 12:51:52 GMT+0800 2013")); //NOSONAR
        service.create(info,null);

        info.setTripId("T1235");
        info.setTrainTypeId("TeKuai");
        info.setRouteId("20eb7122-3a11-423f-b10a-be0dc5bce7db");
        info.setStartingStationId(shanghai);
        info.setStationsId(nanjing);
        info.setTerminalStationId(beijing);
        info.setStartingTime(new Date("Mon May 04 08:31:52 GMT+0800 2013")); //NOSONAR
        info.setEndTime(new Date("Mon May 04 17:21:52 GMT+0800 2013")); //NOSONAR
        service.create(info,null);

        info.setTripId("K1345");
        info.setTrainTypeId("KuaiSu");
        info.setRouteId("1367db1f-461e-4ab7-87ad-2bcc05fd9cb7");
        info.setStartingStationId(shanghai);
        info.setStationsId(nanjing);
        info.setTerminalStationId(beijing);
        info.setStartingTime(new Date("Mon May 04 07:51:52 GMT+0800 2013")); //NOSONAR
        info.setEndTime(new Date("Mon May 04 19:59:52 GMT+0800 2013")); //NOSONAR
        service.create(info,null);
    }
}
