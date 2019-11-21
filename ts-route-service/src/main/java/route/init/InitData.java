package route.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import route.entity.RouteInfo;
import route.service.RouteService;

/**
 * @author cw
 */
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    RouteService routeService;

    @Override
    public void run(String... args) throws Exception {
        String shanghai = "shanghai";
        String taiyuan = "taiyuan";
        String nanjing = "nanjing";
        String beijing = "beijing";
        String hongqiao = "shanghaihongqiao";
        String hangzhou = "hangzhou";
        String suzhou = "suzhou";
        RouteInfo i1 = new RouteInfo("0b23bd3e-876a-4af3-b920-c50a90c90b04",
                shanghai, taiyuan, "shanghai,nanjing,shijiazhuang,taiyuan", "0,350,1000,1300");
        routeService.createAndModify(i1, null);
        RouteInfo i2 = new RouteInfo("9fc9c261-3263-4bfa-82f8-bb44e06b2f52",
                nanjing, beijing, "nanjing,xuzhou,jinan,beijing", "0,500,700,1200");
        routeService.createAndModify(i2, null);

        routeService.createAndModify(new RouteInfo(
                "d693a2c5-ef87-4a3c-bef8-600b43f62c68", taiyuan, shanghai,
                "taiyuan,shijiazhuang,nanjing,shanghai", "0,300,950,1300"
        ), null);
        routeService.createAndModify(new RouteInfo(
                "20eb7122-3a11-423f-b10a-be0dc5bce7db", shanghai, taiyuan,
                "shanghai,taiyuan", "0,1300"
        ), null);

        routeService.createAndModify(new RouteInfo(
                "1367db1f-461e-4ab7-87ad-2bcc05fd9cb7", hongqiao, hangzhou, "shanghaihongqiao,jiaxingnan,hangzhou",
                "0,150,300"
        ), null);

        routeService.createAndModify(new RouteInfo(
                "92708982-77af-4318-be25-57ccb0ff69ad", nanjing, shanghai,
                "nanjing,zhenjiang,wuxi,suzhou,shanghai", "0,100,150,200,250"
        ), null);

        routeService.createAndModify(new RouteInfo(
                "aefcef3f-3f42-46e8-afd7-6cb2a928bd3d", nanjing, shanghai,
                "nanjing,shanghai", "0,250"
        ), null);

        routeService.createAndModify(new RouteInfo(
                "a3f256c1-0e43-4f7d-9c21-121bf258101f", nanjing, shanghai,
                "nanjing,suzhou,shanghai", "0,200,250"
        ), null);

        routeService.createAndModify(new RouteInfo(
                "084837bb-53c8-4438-87c8-0321a4d09917", suzhou, shanghai,
                "suzhou,shanghai", "0,50"
        ), null);

        routeService.createAndModify(new RouteInfo(
                "f3d4d4ef-693b-4456-8eed-59c0d717dd08", shanghai, suzhou, "shanghai,suzhou", "0,50"
        ), null);

    }

}
