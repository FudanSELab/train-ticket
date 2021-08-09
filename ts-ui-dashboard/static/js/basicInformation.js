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
/*****************Function For Basic Information*********************/
/*****************For Basic Information Single Microservice Test*****/

$("#basic_information_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#basic_information_tripId").val();
    if(travelInfo.tripId == null || travelInfo.tripId == ""){
        alert("Please input the trip ID.");
        return;
    }
    travelInfo.trainTypeId = $("#basic_information_trainTypeId").val();
    if(travelInfo.trainTypeId == null || travelInfo.trainTypeId == ""){
        alert("Please input the type of the train.");
        return;
    }
    travelInfo.startingStation =  $("#basic_information_startingStation").val();
    if(travelInfo.startingStation == null || travelInfo.startingStation == ""){
        alert("Please input the starting station.");
        return;
    }
    travelInfo.stations = $("#basic_information_stations").val();
    if(travelInfo.stations == null || travelInfo.stations == ""){
        alert("Please input the middle station.");
        return;
    }
    travelInfo.terminalStation = $("#basic_information_terminalStation").val();
    if(travelInfo.terminalStation == null || travelInfo.terminalStation == ""){
        alert("Please input the terminal station.");
        return;
    }
    travelInfo.startingTime = convertStringToTime($("#basic_information_startingTime").val());
    if(travelInfo.startingTime == null || travelInfo.startingTime == ""){
        alert("Please input the starting time.");
        return;
    }
    travelInfo.endTime = convertStringToTime($("#basic_information_endTime").val());
    if(travelInfo.endTime == null || travelInfo.endTime == ""){
        alert("Please input the end time.");
        return;
    }
    var basicInfo = new Object();
    basicInfo.trip = travelInfo;
    basicInfo.startingPlace = $("#basic_information_startingPlace").val();
    if(basicInfo.startingPlace == null || basicInfo.startingPlace == ""){
        alert("Please input the starting place.");
        return;
    }
    basicInfo.endPlace = $("#basic_information_endPlace").val();
    if(basicInfo.endPlace == null || basicInfo.endPlace == ""){
        alert("Please input the end place.");
        return;
    }
    basicInfo.departureTime = $("#basic_information_departureTime").val();
    if(basicInfo.departureTime == null || basicInfo.departureTime == ""){
        alert("Please input the departure time.");
        return;
    }
    var data = JSON.stringify(basicInfo);
    $("#basic_information_button").attr("disabled",true);
    $("#single_query_basic_info_status").text("false");
    $.ajax({
        type: "post",
        url: "/basic/queryForTravel",
        contentType: "application/json",
        data:data,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#query_basic_information_list_table").find("tbody").html("");
            $("#query_basic_information_list_table").find("tbody").append(
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
            $("#single_query_basic_info_status").text("true");
        },
        error: function(){
            $("#single_query_basic_info_status").text("false");
        },
        complete: function(){
            $("#basic_information_button").attr("disabled",false);
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
