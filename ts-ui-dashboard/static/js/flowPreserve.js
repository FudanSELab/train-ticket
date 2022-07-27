/***********************************************************/
/******************Flow For Preserve Ticket*****************/
/**Before ***/
function setTodayDatePreserve(){
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
 *  Flow Preserve - Step 1 - User Login
 **/
$("#flow_preserve_login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.email = $("#flow_preserve_login_email").val();
    if(loginInfo.email == null || loginInfo.email == ""){
        alert("Email Can Not Be Empty.");
        return;
    }
    if(checkEmailFormat(loginInfo.email) == false){
        alert("Email Format Wrong.");
        return;
    }
    loginInfo.password = $("#flow_preserve_login_password").val();
    if(loginInfo.password == null || loginInfo.password == ""){
        alert("Password Can Not Be Empty.");
        return;
    }
    loginInfo.verificationCode = $("#flow_preserve_login_verification_code").val();
    if(loginInfo.verificationCode == null || loginInfo.verificationCode == ""){
        alert("Verification Code Can Not Be Empty.");
        return;
    }
    var data = JSON.stringify(loginInfo);
    $.ajax({
        type: "post",
        url: "/login",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#user_login_id").html(obj["account"].id);
                $("#user_login_token").html(obj["token"]);
                document.cookie = "loginId=" + obj["account"].id;
                document.cookie = "loginToken=" + obj["token"];
                $("#flow_preserve_login_status").text(obj["status"]);
                $("#flow_preserve_login_msg").text(obj["message"]);
                $("#user_login_id").text(obj["account"].id);
                location.hash="anchor_flow_preserve_select_trip";
            }else{
                // setCookie("loginId", "", -1);
                // setCookie("loginToken", "", -1);
                //alert(obj["message"]);
                $("#flow_preserve_login_msg").text(obj["message"]);
            }
        }
    });
});

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function checkEmailFormat(email){
    var emailFormat = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!emailFormat.test(email)){
        return false;
    }else{
        return true;
    }
}

/**
 *  Flow Preserve - Step 2 - Query Trips
 **/

$("#travel_booking_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startPlace = $("#travel_booking_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_booking_terminalPlace").val();
    travelQueryInfo.departureTime = $("#travel_booking_date").val();
    if(travelQueryInfo.departureTime == null || checkDateFormat(travelQueryInfo.departureTime) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var travelQueryData = JSON.stringify(travelQueryInfo);
    var train_type = $("#search_select_train_type").val();
    var i = 0;
    $("#tickets_booking_list_table").find("tbody").html("");
    if(train_type == 0){
        queryForTravelInfo(travelQueryData,"/travel/query");
        queryForTravelInfo(travelQueryData,"/travel2/query");
    }else if(train_type == 1){
        queryForTravelInfo(travelQueryData,"/travel/query");
    }else if(train_type == 2){
        queryForTravelInfo(travelQueryData,"/travel2/query");
    }
});

function checkDateFormat(date){
    var dateFormat = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
    if(!dateFormat.test(date)){
        return false;
    }else{
        return true;
    }
}

function queryForTravelInfo(data,path) {
    $("#travel_booking_button").attr("disabled",true);
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
            if (result[0] != null) {
                var obj = result;
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#tickets_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] + "</td>" +
                        "<td class='booking_from'>" + obj[i]["startStation"] + "</td>" +
                        "<td class='booking_to'>" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["economyClass"] + "</td>" +
                        "<td>" + obj[i]["confortClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control booking_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='booking_seat_price_confort noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='booking_seat_price_economy noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>" + "</td>" +
                        "</tr>"
                    );
                }
                addListenerToBookingTable();
            }
        },
        complete: function () {
            $("#travel_booking_button").attr("disabled",false);
        }
    });
}

function addListenerToBookingTable(){
    var ticketBookingButtonSet = $(".ticket_booking_button");
    for(var i = 0;i < ticketBookingButtonSet.length;i++){
        ticketBookingButtonSet[i].onclick = function(){
            var tripId = $(this).parents("tr").find(".booking_tripId").text();
            var from = $(this).parents("tr").find(".booking_from").text();
            var to = $(this).parents("tr").find(".booking_to").text();
            var seatType = $(this).parents("tr").find(".booking_seat_class").val();
            var date = $("#travel_booking_date").val();
            $("#ticket_confirm_from").text(from);
            $("#ticket_confirm_to").text(to);
            $("#ticket_confirm_tripId").text(tripId);
            if(seatType == 2){
                $("#ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_confort").text());
            }else if(seatType == 3){
                $("#ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_economy").text());
            }
            $("#ticket_confirm_travel_date").text(date);
            $("#ticket_confirm_seatType").text(seatType);
            if(seatType == 2){
                $("#ticket_confirm_seatType_String").text("confort seat");
            }else if(seatType == 3){
                $("#ticket_confirm_seatType_String").text("economy seat");
            }
            refresh_booking_contacts();
            initFoodSelect(tripId);
            location.hash="anchor_flow_preserve_select_contacts";
        }
    }
}

function needFoodOrNot(){
    if($('#need-food-or-not').is(':checked')){
        $('#food-preserve-select').css("display", "block");
    } else {
        $('#food-preserve-select').css("display", "none");
        $('#food-store-selected').css("display", "none");
        $('#train-food-selected').css("display", "none");
        $('#preserve_food_type').val(0);
    }
}

function changeFoodType(){
    var type = $('#preserve_food_type').find("option:selected").val();
    if(type == 1){
        $('#train-food-selected').css("display", "block");
        $('#food-store-selected').css("display", "none");
        $('#food-station-list').val(0);
    } else if(type == 2){
        $('#train-food-selected').css("display", "none");
        $('#food-store-selected').css("display", "block");
    } else {
        $('#train-food-selected').css("display", "none");
        $('#food-store-selected').css("display", "none");
    }
}

var  preserveFoodStoreListMap = null;

function initFoodSelect(tripId){
    var data = new Object();
    data.date = $('#travel_booking_date').val() || "";
    data.startStation = $('#travel_booking_startingPlace').val() || "";
    data.endStation = $('#travel_booking_terminalPlace').val() || "";
    data.tripId = tripId;

    // alert(JSON.stringify(data));
    $.ajax({
        type: "post",
        url: "/food/getFood",
        contentType: "application/json",
        dataType: "json",
        data:JSON.stringify(data),
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            console.log(result);
            if(result.status){

                if(null == result.trainFoodList || result.trainFoodList.length == 0){
                    //没有
                    $('#train-food-option').disabled(true);
                } else {
                    var trainFoodList = result.trainFoodList[0]['foodList'];
                    console.log("trainFoodList:" );
                    console.log(trainFoodList[0]);

                    $("#train-food-type-list").html("");
                    $("#food-station-list").html("");
                    $("#food-stores-list").html("");
                    $("#food-store-food-list").html("");

                    var trainFoodSelect = document.getElementById ("train-food-type-list");
                    var opt1 = document.createElement ("option");
                    opt1.value = 0;
                    opt1.innerText = "-- --";
                    trainFoodSelect.appendChild(opt1);
                    for(var k = 0; k < trainFoodList.length; k++){
                        var opt2 = document.createElement ("option");
                        opt2.value = k + 1;
                        opt2.innerText = trainFoodList[k]['foodName'] + ":$" + trainFoodList[k]['price'];
                        trainFoodSelect.appendChild (opt2);
                    }
                }


                preserveFoodStoreListMap = result.foodStoreListMap;
                console.log(" preserveFoodStoreListMap:");
                console.log( preserveFoodStoreListMap);
                var foodStationSelect = document.getElementById ("food-station-list");
                var opt3 = document.createElement ("option");
                opt3.value = 0;
                opt3.innerText = "-- --";
                foodStationSelect.appendChild(opt1);
                var fsindex = 1;
                for(var key in  preserveFoodStoreListMap){
                    var opt4 = document.createElement ("option");
                    opt4.value = fsindex;
                    fsindex++;
                    opt4.innerText = key;
                    foodStationSelect.appendChild (opt4);
                }

            } else {
                alert(result.status + ":" + result.message);
            }

        }
    });

}

function preserveChangeFoodStation(){
    var station = $('#food-station-list').find("option:selected").text();
    var  foodStoreList = preserveFoodStoreListMap[station];

    var foodStoreSelect = document.getElementById ("food-stores-list");
    foodStoreSelect.innerHTML = "";
    var opt5 = document.createElement ("option");
    opt5.value = 0;
    opt5.innerText = "-- --";
    foodStoreSelect.appendChild(opt5);
    for(var j = 0; j < foodStoreList.length; j++){
        var opt6 = document.createElement ("option");
        opt6.value = j+1;
        opt6.innerText = foodStoreList[j]["storeName"];
        foodStoreSelect.appendChild (opt6);
    }
}

function preserveChangeFoodStore(){
    var station = $('#food-station-list').find("option:selected").text();
    var storeIndex = parseInt($('#food-stores-list').find("option:selected").val());
    console.log("storeIndex=" +storeIndex);
    var  foodList = preserveFoodStoreListMap[station][storeIndex-1]['foodList'];
    console.log("preserveChangeFoodStore: foodList: ");
    console.log(foodList);
    
    var foodStoreFoodSelect = document.getElementById ("food-store-food-list");
    foodStoreFoodSelect.innerHTML = "";
    var opt5 = document.createElement ("option");
    opt5.value = 0;
    opt5.innerText = "-- --";
    foodStoreFoodSelect.appendChild(opt5);
    for(var j = 0; j < foodList.length; j++){
        var opt6 = document.createElement ("option");
        opt6.value = j+1;
        opt6.innerText = foodList[j]['foodName'] +":$" + foodList[j]['price'];
        foodStoreFoodSelect.appendChild (opt6);
    }
}

function needConsignOrNot(){
    if($('#need-consign-or-not').is(':checked')){
        $('.consign_input').css("display", "block");
    } else {
        $('.consign_input').css("display", "none");
    }
}

function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}

/**
 *  Flow Preserve - Step 3 - Query/Add Contacts
 **/

$("#refresh_booking_contacts_button").click(function(){
    refresh_booking_contacts();
})

function refresh_booking_contacts() {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#refresh_booking_contacts_button").attr("disabled",true);
    $.ajax({
        type: "get",
        url: "/contacts/findContacts",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var obj = result;
            $("#contacts_booking_list_table").find("tbody").html("");
            for (var i = 0, l = obj.length; i < l; i++) {
                $("#contacts_booking_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='booking_contacts_contactsId' style='display:none'>" + obj[i]["id"] + "</td>" +
                    "<td class='booking_contacts_name'>" + obj[i]["name"] + "</td>" +
                    "<td class='booking_contacts_documentType'>" + convertNumberToDocumentType(obj[i]["documentType"]) + "</td>" +
                    "<td class='booking_contacts_documentNumber'>" + obj[i]["documentNumber"] + "</td>" +
                    "<td class='booking_contacts_phoneNumber'>" + obj[i]["phoneNumber"] + "</td>" +
                    "<td>" + "<label><input class='booking_contacts_select' name='booking_contacts' type='radio' />" + "Select" + "</label>" + "</td>" +
                    "</tr>"
                );
            }
            $("#contacts_booking_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + obj.length + "</td>" +
                "<td class='booking_contacts_name'>" + "<input id='booking_new_contacts_name'>" + "</td>" +
                "<td>" +
                "<select id='booking_new_contacts_documentType' class='booking_contacts_documentType all form-control'>" +
                "<option value='1' selected = 'selected'>ID Card</option>" +
                "<option value='2'>Passport</option>" +
                "<option value='3'>Other</option>" +
                "</select>" +
                "</td>" +
                "<td class='booking_contacts_documentNumber'>" + "<input id='booking_new_contacts_documentNum'>" + "</td>" +
                "<td class='booking_contacts_phoneNumber'>" + "<input id='booking_new_contacts_phoneNum'>" + "</td>" +
                "<td>" + "<label><input id='booking_new_contacts_select' class='booking_contacts_select' name='booking_contacts' type='radio' />" + "Select" + "</label>" + "</td>" +
                "</tr>"
            );
        },
        complete: function(){
            $("#refresh_booking_contacts_button").attr("disabled",false);
        }
    });
}

$(function () {
    getAssuranceType();
});

//获取保险的类型
function getAssuranceType(){
    $.ajax({
        type: "get",
        url: "/assurance/getAllAssuranceType",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var obj = result;
            var types = document.getElementById ("assurance_type");
            var opt0 = document.createElement ("option");
            opt0.value = 0;
            opt0.innerText = "No Assurance";
            types.appendChild (opt0);
            for (var i = 0, l = obj.length; i < l; i++) {
                var opt = document.createElement ("option");
                opt.value = obj[i]["index"];
                opt.innerText = obj[i]["name"];
                types.appendChild (opt);
            }
        },
        complete: function(){

        }
    });
}

$("#ticket_select_contacts_cancel_btn").click(function(){
    location.hash="anchor_flow_preserve_select_trip";
})

$("#ticket_select_contacts_confirm_btn").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var contactsId = "";
    var radios = $(".booking_contacts_select");
    var selectContactsStatus = false;
    if(radios[radios.length - 1].checked){
        selectContactsStatus = true;
        preserveCreateNewContacts();
    }else{
        for (var j = 0; j < radios.length - 1; j++) {
            if (radios[j].checked) {
                contactsId = $(".booking_contacts_contactsId").eq(j).text();
                selectContactsStatus = true;
                var contactsName = $(".booking_contacts_name").eq(j).text();
                var documentType = $(".booking_contacts_documentType").eq(j).text();
                var documentNumber = $(".booking_contacts_documentNumber").eq(j).text();
                $("#ticket_confirm_contactsId").text(contactsId);
                $("#ticket_confirm_contactsName").text(contactsName);
                $("#ticket_confirm_documentType").text(documentType);
                $("#ticket_confirm_documentNumber").text(documentNumber);
                //show th food information
                if($('#need-food-or-not').is(":checked")){
                    $('#ticket_confirm_food_type_div').css("display","block");
                    $('#ticket_confirm_food_name_div').css("display","block");
                    $('#ticket_confirm_food_price_div').css("display","block");
                    var type = $('#preserve_food_type').find("option:selected").text();
                    $('#ticket_confirm_food_type').text(type);
                    if($('#preserve_food_type').find("option:selected").val() == 1){
                        var fp =  $('#train-food-type-list').find("option:selected").text().split(":");
                        $('#ticket_confirm_food_name').text(fp[0]);
                        $('#ticket_confirm_food_price').text(fp[1]);
                        $('#ticket_confirm_food_station_div').css("display","none");
                        $('#ticket_confirm_food_store_div').css("display","none");
                    } else {
                        var fp2 =  $('#food-store-food-list').find("option:selected").text().split(":");
                        $('#ticket_confirm_food_name').text(fp2[0]);
                        $('#ticket_confirm_food_price').text(fp2[1]);
                        $('#ticket_confirm_food_station_div').css("display","block");
                        $('#ticket_confirm_food_store_div').css("display","block");
                        $('#ticket_confirm_food_station').text($('#food-station-list').find("option:selected").text());
                        $('#ticket_confirm_food_store').text($('#food-stores-list').find("option:selected").text());
                    }

                } else {
                    $('#ticket_confirm_food_type_div').css("display","none");
                    $('#ticket_confirm_food_station_div').css("display","none");
                    $('#ticket_confirm_food_store_div').css("display","none");
                    $('#ticket_confirm_food_name_div').css("display","none");
                    $('#ticket_confirm_food_price_div').css("display","none");
                }

                //Show the consign information
                if($('#need-consign-or-not').is(":checked")){
                    $('.ticket_confirm_consign_div').css("display", "block");
                    $('#ticket_confirm_consignee_name').text($(" #name_of_consignee ").val());
                    $('#ticket_confirm_consignee_phone').text($(" #phone_of_consignee ").val());
                    $('#ticket_confirm_consign_weight').text($(" #weight_of_consign ").val());
                }
                else{
                    $('.ticket_confirm_consign_div').css("display", "none");
                }
                break;
            }
        }
    }
    if(selectContactsStatus == false){
        alert("Please select contacts.");
        return;
    }else{
        location.hash="anchor_flow_preserve_confirm";
    }
})

function preserveCreateNewContacts(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#ticket_select_contacts_confirm_btn").attr("disabled",true);
    var addContactsInfo = new Object();
    addContactsInfo.name = $("#booking_new_contacts_name").val();
    addContactsInfo.documentType = $("#booking_new_contacts_documentType").val();
    addContactsInfo.documentNumber = $("#booking_new_contacts_documentNum").val();
    addContactsInfo.phoneNumber = $("#booking_new_contacts_phoneNum").val();
    var data = JSON.stringify(addContactsInfo);
    $.ajax({
        type: "post",
        url: "/contacts/create",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#ticket_confirm_contactsId").text(result["contacts"]["id"]);
            $("#ticket_confirm_contactsName").text(result["contacts"]["name"]);
            $("#ticket_confirm_documentType").text(convertNumberToDocumentType(result["contacts"]["documentType"]));
            $("#ticket_confirm_documentNumber").text(result["contacts"]["documentNumber"]);
            refresh_booking_contacts();
        },
        complete: function(){
            $("#ticket_select_contacts_confirm_btn").attr("disabled",false);
        }
    });
}

function convertNumberToDocumentType(code) {
    var str = "";
    if (code == 0) {
        str = "null";
    } else if (code == 1) {
        str = "ID Card";
    } else if (code == 2) {
        str = "Passport";
    } else {
        str = "other";
    }
    return str
}

/**
 * Flow Preserve - Step 4 - Check Your Order Detail Information
 */

$("#ticket_confirm_cancel_btn").click(function () {
    location.hash="anchor_flow_preserve_select_contacts";
})

$("#ticket_confirm_confirm_btn").click(function () {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }

    $("#ticket_confirm_confirm_btn").attr("disabled",true);
    var orderTicketInfo = new Object();
    orderTicketInfo.contactsId = $("#ticket_confirm_contactsId").text();
    orderTicketInfo.tripId = $("#ticket_confirm_tripId").text();
    orderTicketInfo.seatType = $("#ticket_confirm_seatType").text();
    orderTicketInfo.date = $("#ticket_confirm_travel_date").text();
    orderTicketInfo.from = $("#ticket_confirm_from").text();
    orderTicketInfo.to = $("#ticket_confirm_to").text();
    orderTicketInfo.assurance = $("#assurance_type").val();

    //add the food information
    if(null != $('#ticket_confirm_food_type').text() && "" != $('#ticket_confirm_food_type').text()){
        if($('#ticket_confirm_food_type').text() == "Train Food"){
            orderTicketInfo.foodType = 1;
            orderTicketInfo.foodName = $('#ticket_confirm_food_name').text();
            orderTicketInfo.foodPrice = parseFloat($('#ticket_confirm_food_price').text().substr(1));
            orderTicketInfo.stationName = "";
            orderTicketInfo.storeName = "";
        } else if ($('#ticket_confirm_food_type').text()== "Station Food Stores"){
            orderTicketInfo.foodType = 2;
            orderTicketInfo.stationName = $('#ticket_confirm_food_station').text();
            orderTicketInfo.storeName = $('#ticket_confirm_food_store').text();
            orderTicketInfo.foodName = $('#ticket_confirm_food_name').text();
            orderTicketInfo.foodPrice = parseFloat($('#ticket_confirm_food_price').text().substr(1));
        } else {
            orderTicketInfo.foodType = 0;
        }
    } else {
        orderTicketInfo.foodType = 0;
    }

    //Add the consign information
    if($('#need-consign-or-not').is(":checked")){
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        orderTicketInfo.handleDate = currentdate;
        orderTicketInfo.consigneeName = $("#name_of_consignee ").val();
        orderTicketInfo.consigneePhone = $("#phone_of_consignee ").val();
        orderTicketInfo.consigneeWeight = parseFloat($("#weight_of_consign ").val());
        orderTicketInfo.isWithin = false;
    }

    var orderTicketsData = JSON.stringify(orderTicketInfo);
    console.log("orderTicketsData:");
    console.log(orderTicketsData);

    var tripType = orderTicketInfo.tripId.charAt(0);
    if(tripType == 'G' || tripType == 'D'){
        path = "/preserve";
    }else{
        path = "/preserveOther";
    }
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data: orderTicketsData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            alert(result["message"]);
            if(result['status'] == true){
                //$("#preserve_pay_panel").css('display','block');
                $("#preserve_pay_orderId").val(result["order"]["id"]);
                $("#preserve_pay_price").val(result["order"]["price"]);
                $("#preserve_pay_userId").val(result["order"]["accountId"]);
                $("#preserve_pay_tripId").val(result["order"]["trainNumber"]);
                location.hash="anchor_flow_preserve_pay";
            }
        },
        complete: function(){
            $("#ticket_confirm_confirm_btn").attr("disabled",false);
        }
    })
})

/**
 * Flow Preserve - Step 5 - Pay For Your Ticket
 */

$("#preserve_pay_later_button").click(function(){
    //$("#preserve_pay_panel").css('display','none');
    location.hash="anchor_flow_preserve_pay";
})

$("#preserve_pay_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#preserve_pay_button").attr("disabled",true);
    var info = new Object();
    info.orderId = $("#preserve_pay_orderId").val();
    info.tripId = $("#preserve_pay_tripId").val();
    var data = JSON.stringify(info);
    $.ajax({
        type: "post",
        url: "/inside_payment/pay",
        contentType: "application/json",
        dataType: "text",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if(result == "true"){
                $("#preserve_collect_order_id").val(info.orderId);
                //alert("Success");
                location.hash="anchor_flow_preserve_collect";
            }else{
                alert("Pay Fail. Reason Not Clear.Please check the order status before you try.");
                //alert("Some thing error");
            }
        },
        complete: function(){
            $("#preserve_pay_button").attr("disabled",false);
        }
    });
    //$("#preserve_pay_panel").css('display','none');
})

/**
 * Flow Preserve - Step 6 - Collect Your Ticket
 */

$("#preserve_collect_button").click(function() {
    $("#preserve_collect_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#preserve_collect_order_id").val();
    var data = JSON.stringify(executeInfo);
    $.ajax({
        type: "post",
        url: "/execute/collected",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#preserve_execute_order_id").val(executeInfo.orderId);
                $("#preserve_collect_order_status").html(obj["message"]);
                location.hash="anchor_flow_preserve_execute";
            }else{
                $("#preserve_collect_order_status").html(obj["message"]);
            }
        },
        complete: function(){
            $("#preserve_collect_button").attr("disabled",false);
        }
    });
});

/**
 * Flow Preserve - Step 7 - Enter Station
 */

$("#preserve_order_button").click(function() {
    $("#preserve_order_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#preserve_execute_order_id").val();
    var data = JSON.stringify(executeInfo);
    $.ajax({
        type: "post",
        url: "/execute/execute",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#preserve_order_status").html(obj["message"]);
            }else{
                $("#preserve_order_status").html(obj["message"]);
            }
        },
        complete: function(){
            $("#preserve_order_button").attr("disabled",false);
        }
    });
});






