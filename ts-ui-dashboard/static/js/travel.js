
/*********************************************************************/
/********************Function For Travel Service**********************/
/********************Used For Travel Service Single Microservice Test*/

// $("#travel_create_button").click(function(){
//     var travelCreateInfo = new Object();
// //     var travelCreateInfo = {
// //         tripId : $("#travel_create_tripId").val();
// // }
//     travelCreateInfo.tripId = $("#travel_create_tripId").val();
//     travelCreateInfo.trainTypeId = $("#travel_create_trainTypeId").val();
//     travelCreateInfo.startStation = $("#travel_create_startingStation").val();
//     travelCreateInfo.stations = $("#travel_create_stations").val();
//     travelCreateInfo.terminalStation = $("#travel_create_terminalStation").val();
//
//     travelCreateInfo.startTime = "1970-01-01T" +  $("#travel_create_startingTime").val() +":00Z";
//     travelCreateInfo.endTime = "1970-01-01T" +  $("#travel_create_endTime").val() +":00Z";
//
//     var travelCreateData = JSON.stringify(travelCreateInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/create",
//         contentType: "application/json",
//         dataType: "text",
//         data: travelCreateData,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(result);
//         }
//     });
// });

//------For Trip retrieve------------

$("#travel_queryAll_button").click(function(){
    $("#travel_queryAll_button").attr("disabled",true);
    $("#single_list_travel_status").text("false");
    $.ajax({
        type: "get",
        url: "/travel/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["routeId"]     + "</td>" +
                    "<td>" + result[i]["startingStationId"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
            $("#single_list_travel_status").text("true");
        },
        complete: function(){
            $("#travel_queryAll_button").attr("disabled",false);
        }
    });
});

$("#travel2_queryAll_button").click(function(){
    $("#travel2_queryAll_button").attr("disabled",true);
    $("#single_travel_other_query_status").text("false");
    $.ajax({
        type: "get",
        url: "/travel2/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel2_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel2_list_table_result").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["routeId"]     + "</td>" +
                    "<td>" + result[i]["startStationName"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
            $("#single_travel_other_query_status").text("true");
        },
        complete: function(){
            $("#travel2_queryAll_button").attr("disabled",false);
        }
    });
});

//------For Trip update------------
$("#travel_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel_update_tripId").val();
    if(travelInfo.tripId == null || travelInfo.tripId == ""){
        alert("Please input the trip ID.");
        return;
    }
    travelInfo.trainTypeId = $("#travel_update_trainTypeId").val();
    if(travelInfo.trainTypeId == null || travelInfo.trainTypeId == ""){
        alert("Please input the ID of the train type.");
        return;
    }
    travelInfo.routeId = $("#travel_update_routeId").val();
    if(travelInfo.routeId == null || travelInfo.routeId == ""){
        alert("Please input the ID of the route.");
        return;
    }
    // travelInfo.startStationName =  $("#travel_update_startingStationId").val();
    // if(travelInfo.startStationName == null || travelInfo.startStationName== ""){
    //     alert("Please input the ID of the starting station.");
    //     return;
    // }
    // travelInfo.stationsId = $("#travel_update_stationsId").val();
    // if(travelInfo.stationsId == null || travelInfo.stationsId == ""){
    //     alert("Please input the ID of the middle station.");
    //     return;
    // }
    // travelInfo.terminalStationId = $("#travel_update_terminalStationId").val();
    // if(travelInfo.terminalStationId == null || travelInfo.terminalStationId == ""){
    //     alert("Please input the ID of the terminal station.");
    //     return;
    // }
    // travelInfo.startTime = convertStringToTime($("#travel_update_startingTime").val());
    // if(travelInfo.startTime == null || travelInfo.startTime == ""){
    //     alert("Please input the starting time of the trip.");
    //     return;
    // }
    // travelInfo.endTime = convertStringToTime($("#travel_update_endTime").val());
    // if(travelInfo.endTime == null || travelInfo.endTime == ""){
    //     alert("Please input the arrival time of the trip.");
    //     return;
    // }
    var data = JSON.stringify(travelInfo);
    $("#travel_update_button").attr("disabled",true);
    $("#single_travel_update_status").text("false");
    $.ajax({
        type: "post",
        url: "/travel/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel_result").html(JSON.stringify(result));
            $("#single_travel_update_status").text("true");
        },
        complete: function(){
            $("#travel_update_button").attr("disabled",false);
        }
    });
});

$("#travel2_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel2_update_tripId").val();
    if(travelInfo.tripId == null || travelInfo.tripId == ""){
        alert("Please input the trip ID.");
        return;
    }
    travelInfo.trainTypeId = $("#travel2_update_trainTypeId").val();
    if(travelInfo.trainTypeId == null || travelInfo.trainTypeId == ""){
        alert("Please input the ID of the train type.");
        return;
    }
    travelInfo.routeId = $("#travel2_update_routeId").val();
    if(travelInfo.routeId == null || travelInfo.routeId == ""){
        alert("Please input the ID of the route.");
        return;
    }
    // travelInfo.startStationName =  $("#travel2_update_startingStationId").val();
    // if(travelInfo.trainTypeId == null || travelInfo.trainTypeId == ""){
    //     alert("Please input the ID of the starting station.");
    //     return;
    // }
    // travelInfo.stationsId = $("#travel2_update_stationsId").val();
    // if(travelInfo.stationsId == null || travelInfo.stationsId == ""){
    //     alert("Please input the ID of the middle station.");
    //     return;
    // }
    // travelInfo.terminalStationId = $("#travel2_update_terminalStationId").val();
    // if(travelInfo.terminalStationId == null || travelInfo.terminalStationId == ""){
    //     alert("Please input the ID of the terminal station.");
    //     return;
    // }
    // travelInfo.startTime = convertStringToTime($("#travel2_update_startingTime").val());
    // if(travelInfo.startTime == null || travelInfo.startTime == ""){
    //     alert("Please input the starting time of the trip.");
    //     return;
    // }
    // travelInfo.endTime = convertStringToTime($("#travel2_update_endTime").val());
    // if(travelInfo.endTime == null || travelInfo.endTime == ""){
    //     alert("Please input the arrival time of the trip.");
    //     return;
    // }
    var data = JSON.stringify(travelInfo);
    $("#travel2_update_button").attr("disabled",true);
    $("#single_travel_other_update_status").text("false");
    $.ajax({
        type: "post",
        url: "/travel2/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel2_result").html(JSON.stringify(result));
            $("#single_travel_other_update_status").text("true");
        },
        complete: function(){
            $("#travel2_update_button").attr("disabled",false);
        }
    });
});

//------For Trip delete------------
// $("#travel_delete_button").click(function(){
//     var travelInfo = new Object();
//     travelInfo.tripId = $("#travel_delete_tripId").val();
//     var data = JSON.stringify(travelInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(JSON.stringify(result));
//         }
//     });
// });

/********************************************************************/
/*******************Function For Travel Query************************/

function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
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