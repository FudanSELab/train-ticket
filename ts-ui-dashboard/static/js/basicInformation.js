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
    travelInfo.startStation =  $("#basic_information_startingStation").val();
    if(travelInfo.startStation == null || travelInfo.startStation == ""){
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
    travelInfo.startTime = convertStringToTime($("#basic_information_startingTime").val());
    if(travelInfo.startTime == null || travelInfo.startTime == ""){
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
    basicInfo.startPlace = $("#basic_information_startingPlace").val();
    if(basicInfo.startPlace == null || basicInfo.startPlace == ""){
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
