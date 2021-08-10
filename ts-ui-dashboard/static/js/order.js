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
/********************Function For Order Service**********************/
/********************Used For Order Service Single Microservice Test*/

$("#refresh_order_button").click(function(){
    var typeCheckBox = $(".order_type");
    if(typeCheckBox[0].checked && typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/order/findAll");
        refresh_order("/orderOther/findAll");
    }else if(typeCheckBox[0].checked && !typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/order/findAll");
    }else if(!typeCheckBox[0].checked && typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/orderOther/findAll");
    }else{
        alert("Not Select The Order Type.");
    }
});

function refresh_order(path){
    $("#refresh_order_button").attr("disabled",true);
    $("#order_list_status").text("false");
    $.ajax({
        type: "get",
        url: path,
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#order_list_status").text("true");
            if(result["status"] == true){
                var obj = result["orders"];
                for(var i = 0,l = obj.length ; i < l ; i++){
                    $("#all_order_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='all_order_id noshow_component'>" + obj[i]["id"] + "</td>" +
                        "<td>" + obj[i]["id"] + "</td>" +
                        "<td class='all_order_trainNum'>" + obj[i]["trainNumber"] + "</td>" +
                        "<td>" + obj[i]["from"] + "</td>" +
                        "<td>" + obj[i]["to"] + "</td>" +
                        "<td>" + mergeTwoDate(obj[i]["travelDate"],obj[i]["travelTime"]) + "</td>" +
                        "<td>" + convertNumberToHtmlOrderStatus(obj[i]["status"]) + "</td>" +
                        "<td>" + "<button class='all_order_update btn btn-primary'>Update</button>" + "</td>" +
                        "</tr>"
                    );
                }
                addListenerToAllOrderTable();
                $("#order_list_status").text("true");
                //alert("Success.");
            }
        },
        complete: function(){
            $("#refresh_order_button").attr("disabled",false);
        }
    });
}

function addListenerToAllOrderTable(){
    var allOrderUpdateBtnSet = $(".all_order_update");
    for(var i = 0;i < allOrderUpdateBtnSet.length;i++){
        allOrderUpdateBtnSet[i].onclick = function(){
            var updateInfo = new Object();
            updateInfo.orderId = $(this).parents("tr").find(".all_order_id").text();
            updateInfo.status = $(this).parents("tr").find(".all_order_status").val();
            var data = JSON.stringify(updateInfo);
            var path = "";
            var tripType = $(this).parents("tr").find(".all_order_trainNum").text().charAt(0);
            if(tripType == 'G' || tripType == 'D'){
                path = "/order/modifyOrderStatus";
            }else{
                path = "/orderOther/modifyOrderStatus";
            }
            $("#order_list_status").text("false");
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
                    $("#order_list_status").text("true");
                    if(result["status"] == true){
                        refresh_order();
                        //alert("Success.");
                    }else{
                        //alert(result["message"]);
                    }
                },
                complete: function(){
                }
            });
        }
    }
}

function convertNumberToHtmlOrderStatus(number){
    var result = "<select class='all_order_status form-control' name='documentType'>";
    if(number == 0){
        result +=
            "<option selected='selected' value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>" +
            "<option value='6'>Used</option>";
    }else if(number == 1){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option selected='selected' value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>" +
            "<option value='6'>Used</option>";
    }else if(number == 2){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option selected='selected' value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>" +
            "<option value='6'>Used</option>";
    }else if(number == 3){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option selected='selected' value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>" +
            "<option value='6'>Used</option>";
    }else if(number == 4){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option selected='selected' value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>" +
            "<option value='6'>Used</option>";
    }else if(number == 5) {
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option selected='selected' value='5'>Refunded</option>" +
            "<option value='6'>Used</option>";
    }else if(number == 6){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option value='5'>Refunded</option>" +
            "<option selected='selected' value='6'>Used</option>";
    }
    result += "</select>";
    return result;
}

