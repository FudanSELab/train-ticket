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
/********************Function For Price Service**********************/

$("#price_queryAll_button").click(function() {
    $("#price_queryAll_button").attr("disabled",true);
    $("#single_list_price_status").text("false");
    $.ajax({
        type: "get",
        url: "/price/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (obj) {
            var result = obj["priceConfig"];
            var size = result.length;
            $("#query_price_list_table").find("tbody").html("");
            $("#price_result_talbe").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_price_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td>" + result[i]["id"] + "</td>" +
                    "<td>" + result[i]["routeId"] + "</td>" +
                    "<td>" + result[i]["trainType"] + "</td>" +
                    "<td>" + result[i]["basicPriceRate"] + "</td>" +
                    "<td>" + result[i]["firstClassPriceRate"] + "</td>" +
                    "</tr>"
                );
            }
            $("#single_list_price_status").text("true");
        },
        complete: function(){
            $("#price_queryAll_button").attr("disabled",false);
        }
    });
});

$("#price_create_button").click(function(){
    var priceUpdateInfo = new Object();
    priceUpdateInfo.trainType = $("#price_create_trainType").val();
    if(priceUpdateInfo.trainType == null || priceUpdateInfo.trainType == ""){
        alert("Please input train type.");
        return;
    }
    priceUpdateInfo.routeId = $("#price_create_routeId").val();
    if(priceUpdateInfo.routeId == null || priceUpdateInfo.routeId == ""){
        alert("Please input the route id.");
        return;
    }
    priceUpdateInfo.basicPriceRate = $("#price_create_basicPriceRate").val();
    if(priceUpdateInfo.basicPriceRate == null || priceUpdateInfo.basicPriceRate == ""){
        alert("Please input the basic price rate.");
        return;
    }
    priceUpdateInfo.firstClassPriceRate = $("#price_create_basicPriceRate").val();
    if(priceUpdateInfo.firstClassPriceRate == null || priceUpdateInfo.firstClassPriceRate == ""){
        alert("Please input the basic price rate.");
        return;
    }
    priceUpdateInfo.id = "";
    var data = JSON.stringify(priceUpdateInfo);
    $("#price_create_button").attr("disabled",true);
    $("#single_create_price_status").text("false");
    $.ajax({
        type: "post",
        url: "/price/create",
        contentType: "application/json",
        data:data,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#single_create_price_status").text("true");
            // $("#price_result").html(result);
        },
        complete: function(){
            $("#price_create_button").attr("disabled",false);
        }
    });
});

