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

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestFlowTwoPay {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;
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
    //Test Flow Preserve Step 1: - Login
    public void testLogin()throws Exception{
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
        driver.findElement(By.id("flow_two_page")).click();
    }
    @Test (dependsOnMethods = {"testLogin"})
    public void testViewOrders() throws Exception{
        driver.findElement(By.id("flow_two_page")).click();
        driver.findElement(By.id("refresh_my_order_list_button")).click();
        Thread.sleep(1000);
        //gain my oeders
        myOrdersList = driver.findElements(By.xpath("//div[@id='my_orders_result']/div"));
        if (myOrdersList.size() > 0) {
            System.out.printf("Success to show my orders list，the list size is:%d%n",myOrdersList.size());
        }
        else
            System.out.println("Failed to show my orders list，the list size is 0 or No orders in this user!");
        Assert.assertEquals(myOrdersList.size() > 0,true);
    }
    @Test (dependsOnMethods = {"testViewOrders"})
    public void testPayOrder() throws Exception{
        System.out.printf("The orders list size is:%d%n",myOrdersList.size());
        String statusOrder  = "";
        int i;
        //Find the first not paid order .
        for(i = 0;i < myOrdersList.size();i++) {
        //while(!(statusOrder.startsWith("Not")) && i < myOrdersList.size()) {
            //statusOrder = myOrdersList.get(i).findElement(By.xpath("/div[2]/div/div/form/div[7]/div/label[2]")).getText();
            statusOrder = myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[7]/div/label[2]")).getText();
            if(statusOrder.startsWith("Not"))
                break;
        }
        if(i == myOrdersList.size() || i > myOrdersList.size())
            System.out.printf("Failed,there is no not paid order!");
        Assert.assertEquals(i < myOrdersList.size(),true);

        myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[7]/div/button")).click();
        Thread.sleep(1000);
        String inputNotPaidOrderId = driver.findElement(By.id("pay_for_not_paid_orderId")).getAttribute("value");
        String inputNotPaidPrice = driver.findElement(By.id("pay_for_not_paid_price")).getAttribute("value");
        String inputNotPaidTripId = driver.findElement(By.id("pay_for_not_paid_tripId")).getAttribute("value");
        boolean bNotPaidOrderId = !"".equals(inputNotPaidOrderId);
        boolean bNotPaidPrice = !"".equals(inputNotPaidPrice);
        boolean bNotPaidTripId = !"".equals(inputNotPaidTripId);
        boolean bNotPaidStatus = bNotPaidOrderId && bNotPaidPrice && bNotPaidTripId;
        if(bNotPaidStatus == false)
            System.out.println("Step-Pay for Your Order,The input is null!!");
        Assert.assertEquals(bNotPaidStatus,true);

        driver.findElement(By.id("pay_for_not_paid_pay_button")).click();
        Thread.sleep(1000);

        Alert javascriptConfirm = driver.switchTo().alert();
        String statusAlert = driver.switchTo().alert().getText();
        System.out.println("The Alert information of Payment："+statusAlert);
        Assert.assertEquals(statusAlert.startsWith("Success"),true);
        javascriptConfirm.accept();
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
