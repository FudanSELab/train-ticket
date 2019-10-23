package travel.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import travel.entity.TravelInfo;
import travel.service.TravelService;

import java.util.Date;


@Component
public class InitData implements CommandLineRunner{

    @Autowired
    TravelService service;

    public void initializeInfo(TravelInfo info, String TripId, String TrainTypeId, String RouteId, String StartingStationId, String StationsId, String TerminalStationId, Date StartingTime, Date EndTime){
        info.setTripId(TripId);
        info.setTrainTypeId(TrainTypeId);
        info.setRouteId(RouteId);
        info.setStartingStationId(StartingStationId);
        info.setStationsId(StationsId);
        info.setTerminalStationId(TerminalStationId);
        info.setStartingTime(StartingTime);
        info.setEndTime(EndTime);
        service.create(info,null);
    }

    public void run(String... args)throws Exception{
        TravelInfo info = new TravelInfo();
        initializeInfo(info,"G1234","GaoTieOne","92708982-77af-4318-be25-57ccb0ff69ad","shanghai","suzhou","taiyuan", new Date("Mon May 04 09:00:00 GMT+0800 2013"), new Date("Mon May 04 15:51:52 GMT+0800 2013"));
        initializeInfo(info,"G1235","GaoTieOne","aefcef3f-3f42-46e8-afd7-6cb2a928bd3d", "shanghai","suzhou", "taiyuan",new Date("Mon May 04 12:00:00 GMT+0800 2013"),new Date("Mon May 04 17:51:52 GMT+0800 2013"));
        initializeInfo(info,"G1236","GaoTieOne","a3f256c1-0e43-4f7d-9c21-121bf258101f","shanghai","suzhou","taiyuan",new Date("Mon May 04 14:00:00 GMT+0800 2013"), new Date("Mon May 04 20:51:52 GMT+0800 2013"));
        initializeInfo(info,"G1237","GaoTieTwo","084837bb-53c8-4438-87c8-0321a4d09917","shanghai","suzhou","taiyuan",new Date("Mon May 04 08:00:00 GMT+0800 2013"), new Date("Mon May 04 17:21:52 GMT+0800 2013"));
        initializeInfo(info,"D1345","DongCheOne","f3d4d4ef-693b-4456-8eed-59c0d717dd08", "shanghai","suzhou","taiyuan",new Date("Mon May 04 07:00:00 GMT+0800 2013"), new Date("Mon May 04 19:59:52 GMT+0800 2013"));
    }
}
