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

//----------------Switch from many tags
//For toggle pages
$("#microservice_page").click(function(){
    $("#microservices").css('display','block');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','none');
    $("#flow_four").css('display','none');
});

$("#flow_one_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','block');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','none');
    $("#flow_four").css('display','none');
    location.hash="anchor_flow_preserve_login";
});

$("#flow_two_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','block');
    $("#flow_three").css('display','none');
    $("#flow_four").css('display','none');
});

$("#flow_three_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','block');
    $("#flow_four").css('display','none');
});

$("#flow_four_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','none');
    $("#flow_four").css('display','block');
});

// function addListenerToPaymentTable(){
//     var ticketPaymentButtonSet = $(".ticket_payment_button");
//     for(var i = 0;i < ticketPaymentButtonSet.length;i++){
//         ticketPaymentButtonSet[i].onclick = function(){
//             var tripId = $(this).parents("tr").find(".booking_tripId").text();
//             var loginToken = $("#user_login_token").html();
//             var accountId = $("#user_login_id").html();
//             var contactsId = "";
//             var radios = $(".booking_contacts_select");
//             for (var j = 0; j < radios.length; j++) {
//                 if (radios[j].checked) {
//                     contactsId = $(".booking_contacts_contactsId").eq(j).text();
//                 }
//             }
//             var orderTicketInfo = new Object();
//             orderTicketInfo.contactsId = contactsId;
//             orderTicketInfo.tripId = tripId;
//             orderTicketInfo.seatType = seatType;
//             orderTicketInfo.loginToken = loginToken;
//             orderTicketInfo.accountId = accountId;
//             orderTicketInfo.date = date;
//             orderTicketInfo.from = from;
//             orderTicketInfo.to = to;
//             var orderTicketsData = JSON.stringify(orderTicketInfo);
//             $.ajax({
//                 type: "post",
//                 url: "/inside_payment/pay",
//                 contentType: "application/json",
//                 dataType: "json",
//                 data: orderTicketsData,
//                 xhrFields: {
//                     withCredentials: true
//                 },
//                 success: function (result) {
//
//                 }
//             })
//         }
//     }
// }

/********************************************************************/
/***************************Some Basic Function**********************/

function convertNumberToDocumentType(code) {
    var str = "";
    if(code == 0){
        str = "null";
    }else if(code == 1){
        str = "ID Card";
    }else if(code == 2){
        str = "Passport";
    }else{
        str = "other";
    }
    return str;
}

function convertNumberToSeatClass(code){
    var str = "";
    if(code == 2){
        str = "First Class Seat";
    }else if(code == 3){
        str = "Second Class Seat";
    }else{
        str = "other";
    }
    return str;
}

function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}

function mergeTwoDate(dateOne,dateTwo) {
    var one = new Date(dateOne);
    var two = new Date(dateTwo);
    // var year = one.getFullYear();
    // var month = one.getMonth();
    // var day = one.getDay();
    // var hour = two.getHours();
    // var minute = two.getMinutes();
    // ..var datetime = year + ":" + month + ":" + day + "  " + hour + ":" + minute;
    return one.toDateString() + " " + two.toTimeString();
}

function convertStringToTime(string){
    var date = new Date();
    var s = string.toString();
    var index = s.indexOf(':');
    var hour = s.substring(0,index).valueOf();
    var minute = s.substring(index+1,s.length).valueOf();
    date.setHours(hour);
    date.setMinutes(minute);
    return date;
}
