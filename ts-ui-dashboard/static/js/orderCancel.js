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

/*****************************************************************************/
/********************Function For Order Cancel Service************************/
/********************Used For Order Cancel Service Single Microservice Test***/

$("#single_cancel_button").click(function(){
    document.cookie="loginToken=admin";
    var cancelOrderInfo = new Object();
    cancelOrderInfo.orderId =  $("#single_cancel_order_id").val();
    if(cancelOrderInfo.orderId == null || cancelOrderInfo.orderId == ""){
        alert("Please input the order ID that you want to cancel.");
        return;
    }
    var cancelOrderInfoData = JSON.stringify(cancelOrderInfo);
    $("#single_cancel_button").attr("disabled",true);
    $("#single_cancel_order_status").text("false");
    $.ajax({
        type: "post",
        url: "/cancelOrder",
        contentType: "application/json",
        dataType: "json",
        data: cancelOrderInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#single_cancel_order_result").text(result["message"]);
            if(result["status"] == true){
                //
            }
            $("#single_cancel_order_status").text("true");
        },
        complete: function(){
            $("#single_cancel_button").attr("disabled",false);
        }
    });
});

$("#single_cancel_refund_button").click(function(){
    var cancelOrderInfo = new Object();
    cancelOrderInfo.orderId =  $("#single_cancel_order_id").val();
    if(cancelOrderInfo.orderId == null || cancelOrderInfo.orderId == ""){
        alert("Please input the order ID that you want to cancel.");
        return;
    }
    var cancelOrderInfoData = JSON.stringify(cancelOrderInfo);
    $("#single_cancel_refund_button").attr("disabled",true);
    $("#single_cancel_refund_status").text("false");
    $.ajax({
        type: "post",
        url: "/cancelCalculateRefund",
        contentType: "application/json",
        dataType: "json",
        data: cancelOrderInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#single_cancel_refund_result").text(result["refund"]);
            if(result["status"] == true){
                //
            }
            $("#single_cancel_refund_status").text("true");
        },
        complete: function(){
            $("#single_cancel_refund_button").attr("disabled",false);
        }
    });
});
