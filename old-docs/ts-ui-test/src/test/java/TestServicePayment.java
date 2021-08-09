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

public class TestServicePayment {
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
    public void testPayment() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.id("payment_orderId")).clear();
        driver.findElement(By.id("payment_orderId")).sendKeys("5ad7750b-a68b-49c0-a8c0-32776b067703");
        driver.findElement(By.id("payment_price")).clear();
        driver.findElement(By.id("payment_price")).sendKeys("100.0");
        driver.findElement(By.id("payment_userId")).clear();
        driver.findElement(By.id("payment_userId")).sendKeys("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f");
        driver.findElement(By.id("payment_pay_button")).click();
        Thread.sleep(1000);

        String statusPayment = driver.findElement(By.id("payment_result")).getText();
        if (!"".equals(statusPayment))
            System.out.println("Status of payment: "+statusPayment);
        else
            System.out.println("False, status of  payment result is null!");
        Assert.assertEquals(!"".equals(statusPayment),true);
    }
    @Test (dependsOnMethods = {"testPayment"})
    public void testPaymentList() throws Exception {
        driver.findElement(By.id("payment_query_button")).click();
        Thread.sleep(1000);

        List<WebElement> paymentList = driver.findElements(By.xpath("//table[@id='query_payment_list_table']/tbody/tr"));
        if (paymentList.size() > 0)
            System.out.printf("Success to Query PaymentList and Payment list size is %d.%n",paymentList.size());
        else
            System.out.println("Failed to Query PaymentList or Payment list size is 0");
        Assert.assertEquals(paymentList.size() > 0,true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
