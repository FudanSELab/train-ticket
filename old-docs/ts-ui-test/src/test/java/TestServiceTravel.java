import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class TestServiceTravel {
    private WebDriver driver;
    private String baseUrl;
    public class TravelInfo{
        String tripId;
        String trainTypeId;
        String startStationName;
        String stationsId;
        String terminalStationId;
        String startingTime;
        String endTime;
        TravelInfo (
                String tripId,
                String trainTypeId,
                String startStationName,
                String stationsId,
                String terminalStationId,
                String startingTime,
                String endTime
        ){
            this.tripId = tripId;
            this.trainTypeId = trainTypeId;
            this.startStationName = startingStationId;
            this.stationsId = stationsId;
            this.terminalStationId = terminalStationId;
            this.startingTime = startingTime;
            this.endTime = endTime;
        }
    }
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @DataProvider(name="travel")
    public Object[][] Travel(){
        return new Object[][]{
                {new TravelInfo("G1234","GaoTieOne","shanghai","beijing","taiyuan","11:17","15:29")},
        };
    }
    @Test (dataProvider="travel")
    public void testTravel(TravelInfo travelinfo) throws Exception{
        driver.get(baseUrl + "/");

        driver.findElement(By.id("travel_update_tripId")).clear();
        driver.findElement(By.id("travel_update_tripId")).sendKeys(travelinfo.tripId);

        driver.findElement(By.id("travel_update_trainTypeId")).clear();
        driver.findElement(By.id("travel_update_trainTypeId")).sendKeys(travelinfo.trainTypeId);

        driver.findElement(By.id("travel_update_startingStationId")).clear();
        driver.findElement(By.id("travel_update_startingStationId")).sendKeys(travelinfo.startStationName);

        driver.findElement(By.id("travel_update_stationsId")).clear();
        driver.findElement(By.id("travel_update_stationsId")).sendKeys(travelinfo.stationsId);

        driver.findElement(By.id("travel_update_terminalStationId")).clear();
        driver.findElement(By.id("travel_update_terminalStationId")).sendKeys(travelinfo.terminalStationId);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String jsStartingTime = "document.getElementById('travel_update_startingTime').value='"+travelinfo.startingTime+"'";
        js.executeScript(jsStartingTime);
        //driver.findElement(By.id("travel_update_startingTime")).clear();
        //driver.findElement(By.id("travel_update_startingTime")).sendKeys(travelinfo.startingTime);

        String jsEndTime = "document.getElementById('travel_update_endTime').value='"+travelinfo.endTime+"'";
        js.executeScript(jsEndTime);
        //driver.findElement(By.id("travel_update_endTime")).clear();
        //driver.findElement(By.id("travel_update_endTime")).sendKeys(travelinfo.endTime);

        driver.findElement(By.id("travel_update_button")).click();
        Thread.sleep(1000);

//        String statusUpdateTrip = driver.findElement(By.id("login_result_msg")).getText();
//        if(!"".equals(statusUpdateTrip))
//            System.out.println("Failed,Status of Update Trip btn is NULL!");
//        else
//            System.out.println("Update Trip btn status:"+statusUpdateTrip);
//
//        Assert.assertEquals(statusUpdateTrip.startsWith("Success"),true);
    }
    @Test (dependsOnMethods = {"testTravel"})
    public void testQueryTravel() throws Exception{
        driver.findElement(By.id("travel_queryAll_button")).click();
        Thread.sleep(1000);
        //gain Travel list
        List<WebElement> travelList = driver.findElements(By.xpath("//table[@id='query_travel_list_table']/tbody/tr"));

        if (travelList.size() > 0)
            System.out.printf("Success to Query Travel and Travel list size is %d.%n",travelList.size());
        else
            System.out.println("Failed to Query Travel or Travel list size is 0");
        Assert.assertEquals(travelList.size() > 0,true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
