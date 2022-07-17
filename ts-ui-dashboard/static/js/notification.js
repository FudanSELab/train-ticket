/*****************************************************************************/
/********************Function For Notification Service************************/
/********************Used For Notification Service Single Microservice Test***/

$("#notification_send_email_button").click(function(){
    var notificationInfo = new Object();
    notificationInfo.email = $("#notification_email").val();
    if(notificationInfo.email == null || notificationInfo.email == ""){
        alert("Please input the email.");
        return;
    }
    notificationInfo.orderNumber = $("#notification_orderNumber").val();
    if(notificationInfo.orderNumber == null || notificationInfo.orderNumber == ""){
        alert("Please input the order ID.");
        return;
    }
    notificationInfo.username = $("#notification_username").val();
    if(notificationInfo.username == null || notificationInfo.username == ""){
        alert("Please input the username.");
        return;
    }
    notificationInfo.startPlace = $("#notification_startingPlace").val()
    if(notificationInfo.startPlace == null || notificationInfo.startPlace == ""){
        alert("Please input the starting place.");
        return;
    }
    notificationInfo.endPlace = $("#notification_endPlace").val();
    if( notificationInfo.endPlace == null ||  notificationInfo.endPlace == ""){
        alert("Please input the end place.");
        return;
    }
    notificationInfo.startTime = $("#notification_startingTime").val();
    if(notificationInfo.startTime == null || notificationInfo.startTime == ""){
        alert("Please input the starting time.");
        return;
    }
    notificationInfo.date = $("#notification_date").val();
    if(notificationInfo.date == null || notificationInfo.date == ""){
        alert("Please input the date.");
        return;
    }
    notificationInfo.seatClass = $("#notification_seatClass").val();
    if(notificationInfo.seatClass == null || notificationInfo.seatClass == ""){
        alert("Please input the seat class.");
        return;
    }
    notificationInfo.seatNumber = $("#notification_seatNumber").val();
    if(notificationInfo.seatNumber == null || notificationInfo.seatNumber == ""){
        alert("Please input the seat number.");
        return;
    }
    notificationInfo.price = $("#notification_price").val();
    if(notificationInfo.price == null || notificationInfo.price == ""){
        alert("Please input the price.");
        return;
    }
    var data = JSON.stringify(notificationInfo);
    var type = $("#notification_type").val();
    $("#notification_send_email_button").attr("disabled",true);
    $("#notification_status").text("false");
    if(type == 0){
        $.ajax({
            type: "post",
            url: "/notification/preserve_success",
            contentType: "application/json",
            data:data,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                $("#notification_result").html(result);
                $("#notification_status").text("true");
            },
            complete: function(){
                $("#notification_send_email_button").attr("disabled",false);
            }
        });
    }else if(type == 1){
        $.ajax({
            type: "post",
            url: "/notification/order_create_success",
            contentType: "application/json",
            data:data,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                $("#notification_result").html(result);
                $("#notification_status").text("true");
            },
            complete: function(){
                $("#notification_send_email_button").attr("disabled",false);
            }
        });
    }else if(type == 2){
        $.ajax({
            type: "post",
            url: "/notification/order_changed_success",
            contentType: "application/json",
            data:data,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                $("#notification_result").html(result);
                $("#notification_status").text("true");
            },
            complete: function(){
                $("#notification_send_email_button").attr("disabled",false);
            }
        });
    }
});