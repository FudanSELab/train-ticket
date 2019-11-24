package travelto.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import travelto.entity.TravelInfo;
import travelto.service.Travel2Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * InitData class
 *
 * @author fdu
 * @date 2019/11/10
 */

@Component
public class InitData implements CommandLineRunner {

    public static final String SH = "shanghai";
    public static final String BJ = "beijing";
    public static final String NJ = "nanjing";

    @Autowired
    Travel2Service service;

    private static Date str2Date(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM ddHH:mm:ss 'GMT+0800' yyyy", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            //error handle
        }
        return date;
    }


    public TravelInfo buildTravelInfo(String... args) {
        TravelInfo info = new TravelInfo();
        info.setTripId(args[0]);
        info.setTrainTypeId(args[1]);
        info.setRouteId(args[2]);
        info.setStartingStationId(args[3]);
        info.setStationsId(args[4]);
        info.setTerminalStationId(args[5]);
        String stime = args[6];
        String etime = args[7];
        info.setStartingTime(str2Date(stime));
        info.setEndTime(str2Date(etime));
        return info;
    }

    @Override
    public void run(String... args) throws Exception {
        service.create(buildTravelInfo("Z1234", "ZhiDa", "0b23bd3e-876a-4af3-b920-c50a90c90b04",
                SH, SH, BJ, "Mon 5 04 09:51:52 GMT+0800 2013", "Mon 5 04 15:51:52 GMT+0800 2013"), null);

        service.create(buildTravelInfo("Z1235", "Z1235", "9fc9c261-3263-4bfa-82f8-bb44e06b2f52",
                SH, NJ, BJ, "Mon 5 04 11:31:52 GMT+0800 2013", "Mon 5 04 17:51:52 GMT+0800 2013"), null);

        service.create(buildTravelInfo("Z1236", "ZhiDa", "d693a2c5-ef87-4a3c-bef8-600b43f62c68",
                SH, NJ, BJ, "Mon 5 04 7:05:52 GMT+0800 2013", "Mon 5 04 12:51:52 GMT+0800 2013"), null);

        service.create(buildTravelInfo("T1235", "TeKuai", "20eb7122-3a11-423f-b10a-be0dc5bce7db",
                SH, NJ, BJ, "Mon 5 04 08:31:52 GMT+0800 2013", "Mon 5 04 17:21:52 GMT+0800 2013"), null);

        service.create(buildTravelInfo("K1345", "KuaiSu", "1367db1f-461e-4ab7-87ad-2bcc05fd9cb7",
                SH, NJ, BJ, "Mon 5 04 07:51:52 GMT+0800 2013", "Mon 5 04 19:59:52 GMT+0800 2013"), null);
    }
}
