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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestFlowTwoRebook {
    private WebDriver driver;
    private String baseUrl;
    private String trainType;//0--all,1--GaoTie,2--others
    private List<WebElement> myOrdersList;
    private List<WebElement> changeTicketsSearchList;
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
        trainType = "1";
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
    public void testChangeOrder() throws Exception{
        System.out.printf("The orders list size is:%d%n",myOrdersList.size());
        String statusOrder  = "";
        int i;
        //Find the first paid order .
        for(i = 0;i < myOrdersList.size();i++) {
            statusOrder = myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[7]/div/label[2]")).getText();
            if(statusOrder.startsWith("Paid"))
                break;
        }
        if(i == myOrdersList.size() || i > myOrdersList.size())
            System.out.printf("Failed,there is no paid order!");
        Assert.assertEquals(i < myOrdersList.size(),true);

        //click change btn
        myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[12]/div/button[1]")).click();
        Thread.sleep(1000);
        String inputStartingPlace = driver.findElement(By.id("travel_rebook_startingPlace")).getAttribute("value");
        String inputTerminalPlace = driver.findElement(By.id("travel_rebook_terminalPlace")).getAttribute("value");
        boolean bStartingPlace = !"".equals(inputStartingPlace);
        boolean bTerminalPlace = !"".equals(inputTerminalPlace);
        boolean bchangeStatus = bStartingPlace && bTerminalPlace;
        if(bchangeStatus == false)
            System.out.println("Step-Change Your Order,The input is null!!");
        Assert.assertEquals(bchangeStatus,true);

        String bookDate = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        Random randDate = new Random();
        int randomDate = randDate.nextInt(25); //int范围类的随机数
        newDate.add(Calendar.DATE, randomDate+5);//随机定5-30天后的票
        bookDate=sdf.format(newDate.getTime());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel_rebook_date').value='"+bookDate+"'");

        WebElement elementRebookTraintype = driver.findElement(By.id("search_rebook_train_type"));
        Select selTraintype = new Select(elementRebookTraintype);
        selTraintype.selectByValue("trainType"); //All

        driver.findElement(By.id("travel_rebook_button")).click();
        Thread.sleep(1000);

        changeTicketsSearchList = driver.findElements(By.xpath("//table[@id='tickets_change_list_table']/tbody/tr"));
        if (changeTicketsSearchList.size() > 0) {
            System.out.printf("Success to search tickets，the tickets list size is:%d%n",changeTicketsSearchList.size());
        }
        else
            System.out.println("Failed to search tickets，the tickets list size is 0 or No tickets available!");
        Assert.assertEquals(changeTicketsSearchList.size() > 0,true);


    }
    @Test (dependsOnMethods = {"testChangeOrder"})
    public void testTicketRebook ()throws Exception{
        //Pick up a train (the first one!)and rebook tickets
        WebElement elementBookingSeat = changeTicketsSearchList.get(0).findElement(By.xpath("td[10]/select"));
        Select selSeat = new Select(elementBookingSeat);
        selSeat.selectByValue("2"); //1st
        changeTicketsSearchList.get(0).findElement(By.xpath("td[11]/button")).click();
        Thread.sleep(1000);

        String itemTripId = driver.findElement(By.id("ticket_rebook_confirm_old_tripId")).getText();
        String itemNewTripId = driver.findElement(By.id("ticket_rebook_confirm_new_tripId")).getText();
        String itemDate = driver.findElement(By.id("ticket_rebook_confirm_travel_date")).getText();
        String itemSeatType = driver.findElement(By.id("ticket_rebook_confirm_seatType_String")).getText();

        boolean bTripId = !"".equals(itemTripId);
        boolean bNewTripId = !"".equals(itemNewTripId);
        boolean bDate = !"".equals(itemDate);
        boolean bSeatType = !"".equals(itemSeatType);

        boolean bStatusConfirm = bTripId && bNewTripId && bDate &&  bSeatType;
        if(bStatusConfirm == false){
            driver.findElement(By.id("ticket_rebook_confirm_cancel_btn")).click();
            System.out.println("Confirming Ticket Canceled!");
        }
        Assert.assertEquals(bStatusConfirm,true);

        driver.findElement(By.id("ticket_rebook_confirm_confirm_btn")).click();
        Thread.sleep(1000);
        System.out.println("Confirm Ticket!");
        Alert javascriptConfirm = driver.switchTo().alert();
        String statusAlert = driver.switchTo().alert().getText();
        //System.out.println("The Alert information of Confirming Ticket："+statusAlert);

        if("".equals(statusAlert)){
            System.out.println("Failed,Status of tickets confirm alert is NULL!");
            Assert.assertEquals(!"".equals(statusAlert), true);
        }
        else if(statusAlert.startsWith("Success")){
            System.out.println("Rebook status:" + statusAlert);
            javascriptConfirm.accept();
        }
        else if(statusAlert.startsWith("Please")) {
            System.out.println(statusAlert);
            javascriptConfirm.accept();

            String itemPrice = driver.findElement(By.id("rebook_money_pay")).getAttribute("value");
            boolean bPrice = !"".equals(itemPrice);
            if(bPrice == false)
                System.out.println("Confirming Ticket failed!");
            Assert.assertEquals(bPrice,true);

            driver.findElement(By.id("ticket_rebook_pay_panel_confirm")).click();
            Thread.sleep(1000);

            Alert javascriptPay = null;
            String statusPayAlert;

            try {
                new WebDriverWait(driver, 30).until(ExpectedConditions
                        .alertIsPresent());
                javascriptPay = driver.switchTo().alert();
                statusPayAlert = driver.switchTo().alert().getText();
                System.out.println("Rebook payment status:"+statusPayAlert);
                javascriptPay.accept();
                Thread.sleep(1000);
                Assert.assertEquals(statusPayAlert.startsWith("Success"),true);
            } catch (NoAlertPresentException NofindAlert) {
                NofindAlert.printStackTrace();
            }
        }
        else
            System.out.println("Failed,Rebook status:" + statusAlert);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
