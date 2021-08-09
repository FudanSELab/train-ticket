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

$("#transit_search_button").click(function(){
    var transitSearchInfo = new Object();
    transitSearchInfo.fromStationName = $("#transit_start_station").val();
    transitSearchInfo.viaStationName = $("#transit_middle_station").val();
    transitSearchInfo.toStationName = $("#transit_end_station").val();
    transitSearchInfo.travelDate = $("#transit_end_station_date").val();
    if(transitSearchInfo.travelDate  == null || checkDateFormat(transitSearchInfo.travelDate ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    transitSearchInfo.trainType = 0;
    var transitSearchData = JSON.stringify(transitSearchInfo);
    $("#transit_search_list_table_first_section_table").find("tbody").html("");
    $("#transit_search_list_table_second_section_table").find("tbody").html("");
    queryForTransitTravelInfo(transitSearchData,"/travelPlan/getTransferResult");
});

function queryForTransitTravelInfo(data,path) {
    $("#transit_search_button").attr("disabled",true);
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data: data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status = true) {
                var obj = result["firstSectionResult"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#transit_search_list_table_first_section_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["startingStation"] + "</td>" +
                        "<td >" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["economyClass"] + "</td>" +
                        "<td>" + obj[i]["confortClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "</tr>"
                    );
                }
            }
            if (result.status = true) {
                var obj = result["secondSectionResult"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#transit_search_list_table_second_section_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["startingStation"] + "</td>" +
                        "<td >" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["economyClass"] + "</td>" +
                        "<td>" + obj[i]["confortClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "</tr>"
                    );
                }
            }
        },
        complete: function () {
            $("#transit_search_button").attr("disabled",false);
        }
    });
}