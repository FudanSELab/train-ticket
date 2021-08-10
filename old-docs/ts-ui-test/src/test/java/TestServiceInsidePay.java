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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestServiceInsidePay {
    private WebDriver driver;
    private String baseUrl;
    public static void login(WebDriver driver,String username,String password){
        driver.findElement(By.id("flow_one_page")).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(username);
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        driver.findElement(By.id("flow_preserve_login_password")).sendKeys(password);
        driver.findElement(By.id("flow_preserve_login_button")).click();
    }
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void login()throws Exception{
        driver.get(baseUrl + "/");

        //define username and password
        String username = "fdse_microservices@163.com";
        String password = "DefaultPassword";

        //call function login
        login(driver,username,password);
        Thread.sleep(1000);

        //get login status
        String statusLogin = driver.findElement(By.id("flow_preserve_login_msg")).getText();
        if("".equals(statusLogin))
            System.out.println("Failed to Login! Status is Null!");
        else if(statusLogin.startsWith("Success"))
            System.out.println("Success to Login! Status:"+statusLogin);
        else
            System.out.println("Failed to Login! Status:"+statusLogin);
        Assert.assertEquals(statusLogin.startsWith("Success"),true);
        driver.findElement(By.id("microservice_page")).click();
    }
    @Test (dependsOnMethods = {"login"})
    public void testInsidePay() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.id("inside_payment_orderId")).clear();
        driver.findElement(By.id("inside_payment_orderId")).sendKeys("5ad7750b-a68b-49c0-a8c0-32776b067703");
        driver.findElement(By.id("inside_payment_tripId")).clear();
        driver.findElement(By.id("inside_payment_tripId")).sendKeys("G1234");
        driver.findElement(By.id("inside_payment_pay_button")).click();
        Thread.sleep(1000);

        String statusInsidePay = driver.findElement(By.id("inside_payment_result")).getText();
        if (!"".equals(statusInsidePay))
            System.out.println("Status of inside payment: "+statusInsidePay);
        else
            System.out.println("False, status of inside payment result is null!");
        Assert.assertEquals(!"".equals(statusInsidePay),true);
    }
    @Test (dependsOnMethods = {"testInsidePay"})
    public void testInsidePayList() throws Exception {
        driver.findElement(By.id("inside_payment_query_payment_button")).click();
        Thread.sleep(1000);

        List<WebElement> insidePayList = driver.findElements(By.xpath("//table[@id='query_inside_payment_payment_list_table']/tbody/tr"));
        if (insidePayList.size() > 0)
            System.out.printf("Success to Query InsidePayList and InsidePay list size is %d.%n",insidePayList.size());
        else
            System.out.println("Failed to Query InsidePayList or InsidePay list size is 0");
        Assert.assertEquals(insidePayList.size() > 0,true);
    }
    @Test (dependsOnMethods = {"testInsidePayList"})
    public void testUserBalance() throws Exception {
        driver.findElement(By.id("inside_payment_query_account_button")).click();
        Thread.sleep(1000);

        List<WebElement> userBalanceList = driver.findElements(By.xpath("//table[@id='query_inside_payment_account_list_table']/tbody/tr"));
        if (userBalanceList.size() > 0)
            System.out.printf("Success to Query UserBalanceList and UserBalanceList list size is %d.%n",userBalanceList.size());
        else
            System.out.println("Failed to Query UserBalanceList or UserBalanceList list size is 0");
        Assert.assertEquals(userBalanceList.size() > 0,true);
    }
    @Test (dependsOnMethods = {"testUserBalance"})
    public void testAddMoney() throws Exception {
        driver.findElement(By.id("inside_payment_query_add_money_button")).click();
        Thread.sleep(1000);

        List<WebElement> addMoneyList = driver.findElements(By.xpath("//table[@id='query_inside_payment_add_money_list_table']/tbody/tr"));
        if (addMoneyList.size() > 0)
            System.out.printf("Success to Query Add Money List and Add Money List list size is %d.%n",addMoneyList.size());
        else
            System.out.println("Failed to Query Add Money List or Add Money List list size is 0");
        Assert.assertEquals(addMoneyList.size() > 0,true);
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
