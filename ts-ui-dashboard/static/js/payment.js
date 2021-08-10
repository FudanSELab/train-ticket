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

/***********************************************************************/
/********************Function For Payment Service***********************/
/********************Used For Payment Service Single Microservice Test**/

$("#payment_query_button").click(function(){
    $("#payment_query_button").attr("disabled",true);
    $("#single_list_payment_status").text("false");
    $.ajax({
        type: "get",
        url: "/payment/query",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_payment_list_table").find("tbody").html("");
            // $("#payment_result_table").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_payment_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"] + "</td>" +
                    "<td>" + result[i]["orderId"] + "</td>" +
                    "<td>" + result[i]["userId"] + "</td>" +
                    "<td>" + result[i]["price"] + "</td>" +
                    "</tr>"
                );
            }
            $("#single_list_payment_status").text("true");
        },
        complete: function(){
            $("#payment_query_button").attr("disabled",false);
        }
    });
});

$("#payment_pay_button").click(function(){
    var info = new Object();
    info.orderId = $("#payment_orderId").val();
    if(info.orderId == null || info.orderId == ""){
        alert("Please input the order id you want to pay.");
        return;
    }
    info.price = $("#payment_price").val();
    if(info.price == null || info.price == ""){
        alert("Please input the price you want to pay.");
        return;
    }
    info.userId = $("#payment_userId").val();
    if(info.userId == null || info.userId == ""){
        alert("Please input the account id.");
        return;
    }
    var data = JSON.stringify(info);
    $("#payment_pay_button").attr("disabled",true);
    $("#single_payment_status").text("false");
    $.ajax({
        type: "post",
        url: "/payment/pay",
        contentType: "application/json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#payment_result").html(result.toString());
            $("#single_payment_status").text("true");
        },
        complete: function(){
            $("#payment_pay_button").attr("disabled",false);
        }
    });
});