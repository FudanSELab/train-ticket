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

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestServiceTicketInfo {
    private WebDriver driver;
    private String baseUrl;
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void testTicketInfo() throws Exception{
        driver.get(baseUrl + "/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('ticketinfo_tripId').value='G1234'");
        js.executeScript("document.getElementById('ticketinfo_trainTypeId').value='GaoTieOne'");
        js.executeScript("document.getElementById('ticketinfo_startingStation').value='shanghai'");
        js.executeScript("document.getElementById('ticketinfo_stations').value='beijing'");
        js.executeScript("document.getElementById('ticketinfo_terminalStation').value='taiyuan'");

        String jsstartingTime = "document.getElementById('ticketinfo_startingTime').value='09:51'";
        js.executeScript(jsstartingTime);
        String jssendTime = "document.getElementById('ticketinfo_endTime').value='15:51'";
        js.executeScript(jssendTime);

        js.executeScript("document.getElementById('ticketinfo_startingPlace').value='Shang Hai'");
        js.executeScript("document.getElementById('ticketinfo_endPlace').value='Tai Yuan'");

        String bookDate = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        Random randDate = new Random();
        int randomDate = randDate.nextInt(25); //int范围类的随机数
        newDate.add(Calendar.DATE, randomDate+5);//随机定5-30天后的票
        bookDate=sdf.format(newDate.getTime());

        js.executeScript("document.getElementById('ticketinfo_departureTime').value='"+bookDate+"'");

        driver.findElement(By.id("ticketinfo_button")).click();
        Thread.sleep(1000);

        //gain TicketInfo list
        List<WebElement> ticketInfoList = driver.findElements(By.xpath("//table[@id='query_ticketinfo_list_table']/tbody/tr"));
        if (ticketInfoList.size() > 0)
            System.out.printf("Success to Query TicketInfo and TicketInfo list size is %d.%n",ticketInfoList.size());
        else
            System.out.println("Failed to Query TicketInfo or TicketInfo list size is 0");
        Assert.assertEquals(ticketInfoList.size() > 0,true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
