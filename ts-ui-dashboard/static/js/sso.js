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


/********************************************************************/
/********************Function For SSO Service************************/
/********************Used For SSO Service Single Microservice Test***/

$("#refresh_account_button").click(function() {
    refresh_account();
});

function refresh_account() {
    $("#refresh_account_button").attr("disabled",true);
    $("#account_list_status").text("false");
    $.ajax({
        type: "get",
        url: "/account/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#account_list_table").find("tbody").html("");
            var obj = result["accountArrayList"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='sso_account_id'>" + obj[i]["id"] + "</td>" +
                    "<td ><input class='sso_account_email form-control' value='" + obj[i]["email"] + "'></td>" +
                    "<td ><input class='sso_account_password form-control' value='" + obj[i]["password"] + "'></td>" +
                    "<td>" + "<button class='account_update_btn btn btn-primary'>Update</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToSsoAccountTable();
            $("#account_list_status").text("true");
            //alert("Success.");
        },
        complete:function(){
            $("#refresh_account_button").attr("disabled",false);
        }
    });
}

function addListenerToSsoAccountTable(){
    var accountUpdateBtnSet = $(".account_update_btn");
    for(var i = 0;i < accountUpdateBtnSet .length;i++){
        accountUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.accountId = $(this).parents("tr").find(".sso_account_id").text();
            modifyInfo.newEmail = $(this).parents("tr").find(".sso_account_email").val();
            modifyInfo.newPassword = $(this).parents("tr").find(".sso_account_password").val();
            var data = JSON.stringify(modifyInfo);
            $("#account_list_status").text("false");
            $.ajax({
                type: "post",
                url: "/account/modify",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    $("#account_list_status").text("true");
                    if(result["status"] == true){
                        refresh_account();
                        //alert("Success.");
                    }else{
                        //alert(result["message"]);
                    }
                },
                complete: function(){
                }
            });
        }
    }
}

$("#refresh_login_account_button").click(function() {
    refresh_login_account();
});

function refresh_login_account() {
    $("#refresh_login_account_button").attr("disabled",true);
    $("#login_list_status").text("false");
    $.ajax({
        type: "get",
        url: "/account/findAllLogin",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#login_account_list_table").find("tbody").html("");
            var obj = result["loginAccountList"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#login_account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='account_kick_id'>" + obj[i]["accountId"] + "</td>" +
                    "<td class='account_kick_token'>" + obj[i]["loginToken"] + "</td>" +
                    "<td>" +  "<button class='account_kick_off_btn btn btn-primary'>Kick Off</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToSsoLoginAccountTable();
            $("#login_list_status").text("true");
            //alert("Success.");
        },
        complete:function(){
            $("#refresh_login_account_button").attr("disabled",false);
        }
    });
}

function addListenerToSsoLoginAccountTable(){
    var accountKickOffBtnSet = $(".account_kick_off_btn");
    for(var i = 0;i < accountKickOffBtnSet.length;i++){
        accountKickOffBtnSet[i].onclick = function(){
            var logoutInfo = new Object();
            logoutInfo.id = $(this).parents("tr").find(".account_kick_id").text();
            logoutInfo.token = $(this).parents("tr").find(".account_kick_token").text();
            var data = JSON.stringify(logoutInfo);
            $("#login_list_status").text("false");
            $.ajax({
                type: "post",
                url: "/logout",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_login_account();
                        //alert("Success.");
                    }else{
                        //alert(result["message"]);
                    }
                    $("#login_list_status").text("true");
                },
                complete: function(){
                }
            });
        }
    }
}

$("#logout_button").click(function() {
    var logoutInfo = new Object();
    logoutInfo.id = getCookie("loginId");
    if(logoutInfo.id == null || logoutInfo.id == ""){
        alert("No cookie named 'loginId' exist.");
        return;
    }
    logoutInfo.token = getCookie("loginToken");
    if(logoutInfo.token == null || logoutInfo.token == ""){
        alert("No cookie named 'loginToken' exist.");
        return;
    }
    var data = JSON.stringify(logoutInfo);
    $.ajax({
        type: "post",
        url: "/logout",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result["status"] == true){
                setCookie("loginId", "", -1);
                setCookie("loginToken", "", -1);
                $("#user_login_id").text("Not Login");
            }else if(result["message"] == "Not Login"){
                setCookie("loginId", "", -1);
                setCookie("loginToken", "", -1);
                $("#user_login_id").text("Not Login");
            }
        }
    });
});

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i< ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name)==0)
            return c.substring(name.length,c.length);
    }
    return "";
}
