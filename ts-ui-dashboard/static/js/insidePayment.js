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

/**********************************************************************/
/********************Function For Inside Payment Service***************/
/********************Used for Inside-Payment Single Microservice Test**/

$("#inside_payment_query_account_button").click(function(){
    $("#inside_payment_query_account_button").attr("disabled",true);
    $("#single_user_balance_status").text("false");
    $.ajax({
        type: "get",
        url: "/inside_payment/queryAccount",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_inside_payment_account_list_table").find("tbody").html("");
            // $("#inside_payment_payment_result_table").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_inside_payment_account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["userId"] + "</td>" +
                    "<td>" + result[i]["balance"] + "</td>" +
                    "</tr>"
                );
            }
            $("#single_user_balance_status").text("true");
        },
        complete: function(){
            $("#inside_payment_query_account_button").attr("disabled",false);
        }
    });
});

$("#inside_payment_query_payment_button").click(function(){
    $("#inside_payment_query_payment_button").attr("disabled",true);
    $("#single_list_insite_payment_status").text("false");
    $.ajax({
        type: "get",
        url: "/inside_payment/queryPayment",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_inside_payment_payment_list_table").find("tbody").html("");
            // $("#inside_payment_account_result_table").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_inside_payment_payment_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"] + "</td>" +
                    "<td>" + result[i]["orderId"] + "</td>" +
                    "<td>" + result[i]["userId"] + "</td>" +
                    "<td>" + result[i]["price"] + "</td>" +
                    "<td>" + result[i]["type"] + "</td>" +
                    "</tr>"
                );
            }
            $("#single_list_insite_payment_status").text("true");
        },
        complete: function(){
            $("#inside_payment_query_payment_button").attr("disabled",false);
        }
    });
});

$("#inside_payment_query_add_money_button").click(function(){
    $("#inside_payment_query_add_money_button").attr("disabled",true);
    $("#single_list_insite_payment_add_money_status").text("false");
    $.ajax({
        type: "get",
        url: "/inside_payment/queryAddMoney",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_inside_payment_add_money_list_table").find("tbody").html("");
            // $("#inside_payment_account_result_table").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_inside_payment_add_money_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"] + "</td>" +
                    "<td>" + result[i]["userId"] + "</td>" +
                    "<td>" + result[i]["money"] + "</td>" +
                    "<td>" + result[i]["type"] + "</td>" +
                    "</tr>"
                );
            }
            $("#single_list_insite_payment_add_money_status").text("true");
        },
        complete: function(){
            $("#inside_payment_query_add_money_button").attr("disabled",false);
        }
    });
});

$("#inside_payment_pay_button").click(function(){
    var info = new Object();
    info.orderId = $("#inside_payment_orderId").val();
    if(info.orderId == null || info.orderId == ""){
        alert("Please the order ID.");
        return;
    }
    info.tripId = $("#inside_payment_tripId").val();
    if(info.tripId == null || info.tripId == ""){
        alert("Please the trip ID.");
        return;
    }
    var data = JSON.stringify(info);
    $("#inside_payment_pay_button").attr("disabled",true);
    $("#single_inside_payment_status").text("false");
    $.ajax({
        type: "post",
        url: "/inside_payment/pay",
        contentType: "application/json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#inside_payment_result").html(result.toString());
            $("#single_inside_payment_status").text("true");
        },
        complete: function(){
            $("#inside_payment_pay_button").attr("disabled",false);
        }
    });
});