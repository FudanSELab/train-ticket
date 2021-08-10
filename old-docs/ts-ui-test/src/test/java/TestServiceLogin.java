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
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class TestServiceLogin {
    private WebDriver driver;
    private String baseUrl;
    public static void ServiceLogin(WebDriver driver,String username,String password){
        driver.findElement(By.id("login_email")).clear();
        driver.findElement(By.id("login_email")).sendKeys(username);
        driver.findElement(By.id("login_password")).clear();
        driver.findElement(By.id("login_password")).sendKeys(password);
        driver.findElement(By.id("login_button")).click();
    }
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @DataProvider(name="user")
    public Object[][] Users(){
        return new Object[][]{
                {"fdse_microservices@163","DefaultPassword",false},
                {"fdse_microservices@163.com","DefaultPass",false},
                {"fdse_microservices@163.com","DefaultPassword",true},
                {"error","error",false},
                //{"","","请先输入您的邮箱帐号"},
                //{"fdse_microservices@163.com"," ","帐号或密码错误"},
                //{" ","DefaultPassword","请先输入您的邮箱帐号"},
                //{"error","error","帐号或密码错误"},
        };
    }
    @Test (dataProvider="user")
    public void testSignIn(String username,String password,boolean expectText)throws Exception{
        driver.get(baseUrl + "/");

        //call function login
        ServiceLogin(driver,username,password);
        Thread.sleep(1000);

        //get login status
        String statusSignIn = driver.findElement(By.id("login_result_msg")).getText();
        if (!"".equals(statusSignIn))
            System.out.println("Sign Up btn status: "+statusSignIn);
        else
            System.out.println("False，Status of Sign In btn is NULL!");
        System.out.println(expectText);
        Assert.assertEquals(statusSignIn.startsWith("Success"),expectText);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
