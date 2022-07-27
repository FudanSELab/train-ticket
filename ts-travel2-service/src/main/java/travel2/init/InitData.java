package travel2.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import edu.fudan.common.entity.TravelInfo;
import travel2.service.TravelService;
import java.util.Date;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    TravelService service;

    String zhiDa = "ZhiDa";
    String shanghai = "shanghai";
    String nanjing = "nanjing";
    String beijing = "beijing";

    @Override
    public void run(String... args)throws Exception{
        TravelInfo info = new TravelInfo();

        info.setTripId("Z1234");
        info.setTrainTypeName(zhiDa);
        info.setRouteId("0b23bd3e-876a-4af3-b920-c50a90c90b04");
        info.setStartStationName(shanghai);
        info.setStationsName(nanjing);
        info.setTerminalStationName(beijing);
        info.setStartTime("2013-05-04 09:51:52"); //NOSONAR
        info.setEndTime("2013-05-04 15:51:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("Z1235");
        info.setTrainTypeName(zhiDa);
        info.setRouteId("9fc9c261-3263-4bfa-82f8-bb44e06b2f52");
        info.setStartStationName(shanghai);
        info.setStationsName(nanjing);
        info.setTerminalStationName(beijing);
        info.setStartTime("2013-05-04 11:31:52"); //NOSONAR
        info.setEndTime("2013-05-04 17:51:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("Z1236");
        info.setTrainTypeName(zhiDa);
        info.setRouteId("d693a2c5-ef87-4a3c-bef8-600b43f62c68");
        info.setStartStationName(shanghai);
        info.setStationsName(nanjing);
        info.setTerminalStationName(beijing);
        info.setStartTime("2013-05-04 7:05:52"); //NOSONAR
        info.setEndTime("2013-05-04 12:51:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("T1235");
        info.setTrainTypeName("TeKuai");
        info.setRouteId("20eb7122-3a11-423f-b10a-be0dc5bce7db");
        info.setStartStationName(shanghai);
        info.setStationsName(nanjing);
        info.setTerminalStationName(beijing);
        info.setStartTime("2013-05-04 08:31:52"); //NOSONAR
        info.setEndTime("2013-05-04 17:21:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("K1345");
        info.setTrainTypeName("KuaiSu");
        info.setRouteId("1367db1f-461e-4ab7-87ad-2bcc05fd9cb7");
        info.setStartStationName(shanghai);
        info.setStationsName(nanjing);
        info.setTerminalStationName(beijing);
        info.setStartTime("2013-05-04 07:51:52"); //NOSONAR
        info.setEndTime("2013-05-04 19:59:52"); //NOSONAR
        service.create(info,null);
    }
}
