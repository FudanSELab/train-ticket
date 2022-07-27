package travel.init;

import edu.fudan.common.entity.TravelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import travel.service.TravelService;

import java.util.Date;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner{

    @Autowired
    TravelService service;

    String gaoTieOne = "GaoTieOne";
    String shanghai = "shanghai";
    String suzhou = "suzhou";
    String taiyuan = "taiyuan";

    @Override
    public void run(String... args)throws Exception{
        TravelInfo info = new TravelInfo();

        info.setTripId("G1234");
        info.setTrainTypeName(gaoTieOne);
        info.setRouteId("92708982-77af-4318-be25-57ccb0ff69ad");
        info.setStartStationName(shanghai);
        info.setStationsName(suzhou);
        info.setTerminalStationName(taiyuan);
        info.setStartTime("2013-05-04 09:00:00"); //NOSONAR
        info.setEndTime("2013-05-04 15:51:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("G1235");
        info.setTrainTypeName(gaoTieOne);
        info.setRouteId("aefcef3f-3f42-46e8-afd7-6cb2a928bd3d");
        info.setStartStationName(shanghai);
        info.setStationsName(suzhou);
        info.setTerminalStationName(taiyuan);
        info.setStartTime("2013-05-04 12:00:00"); //NOSONAR
        info.setEndTime("2013-05-04 17:51:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("G1236");
        info.setTrainTypeName(gaoTieOne);
        info.setRouteId("a3f256c1-0e43-4f7d-9c21-121bf258101f");
        info.setStartStationName(shanghai);
        info.setStationsName(suzhou);
        info.setTerminalStationName(taiyuan);
        info.setStartTime("2013-05-04 14:00:00"); //NOSONAR
        info.setEndTime("2013-05-04 20:51:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("G1237");
        info.setTrainTypeName("GaoTieTwo");
        info.setRouteId("084837bb-53c8-4438-87c8-0321a4d09917");
        info.setStartStationName(shanghai);
        info.setStationsName(suzhou);
        info.setTerminalStationName(taiyuan);
        info.setStartTime("2013-05-04 08:00:00"); //NOSONAR
        info.setEndTime("2013-05-04 17:21:52"); //NOSONAR
        service.create(info,null);

        info.setTripId("D1345");
        info.setTrainTypeName("DongCheOne");
        info.setRouteId("f3d4d4ef-693b-4456-8eed-59c0d717dd08");
        info.setStartStationName(shanghai);
        info.setStationsName(suzhou);
        info.setTerminalStationName(taiyuan);
        info.setStartTime("2013-05-04 07:00:00"); //NOSONAR
        info.setEndTime("2013-05-04 19:59:52"); //NOSONAR
        service.create(info,null);
    }
}
