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

$("#logout_button").click(function () {
    var logoutInfo = new Object();
    logoutInfo.id = sessionStorage.getItem("client_id");
    if (logoutInfo.id == null || logoutInfo.id == "") {
        //alert("No cookie named 'loginId' exist. please login");
        location.href = "client_login.html";
        return;
    }
    logoutInfo.token = sessionStorage.getItem("client_token");
    if (logoutInfo.token == null || logoutInfo.token == "") {
        // alert("No cookie named 'loginToken' exist.  please login");
        location.href = "client_login.html";
        return;
    }

    sessionStorage.setItem("client_id", "-1");
    sessionStorage.setItem("client_token", "-1");
    sessionStorage.setItem("client_name", "Not Login");
    document.getElementById("client_name").innerHTML = "Not Login";
    location.href = "client_login.html";

    // var data = JSON.stringify(logoutInfo);
    // $.ajax({
    //     type: "post",
    //     url: "/logout",
    //     contentType: "application/json",
    //     dataType: "json",
    //     data:data,
    //     xhrFields: {
    //         withCredentials: true
    //     },
    //     success: function(result){
    //         if(result["status"] == true){
    //             setCookie("loginId", "", -1);
    //             setCookie("loginToken", "", -1);
    //         }else if(result["message"] == "Not Login"){
    //             setCookie("loginId", "", -1);
    //             setCookie("loginToken", "", -1);
    //         }
    //         sessionStorage.setItem("client_id","-1");
    //         sessionStorage.setItem("client_name", "Not Login");
    //         document.getElementById("client_name").innerHTML = "Not Login";
    //         location.href= "client_login.html";
    //         alert("logout success!")
    //     },
    //     error: function (e) {
    //         alert("logout error");
    //     }
    // });
});

$("#name-wrap").click(function () {

    var logoutInfo = new Object();
    logoutInfo.id = sessionStorage.getItem("client_id");
    if (logoutInfo.id == null || logoutInfo.id == "") {
        //alert("No cookie named 'loginId' exist. please login");
        location.href = "client_login.html";
        return;
    }
    logoutInfo.token = sessionStorage.getItem("client_token");
    if (logoutInfo.token == null || logoutInfo.token == "") {
        // alert("No cookie named 'loginToken' exist.  please login");
        location.href = "client_login.html";
        return;
    }

})

$("#upload-avatar").click(function () {
    location.href = "upload_avatar.html"
})

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0)
            return c.substring(name.length, c.length);
    }
    return "";
}