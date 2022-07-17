/***********************************************************/
/******************Flow For Rebook Ticket*******************/
/**Before ***/
function setTodayDateRebook(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd < 10){
        dd='0' + dd
    }
    if(mm < 10){
        mm = '0' + mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("travel_booking_date").setAttribute("min", today);
}


/**
 *  Flow Rebook - Step 1 - Refresh Your Orders
 **/

$("#refresh_my_order_list_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    queryMyOrder();
});

function queryMyOrder(){
    var myOrdersQueryInfo = new Object();
    myOrdersQueryInfo.enableStateQuery = false;
    myOrdersQueryInfo.enableTravelDateQuery = false;
    myOrdersQueryInfo.enableBoughtDateQuery = false;
    myOrdersQueryInfo.travelDateStart = null;
    myOrdersQueryInfo.travelDateEnd = null;
    myOrdersQueryInfo.boughtDateStart = null;
    myOrdersQueryInfo.boughtDateEnd = null;
    var myOrdersQueryData = JSON.stringify(myOrdersQueryInfo);
    $("#my_orders_result").html("");
    queryForMyOrder("/order/queryForRefresh",myOrdersQueryData);
    queryForMyOrder("/orderOther/queryForRefresh",myOrdersQueryData);
}

function queryForMyOrder(path,data){
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            for(var i = 0; i < size;i++){
                var order = result[i];
                var fromString = order['from'];
                var toString  = order['to'];
                $("#my_orders_result").append(
                    "<div class='panel panel-default'>" +
                    "<div class='panel-heading'>" +
                    "<h4 class='panel-title'>" +
                    "<label>" +
                    // "<a data-toggle='collapse' href='#collapse" + i + "'>" +
                    "From:" + fromString + "    ----->    To:" + toString +
                    // "</a>" +
                    "</label>" +
                    "</h4>" +
                    "</div>" +
                    "<div>" +
                    "<div id='collapse" + i + "' class='panel-collapse collapse in'>" +
                    "<div class='panel-body'>" +
                    "<form role='form' class='form-horizontal'>" +
                    "<div class='div form-group'>" +
                    "<label class='col-sm-2 control-label'>Order ID: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label my_order_list_id'>" + order["id"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='div form-group'>" +
                    "<label class='col-sm-2 control-label'>From: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label my_order_list_from'>" + fromString + "</label>" +
                    "<label class='control-label noshow_component my_order_list_from_name'>" + order["from"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='div form-group'>" +
                    "<label class='col-sm-2 control-label'>To: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label my_order_list_to'>" + toString + "</label>" +
                    "<label class='control-label noshow_component my_order_list_to_name'>" + order["to"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Bought Date: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label'>" + convertNumberToDateTimeString(order["boughtDate"]) + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Trip Id: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label my_order_list_train_number'>" + order["trainNumber"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Seat Number: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label'>" + order["seatNumber"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Status: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='noshow_component my_order_list_status'>" + order["status"] + "</label>" +
                    "<label class='control-label'>" + convertNumberToOrderStatus(order["status"]) + "</label>" +
                    addPayButtonOrNot(order["status"]) +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Price: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label my_order_list_price'>" + order["price"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Name: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label'>" + order["contactsName"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Document Type: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label'>" + convertNumberToDocumentType(order["documentType"]) + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>DocumentNumber: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='control-label'>" + order["contactsDocumentNumber"] + "</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-group'>" +
                    "<label class='col-sm-2 control-label'>Operation: </label>" +
                    "<div class='col-sm-10'>" +
                    "<label class='order_id control-label noshow_component' >" + order["id"] + "</label>" +
                    "<label class='order_id control-label noshow_component my_order_list_accountId' >" + order["accountId"] + "</label>" +
                    addCancelAandRebookButtonOrNot(order) +
                    // "<button type='button' class='ticket_cancel_btn btn btn-primary'>" + "Cancel Order" + "</button>" +
                    // "<button type='button' class='order_rebook_btn btn btn-primary'>" + "Change your railway ticket" + "</button>" +
                    "</div>" +
                    "</div>" +
                    "</form>" +
                    "</div>" +
                    "</div>" +
                    "</div>"
                );
            }
            addListenerToOrderCancel();
            addListenerToOrderChange();
            addListenerToPayOrderButton();
        }
    });
}

function addCancelAandRebookButtonOrNot(order){
    var str = "";
    if(order["status"] == 1){
        str += "<button type='button' class='order_rebook_btn btn btn-primary'>" + "Change your railway ticket" + "</button>";
    }
    if(order["status"] == 1 || order['status'] == 0 || order["status"] == 3){
        str += "<button type='button' class='ticket_cancel_btn btn btn-primary'>" + "Cancel Order" + "</button>";
    }
    return str;
}

function addListenerToOrderChange(){
    var ticketChangeButtonSet = $(".order_rebook_btn");
    for(var i = 0;i < ticketChangeButtonSet.length;i++){
        ticketChangeButtonSet[i].onclick = function(){
            var changeStartingPlaceName = $(this).parents("form").find(".my_order_list_from_name").text();
            var changeEndPlaceName = $(this).parents("form").find(".my_order_list_to_name").text();
            var orderStatus = $(this).parents("form").find(".my_order_list_status").text();
            if(orderStatus != 1){
                alert("Order Can Not Be Changed");
                return;
            }
            replaceStationId(changeStartingPlaceName,changeEndPlaceName);
            //$("#order_rebook_panel").css('display','block');
            location.hash="anchor_flow_rebook_rebook_orders";
            //Set Information on confirm page
            $("#ticket_rebook_confirm_old_order_id").text($(this).parents("form").find(".my_order_list_id").text());
            $("#ticket_rebook_confirm_old_tripId").text($(this).parents("form").find(".my_order_list_train_number").text());
        }
    }
}

function addPayButtonOrNot(status){
    if(status == '0'){
        return "<button type='button' class='pay_for_order_not_paid_btn btn btn-primary'>Pay</button>";
    }else{
        return "";
    }
}

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

function convertNumberToDateTimeString(timeNumber){
    var str = new Date(timeNumber);
    return str.toDateString() + " " + str.toTimeString();
}

function convertNumberToOrderStatus(code){
    var str = "";
    if(code == 0){
        str = "Not Paid";
    }else if(code == 1){
        str = "Paid & Not Collected";
    }else if(code == 2){
        str = "Collected";
    }else if(code == 3){
        str = "Cancel & Rebook";
    }else if(code == 4){
        str = "Cancel";
    }else if(code == 5){
        str = "Refunded";
    }else if(code == 6){
        str = "Used";
    }else{
        str = "other";
    }
    return str;
}

function getStationNameById(stationId){
    var stationName;
    var getStationInfoOne = new Object();
    getStationInfoOne.stationId =  stationId;
    var getStationInfoOneData = JSON.stringify(getStationInfoOne);
    $.ajax({
        type: "post",
        url: "/station/queryById",
        contentType: "application/json",
        dataType: "json",
        data:getStationInfoOneData,
        async: false,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            stationName = result["name"];
            //alert("AJAX Station Name:" + stationName);
        },
    });
    //alert("Return Station Name:" + stationName);
    return stationName;
}

function replaceStationId(stationNameOne,stationNameTwo){
    $("#travel_rebook_startingPlace").val(stationNameOne);
    $("#travel_rebook_terminalPlace").val(stationNameTwo);
    // var getStationInfoOne = new Object();
    // getStationInfoOne.stationId =  stationIdOne;
    // var getStationInfoOneData = JSON.stringify(getStationInfoOne);
    // $.ajax({
    //     type: "post",
    //     url: "/station/queryById",
    //     contentType: "application/json",
    //     dataType: "json",
    //     data:getStationInfoOneData,
    //     xhrFields: {
    //         withCredentials: true
    //     },
    //     success: function (result) {
    //         $("#travel_rebook_startingPlace").val(result["name"]);
    //     },
    // });
    // var getStationInfoTwo = new Object();
    // getStationInfoTwo.stationId =  stationIdTwo;
    // var getStationInfoTwoData = JSON.stringify(getStationInfoTwo);
    // $.ajax({
    //     type: "post",
    //     url: "/station/queryById",
    //     contentType: "application/json",
    //     dataType: "json",
    //     data:getStationInfoTwoData,
    //     xhrFields: {
    //         withCredentials: true
    //     },
    //     success: function (result) {
    //         $("#travel_rebook_terminalPlace").val(result["name"]);
    //     },
    // });
}

$("#travel_rebook_cancel").click(function(){
    location.hash="anchor_flow_rebook_orders";
    //$("#order_rebook_panel").css('display','none');
});

$("#travel_rebook_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var travelQueryInfo = new Object();
    travelQueryInfo.startPlace = $("#travel_rebook_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_rebook_terminalPlace").val();
    travelQueryInfo.departureTime = $("#travel_rebook_date").val();
    if(travelQueryInfo.departureTime == null || checkDateFormat(travelQueryInfo.departureTime) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var travelQueryData = JSON.stringify(travelQueryInfo);
    var train_type = $("#search_rebook_train_type").val();
    $("#tickets_change_list_table").find("tbody").html("");
    if(train_type == 0){
        queryForRebookTravelInfo(travelQueryData,"/travel/query");
        queryForRebookTravelInfo(travelQueryData,"/travel2/query");
    }else if(train_type == 1){
        queryForRebookTravelInfo(travelQueryData,"/travel/query");
    }else if(train_type == 2){
        queryForRebookTravelInfo(travelQueryData,"/travel2/query");
    }
});

function queryForRebookTravelInfo(data,path) {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data: data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result[0] != null){
                var obj = result;
                $("#tickets_change_list_table").find("tbody").html("");
                for(var i = 0,l = obj.length ; i < l ; i++){
                    $("#tickets_change_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='rebook_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td class='rebook_trainTypeId'>" + obj[i]["trainTypeId"] +  "</td>" +
                        "<td class='rebook_from'>" + obj[i]["startStation"]                             + "</td>" +
                        "<td class='rebook_to'>" + obj[i]["terminalStation"]                             + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startTime"])     + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"])          + "</td>" +
                        "<td>" + obj[i]["economyClass"]                                + "</td>" +
                        "<td>" + obj[i]["confortClass"]                                + "</td>" +
                        "<td>" +
                        "<select class='form-control rebook_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td>" + "<button class='btn btn-primary ticket_rebook_button'>" + "Rebook" + "</button>"  + "</td>" +
                        "</tr>"
                    );
                }
                addListenerToRebookTable();
            }
        }
    });
}

/**
 *  Flow Rebook - Step 2 - Select Which Trip You want to change.
 **/

function addListenerToRebookTable(){
    var ticketRebookButtonSet = $(".ticket_rebook_button");
    for(var i = 0;i < ticketRebookButtonSet.length;i++) {
        ticketRebookButtonSet[i].onclick = function () {
            $("#ticket_rebook_confirm_new_tripId").text($(this).parents("tr").find(".rebook_tripId").text());
            var seatType = $(this).parents("tr").find(".rebook_seat_class").val();
            $("#ticket_rebook_confirm_seatType").text(seatType);
            if(seatType == 2){
                $("#ticket_rebook_confirm_seatType_String").text("confort seat");
            }else if(seatType == 3){
                $("#ticket_rebook_confirm_seatType_String").text("economy seat");
            }
            $("#ticket_rebook_confirm_travel_date").text($("#travel_rebook_date").val());
            //$("#order_rebook_panel").css('display','none');
            location.hash="anchor_flow_rebook_confirm";
        }
    }
}

/**
 *  Flow Rebook - Step 3 - Confirm Your Ticket Information
 */

$("#ticket_rebook_confirm_cancel_btn").click(function(){
    alert("You Click Ticket Rebook Confirm Cancel Button");
});

$("#ticket_rebook_confirm_confirm_btn").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var rebookInfo = new Object();
    rebookInfo.orderId = $("#ticket_rebook_confirm_old_order_id").text();
    rebookInfo.oldTripId = $("#ticket_rebook_confirm_old_tripId").text();
    rebookInfo.tripId = $("#ticket_rebook_confirm_new_tripId").text();
    rebookInfo.seatType = $("#ticket_rebook_confirm_seatType").text();
    rebookInfo.date = $("#ticket_rebook_confirm_travel_date").text();
    var data = JSON.stringify(rebookInfo);
    $.ajax({
        type: "post",
        url: "/rebook/rebook",
        contentType: "application/json",
        dataType: "json",
        data: data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result["status"] == true){
                alert(result["message"]);
                location.hash="anchor_flow_rebook_orders";
                queryMyOrder();
            }else{
                alert(result["message"]);
                if(result['price'] != null || result['price'] != 'null'){
                    $("#rebook_money_pay").val(result["price"]);
                    location.hash="anchor_flow_rebook_pay";
                    //$("#ticket_rebook_pay_panel").css('display','block');
                }
            }
        }
    });
});



/**
 *  Flow Rebook - Step 4 - Pay Or Not Pay For Rebook The Ticket
 */

$("#ticket_rebook_pay_panel_cancel").click(function(){
    //$("#ticket_rebook_pay_panel").css('display','none');
});

$("#ticket_rebook_pay_panel_confirm").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var rebookPayInfo = new Object();
    rebookPayInfo.orderId = $("#ticket_rebook_confirm_old_order_id").text();;
    rebookPayInfo.oldTripId = $("#ticket_rebook_confirm_old_tripId").text();;
    rebookPayInfo.tripId = $("#ticket_rebook_confirm_new_tripId").text();
    rebookPayInfo.seatType = $("#ticket_rebook_confirm_seatType").text();
    rebookPayInfo.date = $("#ticket_rebook_confirm_travel_date").text();;
    var rebookPayInfoData = JSON.stringify(rebookPayInfo);
    $.ajax({
        type: "post",
        url: "/rebook/payDifference",
        contentType: "application/json",
        dataType: "json",
        data: rebookPayInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            alert(result['message']);
            location.hash="anchor_flow_rebook_orders";
            queryMyOrder();
        }
    });

    //$("#ticket_rebook_pay_panel").css('display','none');
    //$("#order_rebook_panel").css('display','none');
});

/**
 *  Pay For Not Paid Button
 */
function addListenerToPayOrderButton(){
    var orderNotPaidPayButtonSet = $(".pay_for_order_not_paid_btn");
    for(var i = 0;i < orderNotPaidPayButtonSet.length;i++){
        orderNotPaidPayButtonSet[i].onclick = function () {
            var orderId = $(this).parents("form").find(".my_order_list_id").text();
            var price = $(this).parents("form").find(".my_order_list_price").text();
            var tripId = $(this).parents("form").find(".my_order_list_train_number").text();
            $("#pay_for_not_paid_orderId").val(orderId);
            $("#pay_for_not_paid_price").val(price);
            $("#pay_for_not_paid_tripId").val(tripId);
            location.hash="anchor_flow_order_pay_for_not_paid";
        }
    }
}

$("#pay_for_not_paid_pay_later_button").click(function(){
    location.hash="anchor_flow_rebook_orders";
    $("#pay_for_not_paid_orderId").val("");
    $("#pay_for_not_paid_price").val("");
    $("#pay_for_not_paid_tripId").val("");
});

$("#pay_for_not_paid_pay_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var orderPayInfo = new Object();
    orderPayInfo.orderId = $("#pay_for_not_paid_orderId").val();
    orderPayInfo.tripId = $("#pay_for_not_paid_tripId").val();
    var orderPayData = JSON.stringify(orderPayInfo);
    $.ajax({
        type: "post",
        url: "/inside_payment/pay",
        contentType: "application/json",
        dataType: "text",
        data:orderPayData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if(result == "true"){
                alert("Success");
                location.hash="anchor_flow_rebook_orders";
                queryMyOrder();
            }else{
                alert("Some thing error");
            }
        }
    });
});

/**
 * Cancel Order
 */
function addListenerToOrderCancel(){
    var ticketCancelButtonSet = $(".ticket_cancel_btn");
    for(var i = 0;i < ticketCancelButtonSet.length;i++){
        ticketCancelButtonSet[i].onclick = function(){
            var orderStatus = $(this).parents("form").find(".my_order_list_status").text();
            if(orderStatus != 0 && orderStatus != 1 && orderStatus != 3){
                alert("Order Can Not Be Cancel");
                return;
            }
            $("#ticket_cancel_panel").css('display','block');
            var orderId = $(this).parents("form").find(".my_order_list_id").text();
            $("#ticket_cancel_order_id").text(orderId);
            var cancelOrderInfo = new Object();
            cancelOrderInfo.orderId = orderId;
            var cancelOrderData = JSON.stringify(cancelOrderInfo);
            $.ajax({
                type: "post",
                url: "/cancelCalculateRefund",
                contentType: "application/json",
                dataType: "json",
                data:cancelOrderData,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if(result["status"] == true){
                        $("#cancel_money_refund").text(result["refund"]);
                    }else{
                        $("#cancel_money_refund").text("Error ");
                    }
                },
            });
        }
    }
}

$("#ticket_cancel_panel_cancel").click(function(){
    $("#ticket_cancel_panel").css('display','none');
});

$("#ticket_cancel_panel_confirm").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var cancelOrderInfo = new Object();
    cancelOrderInfo.orderId =  $("#ticket_cancel_order_id").text();
    if(cancelOrderInfo.orderId == null || cancelOrderInfo.orderId == ""){
        alert("Please input the order ID that you want to cancel.");
        return;
    }
    var cancelOrderInfoData = JSON.stringify(cancelOrderInfo);
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
            if(result["status"] == true){
                $("#ticket_cancel_panel").css('display','none');
            }
            alert(result["message"]);
        }
    });
});

