$("#travel_min_stop_button").click(function(){
    var minStopSearchInfo = new Object();
    minStopSearchInfo.startStation = $("#travel_min_stop_fromPlace").val();
    minStopSearchInfo.endStation = $("#travel_min_stop_toPlace").val();
    minStopSearchInfo.travelDate = $("#travel_min_stop_date").val();
    minStopSearchInfo.num = 5;
    if(minStopSearchInfo.travelDate  == null || checkDateFormat(minStopSearchInfo.travelDate ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var minStopSearchData = JSON.stringify(minStopSearchInfo);
    $("#tickets_min_stop_table").find("tbody").html("");
    queryForMinStopInfo(minStopSearchData,"/routePlan/minStopStations");
});

function queryForMinStopInfo(data,path) {
    $("#travel_min_stop_button").attr("disabled",true);
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
                var obj = result["results"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#tickets_min_stop_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["startStation"] + "</td>" +
                        "<td >" + obj[i]["endStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" +
                        "<select class='form-control'>" +
                        "<option value='2'>1st - " + obj[i]["priceForFirstClassSeat"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForSecondClassSeat"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='noshow_component'>" + obj[i]["priceForFirstClassSeat"] + "</td>"+
                        "<td class='noshow_component'>" + obj[i]["priceForSecondClassSeat"] + "</td>"+
                        "</tr>"
                    );
                }
            }
        },
        complete: function () {
            $("#travel_min_stop_button").attr("disabled",false);
        }
    });
}



$("#travel_quickest_route_button").click(function(){
    var qucikestSearchInfo = new Object();
    qucikestSearchInfo.startStation = $("#travel_quickest_route_fromPlace").val();
    qucikestSearchInfo.endStation = $("#travel_quickest_route_toPlace").val();
    qucikestSearchInfo.travelDate = $("#travel_quickest_route_date").val();
    qucikestSearchInfo.num = 5;
    if(qucikestSearchInfo.travelDate  == null || checkDateFormat(qucikestSearchInfo.travelDate ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var qucikestSearchData = JSON.stringify(qucikestSearchInfo);
    $("#tickets_quickest_route_table").find("tbody").html("");
    queryForQuickestInfo(qucikestSearchData,"/routePlan/quickestRoute");
});

function queryForQuickestInfo(data,path) {
    $("#travel_quickest_route_button").attr("disabled",true);
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
                var obj = result;
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#tickets_quickest_route_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["startStation"] + "</td>" +
                        "<td >" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
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
            $("#travel_quickest_route_button").attr("disabled",false);
        }
    });
}



$("#travel_cheapest_route_button").click(function(){
    var cheapestSearchInfo = new Object();
    cheapestSearchInfo.startStation = $("#travel_cheapest_route_fromPlace").val();
    cheapestSearchInfo.endStation = $("#travel_cheapest_route_toPlace").val();
    cheapestSearchInfo.travelDate = $("#travel_cheapest_route_date").val();
    cheapestSearchInfo.num = 5;
    if(cheapestSearchInfo.travelDate  == null || checkDateFormat(cheapestSearchInfo.travelDate ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var cheapestSearchData = JSON.stringify(cheapestSearchInfo);
    $("#tickets_cheapest_route_table").find("tbody").html("");
    queryForCheapestInfo(cheapestSearchData,"/routePlan/cheapestRoute");
});

function queryForCheapestInfo(data,path) {
    $("#travel_cheapest_route_button").attr("disabled",true);
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
                var obj = result;
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#tickets_cheapest_route_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["startStation"] + "</td>" +
                        "<td >" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
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
            $("#travel_cheapest_route_button").attr("disabled",false);
        }
    });
}