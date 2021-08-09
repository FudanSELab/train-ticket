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
/********************Function For Order Rebook Service************************/
/********************Used For Order Rebook Service Single Microservice Test***/

$("#rebook_pay_button").click(function(){
    var singleRebookInfo = new Object();
    singleRebookInfo.orderId = $("#single_rebook_order_id").val();
    if(singleRebookInfo.orderId == null || singleRebookInfo.orderId == ""){
        alert("Please input the order ID.");
        return;
    }
    singleRebookInfo.oldTripId = $("#single_rebook_old_trip_id").val();
    if(singleRebookInfo.oldTripId == null || singleRebookInfo.oldTripId == ""){
        alert("Please input the old trip ID.");
        return;
    }
    singleRebookInfo.tripId = $("#single_rebook_trip_id").val();
    if(singleRebookInfo.tripId == null || singleRebookInfo.tripId == ""){
        alert("Please input the new trip Id.");
        return;
    }
    singleRebookInfo.seatType = $("#single_rebook_seat_type").val();
    if(singleRebookInfo.seatType == null || singleRebookInfo.seatType == ""){
        alert("Please select the seat type.");
        return;
    }
    singleRebookInfo.date = $("#single_rebook_date").val();
    if(singleRebookInfo.date == null || singleRebookInfo.date == ""){
        alert("Please select the date.");
        return;
    }
    var singleRebookInfoData = JSON.stringify(singleRebookInfo);
    $("#rebook_pay_button").attr("disabled",true);
    $("#rebook_pay_status").text("false");
    $.ajax({
        type: "post",
        url: "/api/v1/rebookservice/rebook/difference",
        contentType: "application/json",
        dataType: "json",
        data: singleRebookInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#rebook_payment_result").text(result["status"].toString());
            $("#rebook_pay_status").text("true");
        },
        complete: function(){
            $("#rebook_pay_button").attr("disabled",false);
        }

    });
});


$("#single_rebook_button").click(function() {
    var singleRebookInfo = new Object();
    singleRebookInfo.orderId = $("#single_rebook_order_id").val();
    if(singleRebookInfo.orderId == null || singleRebookInfo.orderId == ""){
        alert("Please input the order ID.");
        return;
    }
    singleRebookInfo.oldTripId = $("#single_rebook_old_trip_id").val();
    if(singleRebookInfo.oldTripId == null || singleRebookInfo.oldTripId == ""){
        alert("Please input the old trip Id.");
        return;
    }
    singleRebookInfo.tripId = $("#single_rebook_trip_id").val();
    if(singleRebookInfo.tripId == null || singleRebookInfo.tripId == ""){
        alert("Please input the new trip Id.");
        return;
    }
    singleRebookInfo.seatType = $("#single_rebook_seat_type").val();
    if(singleRebookInfo.seatType == null || singleRebookInfo.seatType == ""){
        alert("Please select the seat type.");
        return;
    }
    singleRebookInfo.date = $("#single_rebook_date").val();
    if(singleRebookInfo.date == null || singleRebookInfo.date == ""){
        alert("Please select the date.");
        return;
    }
    var singleRebookInfoData = JSON.stringify(singleRebookInfo);
    $("#single_rebook_button").attr("disabled",true);
    $("#single_rebook_status").text("false");
    $.ajax({
        type: "post",
        url: "/rebook/rebook",
        contentType: "application/json",
        dataType: "json",
        data: singleRebookInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if(result["status"] == true){
                $("#single_rebook_result").text("true");
            }else{
                $("#single_rebook_result").text(result["message"].toString());
                //if(result["message"].contains("Please pay the different money")){
                    $("#rebook_price").val(result["price"]);
                //}
            }
            $("#single_rebook_status").text("true");
        },
        complete: function(){
            $("#single_rebook_button").attr("disabled",false);
        }
    });
});



