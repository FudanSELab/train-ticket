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
/********************Function For Contacts Service*******************/
/********************For contacts Service Single Microservice Test***/

$("#add_contacts_button").click(function() {
    var addContactsInfo = new Object();
    addContactsInfo.name = $("#add_contacts_name").val();
    if(addContactsInfo.name == null || addContactsInfo.name == ""){
        alert("Please input the contacts name of the new contacts you want to add.");
        return;
    }
    addContactsInfo.documentType = $("#add_contacts_documentType").val();
    if(addContactsInfo.documentType == null || addContactsInfo.documentType == ""){
        alert("Please select the document type of the new contacts you want to add.");
        return;
    }
    addContactsInfo.documentNumber = $("#add_contacts_documentNum").val();
    if(addContactsInfo.documentNumber == null || addContactsInfo.documentNumber == ""){
        alert("Please input the document number of the new contacts you want to add.");
        return;
    }
    addContactsInfo.phoneNumber = $("#add_contacts_phoneNum").val();
    if(addContactsInfo.phoneNumber == null || addContactsInfo.phoneNumber == ""){
        alert("Please input the phone number of the new contacts you want to add.");
        return;
    }
    var data = JSON.stringify(addContactsInfo);
    $("#add_contacts_button").attr("disabled",true);
    $("#add_contacts_result_status").text("false");
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
            if(obj["status"] == true){
                //
            }
            $("#add_contacts_result_status").text("true");
            var obj = result;
            $("#add_contacts_result_msg").html(obj["message"]);
            $("#add_contacts_result_contacts").html(JSON.stringify(obj["contacts"]));
        },
        complete: function(){
            $("#add_contacts_button").attr("disabled",false);
        }
    });
});

$("#refresh_contacts_button").click(function () {
    refresh_contacts();
});

function addListenerToAllContactsTable(){
    var allContactsUpdateBtnSet = $(".all_contacts_update");
    for(var i = 0;i < allContactsUpdateBtnSet.length;i++){
        allContactsUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.contactsId = $(this).parents("tr").find(".all_contacts_id").text();
            modifyInfo.documentNumber = $(this).parents("tr").find(".all_contacts_documentNum").val();
            modifyInfo.name = $(this).parents("tr").find(".all_contacts_name").val();
            modifyInfo.documentType = $(this).parents("tr").find(".all_contacts_documentType").val();;
            modifyInfo.phoneNumber = $(this).parents("tr").find(".all_contacts_phoneNum").val();
            modifyInfo.loginToken = "NotNeed";
            var data = JSON.stringify(modifyInfo);
            $("#order_list_status").text("false");
            $.ajax({
                type: "post",
                url: "/contacts/modifyContacts",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_contacts();
                        //alert("Success.");
                    }else{
                        //alert(result["message"]);
                    }
                    $("#order_list_status").text("true");
                },
                complete: function(){
                }
            });
        }
    }
    var allContactsDeleteBtnSet = $(".all_contacts_delete");
    for(var i = 0;i < allContactsDeleteBtnSet.length;i++){
        allContactsDeleteBtnSet[i].onclick = function(){
            var deleteInfo = new Object();
            deleteInfo.contactsId = $(this).parents("tr").find(".all_contacts_id").text();
            deleteInfo.loginToken = "NotNeed";
            var data = JSON.stringify(deleteInfo);
            $("#order_list_status").text("false");
            $.ajax({
                type: "post",
                url: "/contacts/deleteContacts",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_contacts();
                       // alert("Success.");
                    }else{
                       //alert(result["message"]);
                    }
                    $("#order_list_status").text("true");
                },
                complete: function(){
                }
            });
        }
    }
}

function refresh_contacts(){
    $("#refresh_contacts_button").attr("disabled",true);
    $("#register_result_status").text("false");
    $.ajax({
        type: "get",
        url: "/contacts/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result["status"] == true){
                var obj = result["contacts"];
                $("#contacts_list_table").find("tbody").html("");
                for(var i = 0,l = obj.length ; i < l ; i++){
                    $("#contacts_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i                                                    + "</td>" +
                        "<td class='all_contacts_id noshow_component'>" + obj[i]["id"]                + "</td>" +
                        "<td>" + obj[i]["accountId"]                                  + "</td>" +
                        "<td ><input class='all_contacts_name form-control' value='" + obj[i]["name"] + "'></td>" +
                        "<td>" + convertNumberToHtmlDocumentType(obj[i]["documentType"])  + "</td>" +
                        "<td ><input class='all_contacts_documentNum form-control' value='" + obj[i]["documentNumber"] + "'></td>" +
                        "<td ><input class='all_contacts_phoneNum form-control' value='" + obj[i]["phoneNumber"] + "'></td>" +
                        "<td>" +  "<button class='all_contacts_update btn btn-primary'>Update</button>" + "<button class='all_contacts_delete btn btn-primary'>Delete</button>" + "</td>" +
                        "</tr>"
                    );
                }
                addListenerToAllContactsTable();
                //alert("Success.");
            }
            $("#register_result_status").text("true");
        },
        complete: function(){
            $("#refresh_contacts_button").attr("disabled",false);
        }
    });
}

function convertNumberToHtmlDocumentType(number){
    var result = "";
    if(number == 1){
        result =
            "<select  class='all_contacts_documentType form-control' name='documentType'>" +
            "<option value='1' selected = 'selected'>ID Card</option>" +
            "<option value='2'>Passport</option>" +
            "<option value='3'>Other</option>" +
            "</select>";
    }else if(number == 2){
        result =
            "<select class='all_contacts_documentType form-control' name='documentType'>" +
            "<option value='1'>ID Card</option>" +
            "<option value='2' selected = 'selected'>Passport</option>" +
            "<option value='3'>Other</option>" +
            "</select>";
    }else{
        result =
            "<select  class='all_contacts_documentType form-control' name='documentType'>" +
            "<option value='1'>ID Card</option>" +
            "<option value='2'>Passport</option>" +
            "<option value='3' selected = 'selected'>Other</option>" +
            "</select>";
    }
    return result;
}
