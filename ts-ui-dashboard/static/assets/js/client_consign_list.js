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

var appConsign = new Vue({
    el: '#vueConsignApp',
    data: {
        myConsigns: []
    },
    methods: {
        queryMyConsign() {
            var accountid = sessionStorage.getItem("client_id");
            var that = this;
            $.ajax({
                type: "get",
                url: "/api/v1/consignservice/consigns/account/" + accountid,
                dataType: "json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result.status == 1) {
                        var size = result.data.length;
                        that.myConsigns = new Array(size);
                        for (var i = 0; i < size; i++) {
                            that.myConsigns[i] = result.data[i];
                            // that.myConsigns[i].from = that.getStationNameById(that.myConsigns[i].from);
                            // that.myConsigns[i].to = that.getStationNameById(that.myConsigns[i].to);
                            //  that.myConsigns[i].handleDate = that.convertNumberToDateTimeString(that.myConsigns[i].handleDate);
                            that.myConsigns[i].targetDate = that.convertNumberToDateTimeString(that.myConsigns[i].targetDate);
                        }
                    } else {
                        alert("no consign!")
                    }
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                }
            });

        },
        logOutClient() {
            var logoutInfo = new Object();
            logoutInfo.id = sessionStorage.getItem("client_id");
            if (logoutInfo.id == null || logoutInfo.id == "") {
                alert("No cookie named 'loginId' exist. please login");
                location.href = "client_login.html";
                return;
            }
            logoutInfo.token = sessionStorage.getItem("client_token");
            if (logoutInfo.token == null || logoutInfo.token == "") {
                alert("No cookie named 'loginToken' exist.  please login");
                location.href = "client_login.html";
                return;
            }
            var data = JSON.stringify(logoutInfo);
            var that = this;
            $.ajax({
                type: "post",
                url: "/logout",
                contentType: "application/json",
                dataType: "json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    sessionStorage.setItem("client_id", "-1");
                    sessionStorage.setItem("client_name", "Not Login");
                    sessionStorage.setItem("client_token", "", -1);

                    document.getElementById("client_name").innerHTML = "Not Login";
                    location.href = "client_login.html";
                },
                error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                }
            });
        },
        getStationNameById(stationId) {
            var stationName;
            var getStationInfoOne = new Object();
            getStationInfoOne.stationId = stationId;
            var getStationInfoOneData = JSON.stringify(getStationInfoOne);
            $.ajax({
                type: "post",
                url: "/station/queryById",
                contentType: "application/json",
                dataType: "json",
                data: getStationInfoOneData,
                async: false,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    stationName = result["name"];
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                }
            });
            //alert("Return Station Name:" + stationName);
            return stationName;
        },
        convertNumberToDateTimeString(timeNumber) {
            var date = new Date(timeNumber);
            return date.toISOString().slice(0, 10);
        },
        setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toUTCString();
            document.cookie = cname + "=" + cvalue + "; " + expires;
        }

    },
    mounted() {
        var username = sessionStorage.getItem("client_name");
        console.log("UserName" + username);
        if (username == null || username == "Not Login") {

            location.href = "client_login.html";
        } else {
            document.getElementById("client_name").innerHTML = username;
            this.queryMyConsign();
        }
    }
});