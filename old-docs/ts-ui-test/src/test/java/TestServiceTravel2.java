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

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class TestServiceTravel2 {
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
    public void testTravel2() throws Exception{
        driver.get(baseUrl + "/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel2_update_tripId').value='Z1234'");
        js.executeScript("document.getElementById('travel2_update_trainTypeId').value='ZhiDa'");
        js.executeScript("document.getElementById('travel2_update_startingStationId').value='shanghai'");
        js.executeScript("document.getElementById('travel2_update_stationsId').value='beijing'");
        js.executeScript("document.getElementById('travel2_update_terminalStationId').value='taiyuan'");
        js.executeScript("document.getElementById('travel2_update_startingTime').value='11:17'");
        js.executeScript("document.getElementById('travel2_update_endTime').value='15:19'");

        driver.findElement(By.id("travel2_update_button")).click();
        Thread.sleep(1000);
//        String statusSignIn = driver.findElement(By.id("login_result_msg")).getText();
//        if(statusSignIn ==null || statusSignIn.length() <= 0) {
//            System.out.println("Failed,Status of Sign In btn is NULL!");
//            driver.quit();
//        }else
//            System.out.println("Sign Up btn status:"+statusSignIn);
    }
    @Test (dependsOnMethods = {"testTravel2"})
    public void testQueryTravel2() throws Exception{
        driver.findElement(By.id("travel2_queryAll_button")).click();
        Thread.sleep(1000);
        //gain Travel list
        List<WebElement> travel2List = driver.findElements(By.xpath("//table[@id='query_travel2_list_table']/tbody/tr"));

        if (travel2List.size() > 0)
            System.out.printf("Success to Query Travel2 and Travel2 list size is %d.%n",travel2List.size());
        else
            System.out.println("Failed to Query Travel2 or Travel2 list size is 0");
        Assert.assertEquals(travel2List.size() > 0,true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
