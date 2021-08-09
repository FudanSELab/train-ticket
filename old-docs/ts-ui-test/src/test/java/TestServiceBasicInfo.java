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

public class TestServiceBasicInfo {
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
    public void testBasicInfo() throws Exception{
        driver.get(baseUrl + "/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('basic_information_tripId').value='G1234'");
        js.executeScript("document.getElementById('basic_information_trainTypeId').value='GaoTieOne'");
        js.executeScript("document.getElementById('basic_information_startingStation').value='shanghai'");
        js.executeScript("document.getElementById('basic_information_stations').value='beijing'");
        js.executeScript("document.getElementById('basic_information_terminalStation').value='taiyuan'");

        String jsstartingTime = "document.getElementById('basic_information_startingTime').value='09:51'";
        js.executeScript(jsstartingTime);
        String jssendTime = "document.getElementById('basic_information_endTime').value='15:51'";
        js.executeScript(jssendTime);

        js.executeScript("document.getElementById('basic_information_startingPlace').value='Shang Hai'");
        js.executeScript("document.getElementById('basic_information_endPlace').value='Tai Yuan'");

        String bookDate = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        Random randDate = new Random();
        int randomDate = randDate.nextInt(25); //int范围类的随机数
        newDate.add(Calendar.DATE, randomDate+5);//随机定5-30天后的票
        bookDate=sdf.format(newDate.getTime());

        js.executeScript("document.getElementById('basic_information_departureTime').value='"+bookDate+"'");

        driver.findElement(By.id("basic_information_button")).click();
        Thread.sleep(1000);

        //gain BasicInfo list
        List<WebElement> basicInfoList = driver.findElements(By.xpath("//table[@id='query_basic_information_list_table']/tbody/tr"));
        if (basicInfoList.size() > 0)
            System.out.printf("Success to Query BasicInfo and BasicInfo list size is %d.%n",basicInfoList.size());
        else
            System.out.println("Failed to Query BasicInfo or BasicInfo list size is 0");
        Assert.assertEquals(basicInfoList.size() > 0,true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
