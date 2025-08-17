package route.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import route.entity.RouteInfo;
import route.service.RouteService;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    RouteService routeService;

    String shanghai = "shanghai";
    String taiyuan = "taiyuan";
    String nanjing = "nanjing";

    @Override
    public void run(String... args)throws Exception{
        RouteInfo info = new RouteInfo();
        info.setId("0b23bd3e-876a-4af3-b920-c50a90c90b04");
        info.setStartStation(shanghai);
        info.setEndStation(taiyuan);
        info.setStationList("shanghai,nanjing,shijiazhuang,taiyuan");
        info.setDistanceList("0,350,1000,1300");
        routeService.createAndModify(info,null);

        info.setId("9fc9c261-3263-4bfa-82f8-bb44e06b2f52");
        info.setStartStation(nanjing);
        info.setEndStation("beijing");
        info.setStationList("nanjing,xuzhou,jinan,beijing");
        info.setDistanceList("0,500,700,1200");
        routeService.createAndModify(info,null);

        info.setId("d693a2c5-ef87-4a3c-bef8-600b43f62c68");
        info.setStartStation(taiyuan);
        info.setEndStation(shanghai);
        info.setStationList("taiyuan,shijiazhuang,nanjing,shanghai");
        info.setDistanceList("0,300,950,1300");
        routeService.createAndModify(info,null);


        info.setId("20eb7122-3a11-423f-b10a-be0dc5bce7db");
        info.setStartStation(shanghai);
        info.setEndStation(taiyuan);
        info.setStationList("shanghai,taiyuan");
        info.setDistanceList("0,1300");
        routeService.createAndModify(info,null);

        info.setId("1367db1f-461e-4ab7-87ad-2bcc05fd9cb7");
        info.setStartStation("shanghaihongqiao");
        info.setEndStation("hangzhou");
        info.setStationList("shanghaihongqiao,jiaxingnan,hangzhou");
        info.setDistanceList("0,150,300");
        routeService.createAndModify(info,null);

        info.setId("92708982-77af-4318-be25-57ccb0ff69ad");
        info.setStartStation(nanjing);
        info.setEndStation(shanghai);
        info.setStationList("nanjing,zhenjiang,wuxi,suzhou,shanghai");
        info.setDistanceList("0,100,150,200,250");
        routeService.createAndModify(info,null);

        info.setId("aefcef3f-3f42-46e8-afd7-6cb2a928bd3d");
        info.setStartStation(nanjing);
        info.setEndStation(shanghai);
        info.setStationList("nanjing,shanghai");
        info.setDistanceList("0,250");
        routeService.createAndModify(info,null);

        info.setId("a3f256c1-0e43-4f7d-9c21-121bf258101f");
        info.setStartStation(nanjing);
        info.setEndStation(shanghai);
        info.setStationList("nanjing,suzhou,shanghai");
        info.setDistanceList("0,200,250");
        routeService.createAndModify(info,null);

        info.setId("084837bb-53c8-4438-87c8-0321a4d09917");
        info.setStartStation("suzhou");
        info.setEndStation(shanghai);
        info.setStationList("suzhou,shanghai");
        info.setDistanceList("0,50");
        routeService.createAndModify(info,null);

        info.setId("f3d4d4ef-693b-4456-8eed-59c0d717dd08");
        info.setStartStation(shanghai);
        info.setEndStation("suzhou");
        info.setStationList("shanghai,suzhou");
        info.setDistanceList("0,50");
        routeService.createAndModify(info,null);

    }

}
