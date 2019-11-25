package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.QueryInfo;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class QueryInfoTest {

    @Test
    void getLoginId() {
        QueryInfo quertI = new QueryInfo();
        quertI.setLoginId("Test");
        Assert.assertEquals("Test",quertI.getLoginId());
    }

    @Test
    void setLoginId() {
        QueryInfo quertI = new QueryInfo();
        quertI.setLoginId("Test");
        Assert.assertEquals("Test",quertI.getLoginId());
    }

    @Test
    void getTravelDateStart() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableTravelDateQuery();
        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getTravelDateStart().toString());

    }

    @Test
    void getTravelDateEnd() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableTravelDateQuery();
        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 04:01:01 CST 2014",quertI.getTravelDateEnd().toString());
    }

    @Test
    void getBoughtDateStart() {

        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableBoughtDateQuery();
        quertI.enableBoughtDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getBoughtDateStart().toString());
    }

    @Test
    void getBoughtDateEnd() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableBoughtDateQuery();
        quertI.enableBoughtDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 04:01:01 CST 2014",quertI.getBoughtDateEnd().toString());
    }

    @Test
    void getState() {
        QueryInfo test = new QueryInfo();
        test.enableStateQuery(1);
        Assert.assertEquals(1,test.getState());
    }

    @Test
    void enableTravelDateQuery() {

        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableTravelDateQuery();
        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getTravelDateStart().toString());

    }

    @Test
    void disableTravelDateQuery() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableTravelDateQuery();
        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getTravelDateStart().toString());
    }

    @Test
    void enableBoughtDateQuery() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableTravelDateQuery();
        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getTravelDateStart().toString());
    }

    @Test
    void disableBoughtDateQuery() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();

        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getTravelDateStart().toString());
        quertI.disableBoughtDateQuery();
        Assert.assertEquals(false,quertI.isEnableBoughtDateQuery());
    }

    @Test
    void enableStateQuery() {

        QueryInfo test = new QueryInfo();
        test.disableStateQuery();
        test.enableStateQuery(1);
        Assert.assertEquals(1,test.getState());
    }

    @Test
    void disableStateQuery() {
        QueryInfo test = new QueryInfo();
        test.disableStateQuery();
        test.enableStateQuery(1);
        test.disableStateQuery();

    }



    @Test
    void isEnableTravelDateQuery() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableTravelDateQuery();
        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getTravelDateStart().toString());
    }

    @Test
    void isEnableBoughtDateQuery() {
        QueryInfo quertI = new QueryInfo();
        quertI.disableTravelDateQuery();
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        cld.set(2014,11,2,4,1,1);
        Date endtime = cld.getTime();
        quertI.disableTravelDateQuery();
        quertI.enableTravelDateQuery(starttime,endtime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",quertI.getTravelDateStart().toString());
    }

    @Test
    void isEnableStateQuery() {
        QueryInfo test = new QueryInfo();
        test.disableStateQuery();
        test.enableStateQuery(1);
        Assert.assertEquals(1,test.getState());
    }
}