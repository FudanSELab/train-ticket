/**Before ***/
function setTodayDateAdvancedSearch(){
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
    document.getElementById("flow_advance_reserve_booking_date").setAttribute("min", today);
}

/**
 *  Flow Preserve - Step 1 - User Login
 **/
$("#flow_advance_reserve_login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.email = $("#flow_advance_reserve_login_email").val();
    if(loginInfo.email == null || loginInfo.email == ""){
        alert("Email Can Not Be Empty.");
        return;
    }
    if(checkEmailFormat(loginInfo.email) == false){
        alert("Email Format Wrong.");
        return;
    }
    loginInfo.password = $("#flow_advance_reserve_login_password").val();
    if(loginInfo.password == null || loginInfo.password == ""){
        alert("Password Can Not Be Empty.");
        return;
    }
    loginInfo.verificationCode = $("#flow_advance_reserve_login_verification_code").val();
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
                location.hash="anchor_flow_advance_reserve_select_trip";
            }else{
                alert(obj["message"]);
            }
        }
    });
});

/**
 *  Flow Preserve - Step 2 - Search For Ticket
 **/

$("#flow_advance_reserve_booking_button").click(function() {
    var advanceSearchInfo = new Object();
    advanceSearchInfo.startPlace = $("#flow_advance_reserve_startingPlace").val();
    advanceSearchInfo.endPlace = $("#flow_advance_reserve_terminalPlace").val();
    advanceSearchInfo.departureTime = $("#flow_advance_reserve_booking_date").val();
    if(advanceSearchInfo.departureTime  == null || checkDateFormat(advanceSearchInfo.departureTime ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var advanceSearchData = JSON.stringify(advanceSearchInfo);
    $("#flow_advance_reserve_booking_list_table").find("tbody").html("");
    var selectType = $("#flow_advance_reserve_select_searchType").val();

    if(selectType == 0){
        advanceSearchForMinStopInfo(advanceSearchData,"/api/v1/travelplanservice/travelPlan/minStation");
    }else if(selectType == 1){
        advanceSearchForCheapestInfo(advanceSearchData,"/api/v1/travelplanservice/travelPlan/cheapest");
    }else if(selectType == 2){
        advanceSearchForQuickestInfo(advanceSearchData,"/api/v1/travelplanservice/travelPlan/quickest");
    }else{
        alert("Select Search Type Wrong");
    }
})

function advanceSearchForCheapestInfo(data,path) {
    $("#flow_advance_reserve_booking_button").attr("disabled",true);
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
                var obj = result["travelAdvanceResultUnits"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#flow_advance_reserve_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='booking_tripId'>" + obj[i]["tripId"] + "</td>" +
                        "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] + "</td>" +
                        "<td class='booking_from'>" + obj[i]["startStation"] + "</td>" +
                        "<td class='booking_to'>" + obj[i]["endStation"] + "</td>" +
                        "<td>" +  obj[i]["stopStations"]  + "</td>" +
                        "<td>" + flow_advance_convertNumberToTimeString(obj[i]["startTime"]) + "</td>" +
                        "<td>" + flow_advance_convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["numberOfRestTicketSecondClass"] + "</td>" +
                        "<td>" + obj[i]["numberOfRestTicketFirstClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control booking_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForFirstClassSeat"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForSecondClassSeat"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='booking_seat_price_confort noshow_component'>" + obj[i]["priceForFirstClassSeat"] + "</td>"+
                        "<td class='booking_seat_price_economy noshow_component'>" + obj[i]["priceForSecondClassSeat"] + "</td>"+
                        "<td>" + "<button class='btn btn-primary advance_ticket_booking_button'>" + "Booking" + "</button>" + "</td>" +
                        "</tr>"
                    );
                }
                flow_advance_addListenerToBookingTable();
            }
        },
        complete: function () {
            $("#flow_advance_reserve_booking_button").attr("disabled",false);
        }
    });
}

function advanceSearchForQuickestInfo(data,path) {
    $("#flow_advance_reserve_booking_button").attr("disabled",true);
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
                var obj = result["travelAdvanceResultUnits"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#flow_advance_reserve_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='booking_tripId'>" + obj[i]["tripId"] + "</td>" +
                        "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] + "</td>" +
                        "<td class='booking_from'>" + obj[i]["startStation"] + "</td>" +
                        "<td class='booking_to'>" + obj[i]["endStation"] + "</td>" +
                        "<td>" +  obj[i]["stopStations"]  + "</td>" +
                        "<td>" + flow_advance_convertNumberToTimeString(obj[i]["startTime"]) + "</td>" +
                        "<td>" + flow_advance_convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["numberOfRestTicketSecondClass"] + "</td>" +
                        "<td>" + obj[i]["numberOfRestTicketFirstClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control booking_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForFirstClassSeat"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForSecondClassSeat"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='booking_seat_price_confort noshow_component'>" + obj[i]["priceForFirstClassSeat"] + "</td>"+
                        "<td class='booking_seat_price_economy noshow_component'>" + obj[i]["priceForSecondClassSeat"] + "</td>"+
                        "<td>" + "<button class='btn btn-primary advance_ticket_booking_button'>" + "Booking" + "</button>" + "</td>" +
                        "</tr>"
                    );
                }
                flow_advance_addListenerToBookingTable();
            }
        },
        complete: function () {
            $("#flow_advance_reserve_booking_button").attr("disabled",false);
        }
    });
}

function advanceSearchForMinStopInfo(data,path) {
    $("#flow_advance_reserve_booking_button").attr("disabled",true);
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data: data,
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == 1) {
                var obj = result["travelAdvanceResultUnits"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#flow_advance_reserve_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='booking_tripId'>" + obj[i]["tripId"] + "</td>" +
                        "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] + "</td>" +
                        "<td class='booking_from'>" + obj[i]["startStation"] + "</td>" +
                        "<td class='booking_to'>" + obj[i]["endStation"] + "</td>" +
                        "<td>" +  obj[i]["stopStations"]  + "</td>" +
                        "<td>" + flow_advance_convertNumberToTimeString(obj[i]["startTime"]) + "</td>" +
                        "<td>" + flow_advance_convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["numberOfRestTicketSecondClass"] + "</td>" +
                        "<td>" + obj[i]["numberOfRestTicketFirstClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control booking_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForFirstClassSeat"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForSecondClassSeat"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='booking_seat_price_confort noshow_component'>" + obj[i]["priceForFirstClassSeat"] + "</td>"+
                        "<td class='booking_seat_price_economy noshow_component'>" + obj[i]["priceForSecondClassSeat"] + "</td>"+
                        "<td>" + "<button class='btn btn-primary advance_ticket_booking_button'>" + "Booking" + "</button>" + "</td>" +
                        "</tr>"
                    );
                }
                flow_advance_addListenerToBookingTable();
            }
        },
        complete: function () {
            $("#flow_advance_reserve_booking_button").attr("disabled",false);
        }
    });
}

function flow_advance_addListenerToBookingTable(){
    var ticketBookingButtonSet = $(".advance_ticket_booking_button");
    for(var i = 0;i < ticketBookingButtonSet.length;i++){
        ticketBookingButtonSet[i].onclick = function(){
            var tripId = $(this).parents("tr").find(".booking_tripId").text();
            var from = $(this).parents("tr").find(".booking_from").text();
            var to = $(this).parents("tr").find(".booking_to").text();
            var seatType = $(this).parents("tr").find(".booking_seat_class").val();
            var date = $("#flow_advance_reserve_booking_date").val();
            $("#flow_advance_reserve_ticket_confirm_from").text(from);
            $("#flow_advance_reserve_ticket_confirm_to").text(to);
            $("#flow_advance_reserve_ticket_confirm_tripId").text(tripId);
            if(seatType == 2){
                $("#flow_advance_reserve_ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_confort").text());
            }else if(seatType == 3){
                $("#flow_advance_reserve_ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_economy").text());
            }
            $("#flow_advance_reserve_ticket_confirm_travel_date").text(date);
            $("#flow_advance_reserve_ticket_confirm_seatType").text(seatType);
            if(seatType == 2){
                $("#flow_advance_reserve_ticket_confirm_seatType_String").text("confort seat");
            }else if(seatType == 3){
                $("#flow_advance_reserve_ticket_confirm_seatType_String").text("economy seat");
            }
            flow_advance_refresh_booking_contacts();
            flow_advance_initFoodSelect(tripId);
            location.hash="anchor_flow_preserve_select_contacts";
        }
    }
}

/**
 *  Flow Preserve - Step 3 - Search For Contact and Food
 **/

$("#flow_advance_reserve_booking_contacts_button").click(function(){
    flow_advance_refresh_booking_contacts();
})

function flow_advance_refresh_booking_contacts() {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#flow_advance_reserve_booking_contacts_button").attr("disabled",true);
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
            $("#flow_advance_reserve_contacts_booking_list_table").find("tbody").html("");
            for (var i = 0, l = obj.length; i < l; i++) {
                $("#flow_advance_reserve_contacts_booking_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='advance_search_booking_contacts_contactsId' style='display:none'>" + obj[i]["id"] + "</td>" +
                    "<td class='advance_search_booking_contacts_name'>" + obj[i]["name"] + "</td>" +
                    "<td class='advance_search_booking_contacts_documentType'>" + convertNumberToDocumentType(obj[i]["documentType"]) + "</td>" +
                    "<td class='advance_search_booking_contacts_documentNumber'>" + obj[i]["documentNumber"] + "</td>" +
                    "<td class='advance_search_booking_contacts_phoneNumber'>" + obj[i]["phoneNumber"] + "</td>" +
                    "<td>" + "<label><input class='advance_search_booking_contacts_select' type='radio' />" + "Select" + "</label>" + "</td>" +
                    "</tr>"
                );
            }
            $("#flow_advance_reserve_contacts_booking_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + obj.length + "</td>" +
                "<td class='booking_contacts_name'>" + "<input id='advance_search__booking_new_contacts_name'>" + "</td>" +
                "<td>" +
                "<select id='flow_advance_booking_new_contacts_documentType' class='advance_search_booking_contacts_documentType all form-control'>" +
                "<option value='1' selected = 'selected'>ID Card</option>" +
                "<option value='2'>Passport</option>" +
                "<option value='3'>Other</option>" +
                "</select>" +
                "</td>" +
                "<td class='advance_search_booking_contacts_documentNumber'>" + "<input id='flow_advance_booking_new_contacts_documentNum'>" + "</td>" +
                "<td class='advance_search_booking_contacts_phoneNumber'>" + "<input id='flow_advance_booking_new_contacts_phoneNum'>" + "</td>" +
                "<td>" + "<label><input id='flow_advance_booking_new_contacts_select' class='flow_advance_booking_contacts_select' name='booking_contacts' type='radio' />" + "Select" + "</label>" + "</td>" +
                "</tr>"
            );
        },
        complete: function(){
            $("#flow_advance_reserve_booking_contacts_button").attr("disabled",false);
        }
    });
}

$(function () {
    flow_advance_getAssuranceType();
});

//获取保险的类型
function flow_advance_getAssuranceType(){
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
            var types = document.getElementById ("flow_advance_reserve_assurance_type");
            var opt0 = document.createElement("option");
            opt0.value = 0;
            opt0.innerText = "No Assurance";
            types.appendChild(opt0);
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

$("#flow_advance_reserve_ticket_select_contacts_cancel_btn").click(function(){
    location.hash="anchor_flow_advance_reserve_select_trip";
})

$("#flow_advance_reserve_ticket_select_contacts_confirm_btn").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var contactsId = "";
    var radios = $(".advance_search_booking_contacts_select");
    var selectContactsStatus = false;
    if(radios[radios.length - 1].checked){
        selectContactsStatus = true;
        flow_advance_preserveCreateNewContacts();
    }else{
        for (var j = 0; j < radios.length - 1; j++) {
            if (radios[j].checked) {
                contactsId = $(".advance_search_booking_contacts_contactsId").eq(j).text();
                selectContactsStatus = true;
                var contactsName = $(".advance_search_booking_contacts_name").eq(j).text();
                var documentType = $(".advance_search_booking_contacts_documentType").eq(j).text();
                var documentNumber = $(".advance_search_booking_contacts_documentNumber").eq(j).text();
                $("#flow_advance_reserve_ticket_confirm_contactsId").text(contactsId);
                $("#flow_advance_reserve_ticket_confirm_contactsName").text(contactsName);
                $("#flow_advance_reserve_ticket_confirm_documentType").text(documentType);
                $("#flow_advance_reserve_ticket_confirm_documentNumber").text(documentNumber);

                break;
            }
        }

    }
    //show th food information
    if($('#flow_advance_reserve_need_food_or_not').is(":checked")){
        $('#flow_advance_reserve_ticket_confirm_food_type_div').css("display","block");
        $('#flow_advance_reserve_ticket_confirm_food_name_div').css("display","block");
        $('#flow_advance_reserve_ticket_confirm_food_price_div').css("display","block");
        var type = $('#flow_advance_reserve_preserve_food_type').find("option:selected").text();
        $('#flow_advance_reserve_ticket_confirm_food_type').text(type);
        if($('#flow_advance_reserve_preserve_food_type').find("option:selected").val() == 1){
            var fp =  $('#flow_advance_reserve_train_food_type_list').find("option:selected").text().split(":");
            $('#flow_advance_reserve_ticket_confirm_food_name').text(fp[0]);
            $('#flow_advance_reserve_ticket_confirm_food_price').text(fp[1]);
            $('#flow_advance_reserve_ticket_confirm_food_station_div').css("display","none");
            $('#flow_advance_reserve_ticket_confirm_food_store_div').css("display","none");
        } else {
            var fp2 =  $('#flow_advance_reserve_food_store_food_list').find("option:selected").text().split(":");
            $('#flow_advance_reserve_ticket_confirm_food_name').text(fp2[0]);
            $('#flow_advance_reserve_ticket_confirm_food_price').text(fp2[1]);
            $('#flow_advance_reserve_ticket_confirm_food_station_div').css("display","block");
            $('#flow_advance_reserve_ticket_confirm_food_store_div').css("display","block");
            $('#flow_advance_reserve_ticket_confirm_food_station').text($('#food-station-list').find("option:selected").text());
            $('#flow_advance_reserve_ticket_confirm_food_store').text($('#food-stores-list').find("option:selected").text());
        }

    } else {
        $('#flow_advance_reserve_ticket_confirm_food_type_div').css("display","none");
        $('#flow_advance_reserve_ticket_confirm_food_station_div').css("display","none");
        $('#flow_advance_reserve_ticket_confirm_food_store_div').css("display","none");
        $('#flow_advance_reserve_ticket_confirm_food_name_div').css("display","none");
        $('#flow_advance_reserve_ticket_confirm_food_price_div').css("display","none");
    }

    //Show the consign information
    if($('#flow_advance_reserve_need_consign_or_not').is(":checked")){
        $('.flow_advance_reserve_ticket_confirm_consign_div').css("display", "block");
        $('#flow_advance_reserve_ticket_confirm_consignee_name').text($(" #flow_advance_reserve_name_of_consignee ").val());
        $('#flow_advance_reserve_ticket_confirm_consignee_phone').text($(" #flow_advance_reserve_phone_of_consignee").val());
        $('#flow_advance_reserve_ticket_confirm_consign_weight').text($(" #flow_advance_reserve_weight_of_consign ").val());
    }
    else{
        $('.flow_advance_reserve_ticket_confirm_consign_div').css("display", "none");
    }
    if(selectContactsStatus == false){
        alert("Please select contacts.");
        return;
    }else{
        location.hash="anchor_flow_preserve_confirm";
    }
})

function flow_advance_preserveCreateNewContacts(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#flow_advance_reserve_ticket_select_contacts_confirm_btn").attr("disabled",true);
    var addContactsInfo = new Object();
    addContactsInfo.name = $("#flow_advance_booking_new_contacts_name").val();
    addContactsInfo.documentType = $("#flow_advance_booking_new_contacts_documentType").val();
    addContactsInfo.documentNumber = $("#flow_advance_booking_new_contacts_documentNum").val();
    addContactsInfo.phoneNumber = $("#flow_advance_booking_new_contacts_phoneNum").val();
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
            $("#flow_advance_reserve_ticket_confirm_contactsId").text(result["contacts"]["id"]);
            $("#flow_advance_reserve_ticket_confirm_contactsName").text(result["contacts"]["name"]);
            $("#flow_advance_reserve_ticket_confirm_documentType").text(convertNumberToDocumentType(result["contacts"]["documentType"]));
            $("#flow_advance_reserve_ticket_confirm_documentNumber").text(result["contacts"]["documentNumber"]);
            refresh_booking_contacts();
        },
        complete: function(){
            $("#flow_advance_reserve_ticket_confirm_confirm_btn").attr("disabled",false);
        }
    });
}

function flow_advance_needFoodOrNot(){
    if($('#flow_advance_reserve_need_food_or_not').is(':checked')){
        $('#flow_advance_reserve_food_preserve_select').css("display", "block");
    } else {
        $('#flow_advance_reserve_food_preserve_select').css("display", "none");
        $('#flow_advance_reserve_food_store_selected').css("display", "none");
        $('#flow_advance_reserve_train_food_selected').css("display", "none");
        $('#flow_advance_reserve_preserve_food_type').val(0);
    }
}

function flow_advance_changeFoodType(){
    var type = $('#flow_advance_reserve_preserve_food_type').find("option:selected").val();
    if(type == 1){
        $('#flow_advance_reserve_train_food_selected').css("display", "block");
        $('#flow_advance_reserve_food_store_selected').css("display", "none");
        $('#flow_advance_reserve_food_station_list').val(0);
    } else if(type == 2){
        $('#flow_advance_reserve_train_food_selected').css("display", "none");
        $('#flow_advance_reserve_food_store_selected').css("display", "block");
    } else {
        $('#flow_advance_reserve_train_food_selected').css("display", "none");
        $('#flow_advance_reserve_food_store_selected').css("display", "none");
    }
}

var flow_advance_preserveFoodStoreListMap = null;

//在点击预定时候调用
function flow_advance_initFoodSelect(tripId){
    var data = new Object();
    data.date = $('#flow_advance_reserve_booking_date').val() || "";
    data.startStation = $('#flow_advance_reserve_startingPlace').val() || "";
    data.endStation = $('#flow_advance_reserve_terminalPlace').val() || "";
    data.tripId = tripId;
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
                    $('#flow_advance_reserve_train_food_option').disabled(true);
                } else {
                    var trainFoodList = result.trainFoodList[0]['foodList'];
                    console.log("trainFoodList:" );
                    console.log(trainFoodList[0]);

                    $("#flow_advance_reserve_train_food_type_list").html("");
                    $("#flow_advance_reserve_food_station_list").html("");
                    $("#flow_advance_reserve_food_stores_list").html("");
                    $("#flow_advance_reserve_food_store_food_list").html("");

                    var trainFoodSelect = document.getElementById ("flow_advance_reserve_train_food_type_list");
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
                flow_advance_preserveFoodStoreListMap = result.foodStoreListMap;
                console.log(" flow_advance_preserveFoodStoreListMap:");
                console.log(flow_advance_preserveFoodStoreListMap);
                var foodStationSelect = document.getElementById ("flow_advance_reserve_food_station_list");
                var opt3 = document.createElement ("option");
                opt3.value = 0;
                opt3.innerText = "-- --";
                foodStationSelect.appendChild(opt1);
                var fsindex = 1;
                for(var key in flow_advance_preserveFoodStoreListMap){
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

function flow_advance_preserveChangeFoodStation(){
    var station = $('#flow_advance_reserve_food_station_list').find("option:selected").text();
    var  foodStoreList = flow_advance_preserveFoodStoreListMap[station];

    var foodStoreSelect = document.getElementById ("flow_advance_reserve_food_stores_list");
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

function flow_advance_preserveChangeFoodStore(){
    var station = $('#flow_advance_reserve_food_station_list').find("option:selected").text();
    var storeIndex = parseInt($('#flow_advance_reserve_food_stores_list').find("option:selected").val());
    console.log("storeIndex=" + storeIndex);
    var foodList = flow_advance_preserveFoodStoreListMap[station][storeIndex-1]['foodList'];
    console.log("preserveChangeFoodStore: foodList: ");
    console.log(foodList);

    var foodStoreFoodSelect = document.getElementById ("flow_advance_reserve_food_store_food_list");
    foodStoreFoodSelect.innerHTML = "";
    var opt5 = document.createElement("option");
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

function flow_advance_needConsignOrNot(){
    if($('#flow_advance_reserve_need_consign_or_not').is(':checked')){
        $('.flow_advance_reserve_consign_input').css("display", "block");
    } else {
        $('.flow_advance_reserve_consign_input').css("display", "none");
    }
}

function flow_advance_convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}

/**
 *  Flow Preserve - Step 4 - Confirm Your Order
 **/
$("#flow_advance_reserve_ticket_confirm_cancel_btn").click(function () {
    location.hash="anchor_flow_preserve_select_contacts";
})

$("#flow_advance_reserve_ticket_confirm_confirm_btn").click(function () {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }

    $("#flow_advance_reserve_ticket_confirm_confirm_btn").attr("disabled",true);
    var orderTicketInfo = new Object();
    orderTicketInfo.contactsId = $("#flow_advance_reserve_ticket_confirm_contactsId").text();
    orderTicketInfo.tripId = $("#flow_advance_reserve_ticket_confirm_tripId").text();
    orderTicketInfo.seatType = $("#flow_advance_reserve_ticket_confirm_seatType").text();
    orderTicketInfo.date = $("#flow_advance_reserve_ticket_confirm_travel_date").text();
    orderTicketInfo.from = $("#flow_advance_reserve_ticket_confirm_from").text();
    orderTicketInfo.to = $("#flow_advance_reserve_ticket_confirm_to").text();
    orderTicketInfo.assurance = $("#flow_advance_reserve_assurance_type").val();

    //add the food information
    if(null != $('#flow_advance_reserve_ticket_confirm_food_type').text() && "" != $('#flow_advance_reserve_ticket_confirm_food_type').text()){
        if($('#flow_advance_reserve_ticket_confirm_food_type').text() == "Train Food"){
            orderTicketInfo.foodType = 1;
            orderTicketInfo.foodName = $('#flow_advance_reserve_ticket_confirm_food_name').text();
            orderTicketInfo.foodPrice = parseFloat($('#flow_advance_reserve_ticket_confirm_food_price').text().substr(1));
            orderTicketInfo.stationName = "";
            orderTicketInfo.storeName = "";
        } else if ($('#flow_advance_reserve_ticket_confirm_food_type').text()== "Station Food Stores"){
            orderTicketInfo.foodType = 2;
            orderTicketInfo.stationName = $('#flow_advance_reserve_ticket_confirm_food_station').text();
            orderTicketInfo.storeName = $('#flow_advance_reserve_ticket_confirm_food_store').text();
            orderTicketInfo.foodName = $('#flow_advance_reserve_ticket_confirm_food_name').text();
            orderTicketInfo.foodPrice = parseFloat($('#low_advance_reserve_ticket_confirm_food_price').text().substr(1));
        } else {
            orderTicketInfo.foodType = 0;
        }
    } else {
        orderTicketInfo.foodType = 0;
    }

    //Add the consign information
    if($('#flow_advance_reserve_need_consign_or_not').is(":checked")){
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
        orderTicketInfo.consigneeName = $("#flow_advance_reserve_name_of_consignee").val();
        orderTicketInfo.consigneePhone = $("#flow_advance_reserve_phone_of_consignee").val();
        orderTicketInfo.consigneeWeight = parseFloat($("#flow_advance_reserve_weight_of_consign").val());
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
                $("#flow_advance_reserve_pay_orderId").val(result["order"]["id"]);
                $("#flow_advance_reserve_pay_price").val(result["order"]["price"]);
                //$("#preserve_pay_userId").val(result["order"]["accountId"]);
                $("#flow_advance_reserve_pay_tripId").val(result["order"]["trainNumber"]);
                location.hash="anchor_flow_advance_reserve_pay";
            }
        },
        complete: function(){
            $("#flow_advance_reserve_ticket_confirm_confirm_btn").attr("disabled",false);
        }
    })
})

/**
 * Flow Preserve - Step 5 - Pay For Your Ticket
 */

$("#flow_advance_reserve_pay_later_button").click(function(){
    location.hash="anchor_flow_advance_reserve_pay";
})

$("#flow_advance_reserve_pay_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#flow_advance_reserve_pay_button").attr("disabled",true);
    var info = new Object();
    info.orderId = $("#flow_advance_reserve_pay_orderId").val();
    info.tripId = $("#flow_advance_reserve_pay_tripId").val();
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
                $("#flow_advanced_reserve_collect_order_id").val(info.orderId);
                location.hash="anchor_flow_advance_reserve_collect";
            }else{
                alert("Pay Fail. Reason Not Clear.Please check the order status before you try.");
            }
        },
        complete: function(){
            $("#flow_advance_reserve_pay_button").attr("disabled",false);
        }
    });
})



/**
 * Flow Preserve - Step 6 - Collect Your Ticket
 */

$("#flow_advanced_reserve_collect_button").click(function() {
    $("#flow_advanced_reserve_collect_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#flow_advanced_reserve_collect_order_id").val();
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
                $("#advanced_reserve_execute_order_id").val(executeInfo.orderId);
                location.hash="anchor_flow_advance_reserve_execute";
            }else{
                alert(obj["message"]);
            }
        },
        complete: function(){
            $("#flow_advanced_reserve_collect_button").attr("disabled",false);
        }
    });
});

/**
 * Flow Preserve - Step 7 - Enter Station
 */

$("#flow_advanced_reserve_execute_order_button").click(function() {
    $("#flow_advanced_reserve_execute_order_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#advanced_reserve_execute_order_id").val();
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
                alert(obj["message"]);
            }else{
                alert(obj["message"]);
            }
        },
        complete: function(){
            $("#flow_advanced_reserve_execute_order_button").attr("disabled",false);
        }
    });
});
