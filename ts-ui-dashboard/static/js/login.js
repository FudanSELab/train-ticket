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
/********************Function For Login Service**********************/
/********************Used For Login Service Single Microservice Test*/

$("#login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.email = $("#login_email").val();
    if(loginInfo.email == null || loginInfo.email == ""){
        alert("Please input your email to login.");
        return;
    }
    loginInfo.password = $("#login_password").val();
    if(loginInfo.password == null || loginInfo.password == ""){
        alert("Please input your password to login.");
        return;
    }
    loginInfo.verificationCode = $("#login_verification_code").val();
    if(loginInfo.verificationCode == null || loginInfo.verificationCode == ""){
        alert("Please input verification code to login.");
        return;
    }
    var data = JSON.stringify(loginInfo);
    $("#login_button").attr("disabled",true);
    $("#login_result_status").text("false");
    $.ajax({
        type: "post",
        url: "/login",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            $("#login_result_status").text("true");
            if(obj["status"] == true){
                $("#user_login_id").html(obj["account"].id);
                $("#user_login_token").html(obj["token"]);
                document.cookie = "loginId=" + obj["account"].id;
                document.cookie = "loginToken=" + obj["token"];
                $("#user_login_id").text(obj["account"].id);
            }
            $("#login_result_status").html(JSON.stringify(obj["status"]));
            $("#login_result_msg").html(obj["message"]);
            $("#login_result_account").html(JSON.stringify(obj["account"]));
            $("#login_result_token").html(JSON.stringify(obj["token"]));
        },
        complete: function(){
            $("#login_button").attr("disabled",false);
        }
    });
});
