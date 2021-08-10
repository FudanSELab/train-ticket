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


/**********************************************************************/
/********************Function For Assurance Service************************/
$("#refresh_assurance_button").click(function(){
    $("#refresh_assurance_button").attr("disabled",true);
    $("#assurance_list_status").text("false");
    $.ajax({
        type: "get",
        url: "/assurance/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (obj) {
            var result = obj["assurances"];
            var size = result.length;
            $("#assurance_list_table").find("tbody").html("");
            for (var i = 0; i < size; i++) {
                $("#assurance_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='all_assurance_id '>" + result[i]["id"] + "</td>" +
                    "<td>" +
                    "<input class='all_assurance_order_id' value='" + result[i]["orderId"] + "'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='all_assurance_type_index form-control' value='" + result[i]["typeIndex"] + "'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='all_assurance_type_name' value='" + result[i]["typeName"] + "'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='all_assurance_type_price' value='" + result[i]["typePrice"] + "'>" +
                    "</td>" +
                    "<td>" + "<button class='all_assurance_update btn btn-primary'>Update</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToAllAssuranceTable();
            $("#assurance_list_status").text("true");
        },
        complete: function(){
            $("#refresh_assurance_button").attr("disabled",false);
        }
    });
});

function addListenerToAllAssuranceTable(){
    var allAssuranceUpdateBtnSet = $(".all_assurance_update");
    for(var i = 0;i < allAssuranceUpdateBtnSet.length;i++){
        allAssuranceUpdateBtnSet[i].onclick = function(){

            var assurance = new Object();
            assurance.assuranceId = $(this).parents("tr").find(".all_assurance_id").text();;
            assurance.orderId = $(this).parents("tr").find(".all_assurance_order_id").val();
            assurance.typeIndex = $(this).parents("tr").find(".all_assurance_type_index").val();

            var assuranceData = JSON.stringify(assurance);
            alert(assuranceData);

            $.ajax({
                type: "post",
                url: "/assurance/modifyAssurance",
                contentType: "application/json",
                dataType: "json",
                data:assuranceData,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    var obj = result;
                    if(obj["status"] == true){
                        alert("Success");
                    }else{
                        alert("Update Fail");
                    }
                },
                complete: function(){
                }
            });

        }
    }
}

$("#create_assurance_button").click(function(){

    // var orderId = $("#assurance_order_id").val();
    var typeIndex = $("#assurance_type_index").val();

    var newAssurance = new Object();
    newAssurance.id = "";
    newAssurance.orderId = guid();
    newAssurance.typeIndex = typeIndex;

    var assuranceData = JSON.stringify(newAssurance);

    $("#create_assurance_button").attr("disabled",true);
    $("#create_assurance_status").html("false");

    $.ajax({
        type: "post",
        url: "/assurance/create",
        contentType: "application/json",
        dataType: "json",
        data: assuranceData,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#create_assurance_create_message").html(obj["message"]);
            }else{
                $("#create_assurance_create_message").html(obj["message"]);
            }
            $("#create_assurance_status").html("true");
        },
        complete: function(){
            $("#create_assurance_button").attr("disabled",false);
        }
    });

});

function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}