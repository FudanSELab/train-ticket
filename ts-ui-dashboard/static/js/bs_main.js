/**
 * Created by ldw on 20178/7/17.
 */

function onLoadBody(){
    setTodayDatePreserve();
}

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
    document.getElementById("travel_booking_date").value=today;
    document.getElementById("travel_booking_date").setAttribute("min", today);

}

var login = function () {
    sessionStorage.clear();
    location.href= "client_login.html";
}


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


function addListenerToBookingTable(){

    var ticketBookingButtonSet = $(".ticket_booking_button");
    for(var i = 0;i < ticketBookingButtonSet.length;i++){
        ticketBookingButtonSet[i].onclick = function(){

            var username = sessionStorage.getItem("admin_name");
            if (username == null) {
                alert("Please login first!");
                location.href = "client_login.html";
            }
            else {
                document.getElementById("admin_name").innerHTML = username;
            }

            // var tripId = $(this).parents("tr").find(".booking_tripId").text();
            // var from = $(this).parents("tr").find(".booking_from").text();
            // var to = $(this).parents("tr").find(".booking_to").text();
            // var seatType = $(this).parents("tr").find(".booking_seat_class").val();
            // var date = $("#travel_booking_date").val();
            // $("#ticket_confirm_from").text(from);
            // $("#ticket_confirm_to").text(to);
            // $("#ticket_confirm_tripId").text(tripId);
            // if(seatType == 2){
            //     $("#ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_confort").text());
            // }else if(seatType == 3){
            //     $("#ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_economy").text());
            // }
            // $("#ticket_confirm_travel_date").text(date);
            // $("#ticket_confirm_seatType").text(seatType);
            // if(seatType == 2){
            //     $("#ticket_confirm_seatType_String").text("confort seat");
            // }else if(seatType == 3){
            //     $("#ticket_confirm_seatType_String").text("economy seat");
            // }
            // refresh_booking_contacts();
            // initFoodSelect(tripId);
            location.hash="anchor_flow_preserve_select_contacts";
        }
    }
}


/**
 *  Flow Preserve - Step 1 - User Login
 **/
$("#flow_preserve_login_button").click(function() {
    alert("dffd")
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

function checkDateFormat(date){
    var dateFormat = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
    if(!dateFormat.test(date)){
        return false;
    }else{
        return true;
    }
}

function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}
function checkEmailFormat(email){
    var emailFormat = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!emailFormat.test(email)){
        return false;
    }else{
        return true;
    }
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