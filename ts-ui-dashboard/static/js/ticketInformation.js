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


/****************************************************************************/
/*****************Function For Ticket Information****************************/
/*****************For Ticket Information Service Single Microservice Test****/

$("#ticketinfo_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#ticketinfo_tripId").val();
    if(travelInfo.tripId == null || travelInfo.tripId == ""){
        alert("Please input the trip ID.");
        return;
    }
    travelInfo.trainTypeId = $("#ticketinfo_trainTypeId").val();
    if(travelInfo.trainTypeId == null || travelInfo.trainTypeId == ""){
        alert("Please input the ID of the train type.");
        return;
    }
    travelInfo.startingStation =  $("#ticketinfo_startingStation").val();
    if(travelInfo.startingStation == null || travelInfo.startingStation == ""){
        alert("Please input the start stations.");
        return;
    }
    travelInfo.stations = $("#ticketinfo_stations").val();
    if(travelInfo.stations == null || travelInfo.stations == ""){
        alert("Please input the middle station.");
        return;
    }
    travelInfo.terminalStation = $("#ticketinfo_terminalStation").val();
    if(travelInfo.terminalStation == null || travelInfo.terminalStation == ""){
        alert("Please input the terminal station.");
        return;
    }
    travelInfo.startingTime = convertStringToTime($("#ticketinfo_startingTime").val());
    if(travelInfo.startingTime == null || travelInfo.startingTime == ""){
        alert("Please input the start time.");
        return;
    }
    travelInfo.endTime = convertStringToTime($("#ticketinfo_endTime").val());
    if(travelInfo.endTime == null || travelInfo.endTime == ""){
        alert("Please input the end time.");
        return;
    }
    var ticketInfo = new Object();
    ticketInfo.trip = travelInfo;
    ticketInfo.startingPlace = $("#ticketinfo_startingPlace").val();
    if(ticketInfo.startingPlace == null || ticketInfo.startingPlace == ""){
        alert("Please input the start station.");
        return;
    }
    ticketInfo.endPlace = $("#ticketinfo_endPlace").val();
    if(ticketInfo.endPlace == null || ticketInfo.endPlace == ""){
        alert("Please input the end station.");
        return;
    }
    ticketInfo.departureTime = $("#ticketinfo_departureTime").val();
    if(ticketInfo.departureTime == null || ticketInfo.departureTime == ""){
        alert("Please input the departure time.");
        return;
    }
    var data = JSON.stringify(ticketInfo);
    $("#ticketinfo_button").attr("disabled",true);
    $("#single_list_ticketInfo_status").text("false");
    $.ajax({
        type: "post",
        url: "/ticketinfo/queryForTravel",
        contentType: "application/json",
        data:data,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#query_ticketinfo_list_table").find("tbody").html("");
            $("#query_ticketinfo_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + result["status"] + "</td>" +
                "<td>" + result["percent"] + "</td>" +
                "<td>" + result["trainType"]["id"] + "</td>" +
                "<td>" + result["trainType"]["economyClass"] + "</td>" +
                "<td>" + result["trainType"]["confortClass"] + "</td>" +
                "<td>" + result["prices"]["economyClass"] + "</td>" +
                "<td>" + result["prices"]["confortClass"] + "</td>" +
                "</tr>"
            );
            $("#single_list_ticketInfo_status").text("true");
        },
        complete: function(){
            $("#ticketinfo_button").attr("disabled",false);
        }
    });
});

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