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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class TestServiceRebook {
    private WebDriver driver;
    private String baseUrl;
    private String orderId = "";
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
        //Go to flow_one_page

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
    public void getOrders()throws Exception{

        WebElement elementRefreshOrdersBtn = driver.findElement(By.id("refresh_order_button"));
        WebElement elementOrdertypeGTCJ = driver.findElement(By.xpath("//*[@id='microservices']/div[4]/div[1]/h3/input[1]"));
        WebElement elementOrdertypePT = driver.findElement(By.xpath("//*[@id='microservices']/div[4]/div[1]/h3/input[2]"));
        elementOrdertypeGTCJ.click();
        elementOrdertypePT.click();
        if(elementOrdertypeGTCJ.isEnabled() || elementOrdertypePT.isEnabled()){
            elementRefreshOrdersBtn.click();
            System.out.println("Show Orders according database!");
        }
        else {
            elementRefreshOrdersBtn.click();
            Alert javascriptConfirm = driver.switchTo().alert();
            javascriptConfirm.accept();
            elementOrdertypeGTCJ.click();
            elementOrdertypePT.click();
            elementRefreshOrdersBtn.click();
        }
        //gain oeders
        List<WebElement> ordersList = driver.findElements(By.xpath("//table[@id='all_order_table']/tbody/tr"));
        //Confirm ticket selection
        if (ordersList.size() > 0) {
            Random rand = new Random();
            int i = rand.nextInt(100) % ordersList.size(); //int范围类的随机数
            orderId =  ordersList.get(i).findElement(By.xpath("td[3]")).getText();
            WebElement elementOrderStatus = ordersList.get(i).findElement(By.xpath("td[8]/select"));
            Select selSeat = new Select(elementOrderStatus);
            selSeat.selectByValue("1"); //2st
            ordersList.get(i).findElement(By.xpath("td[9]/button")).click();
            System.out.println("Success get orderId and update order status! orderId:"+orderId);
        }
        else
            System.out.println("Cant't get orders information1");
        Assert.assertEquals(ordersList.size() > 0,true);
        Assert.assertEquals(orderId.equals(""),false);
    }
    @Test (dependsOnMethods = {"getOrders"})
    public void testTicketRebook()throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        if(orderId ==null || orderId.length() <= 0) {
//            System.out.println("Failed,orderId is NULL!");
//            driver.quit();
//        }
//        if (!"".equals(orderId))
//            System.out.println("Sign Up btn status: "+statusSignIn);
//        else
//            System.out.println("False，Status of Sign In btn is NULL!");
        driver.findElement(By.id("single_rebook_order_id")).clear();

        driver.findElement(By.id("single_rebook_order_id")).sendKeys(orderId);
        //driver.findElement(By.id("single_rebook_order_id")).sendKeys("8177ac5a-61ac-42f4-83f4-bd7b394d0531");
        //js.executeScript("document.getElementById('single_rebook_order_id').value=orderId");
        js.executeScript("document.getElementById('single_rebook_old_trip_id').value='G1234'");
        js.executeScript("document.getElementById('single_rebook_trip_id').value='G1235'");
        WebElement elementRebookSeatType = driver.findElement(By.id("single_rebook_seat_type"));
        Select selSeat = new Select(elementRebookSeatType);
        selSeat.selectByValue("2"); //2st

        String bookDate = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        Random randDate = new Random();
        int randomDate = randDate.nextInt(25); //int范围类的随机数
        newDate.add(Calendar.DATE, randomDate+5);//随机定5-30天后的票
        bookDate=sdf.format(newDate.getTime());

        js.executeScript("document.getElementById('single_rebook_date').value='"+bookDate+"'");

        driver.findElement(By.id("single_rebook_button")).click();
        Thread.sleep(1000);
        //get rebook status
        String statusRebook = driver.findElement(By.id("single_rebook_result")).getText();
        if("".equals(statusRebook)){
            System.out.println("Failed,Status of Rebook btn is NULL!");
            Assert.assertEquals(!"".equals(statusRebook), true);
        }
        else if(statusRebook.startsWith("You haven't paid")){
            System.out.println("Failed,You haven't paid the original ticket!");
        }
        else if(statusRebook.startsWith("Please")) {
            System.out.println(statusRebook);
            driver.findElement(By.id("rebook_pay_button")).click();
            Thread.sleep(1000);
            String statusRebookPayment = driver.findElement(By.id("rebook_payment_result")).getText();
            System.out.println("Rebook payment status:"+statusRebookPayment);
            Assert.assertEquals(statusRebookPayment.startsWith("true"), true);
        }
        else {
            System.out.println("Rebook status:" + statusRebook);
            Assert.assertEquals(statusRebook.startsWith("true"), true);
        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
