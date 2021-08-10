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


/************************************************************************/
/********************Function For Security Service***********************/
/********************Used For Security Service Single Microservice Test**/

$("#security_check_button").click(function() {
    var checkInfo = new Object();
    checkInfo.accountId = $("#security_check_account_id").val();
    if(checkInfo.accountId == null || checkInfo.accountId == ""){
        alert("Please input account id you want to check.");
        return;
    }
    var data = JSON.stringify(checkInfo);
    $("#security_check_button").attr("disabled",true);
    $("#security_check_status").html("false");
    $.ajax({
        type: "post",
        url: "/security/check",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#security_check_message").html(obj["message"]);
            }else{
                $("#security_check_message").html(obj["message"]);
            }
            $("#security_check_status").html("true");
        },
        complete: function(){
            $("#security_check_button").attr("disabled",false);
        }
    });
});


$("#refresh_security_config_button").click(function() {
    refresh_security_config();
});

function refresh_security_config() {
    $("#refresh_security_config_button").attr("disabled",true);
    $("#security_list_status").text("false");
    $.ajax({
        type: "get",
        url: "/securityConfig/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result["status"] == true){
                $("#security_config_list_table").find("tbody").html("");
                var obj = result["result"];
                for(var i = 0,l = obj.length ; i < l ; i++){
                    $("#security_config_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='noshow_component list_security_config_id'>" + obj[i]["id"] + "</td>" +
                        "<td class='list_security_config_name'>" + obj[i]["name"] + "</td>" +
                        "<td ><input class='list_security_config_id_value form-control' value='" + obj[i]["value"] + "'></td>" +
                        "<td ><input class='list_security_config_id_description form-control' value='" + obj[i]["description"] + "'></td>" +
                        "<td>" + "<button class='security_config_update_btn btn btn-primary'>Update</button>" + "<button class='security_config_delete btn btn-primary noshow_component'>Delete</button>" + "</td>" +
                        "</tr>"
                    );
                }
                addListenerToAllSecurityConfigTable();
                $("#security_list_status").text("true");
                //alert("Success.");
            }
        },
        complete: function(){
            $("#refresh_security_config_button").attr("disabled",false);
        }
    });
}

function addListenerToAllSecurityConfigTable(){
    var allSecurityConfigUpdateBtnSet = $(".security_config_update_btn");
    for(var i = 0;i < allSecurityConfigUpdateBtnSet.length;i++){
        allSecurityConfigUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.id = $(this).parents("tr").find(".list_security_config_id").text();
            modifyInfo.name = $(this).parents("tr").find(".list_security_config_name").text();
            modifyInfo.value = $(this).parents("tr").find(".list_security_config_id_value").val();
            modifyInfo.description = $(this).parents("tr").find(".list_security_config_id_description").val();
            var data = JSON.stringify(modifyInfo);
            $("#security_list_status").text("false");
            $.ajax({
                type: "post",
                url: "/securityConfig/update",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_security_config();
                        //alert("Success.");
                    }else{
                        //alert(result["message"]);
                    }
                    $("#security_list_status").text("true");
                },
                complete: function(){
                }
            });
        }
    }
    var allSecurityConfigDeleteBtnSet = $(".security_config_delete");
    for(var i = 0;i < allSecurityConfigDeleteBtnSet.length;i++){
        allSecurityConfigDeleteBtnSet[i].onclick = function(){
            var deleteInfo = new Object();
            deleteInfo.id = $(this).parents("tr").find(".list_security_config_id").text();
            var data = JSON.stringify(deleteInfo);
            $("#security_list_status").text("false");
            $.ajax({
                type: "post",
                url: "/securityConfig/delete",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_security_config();
                        //alert("Success.");
                    }else{
                        //alert(result["message"]);
                    }
                    $("#security_list_status").text("true");
                },
                complete: function(){
                }
            });
        }
    }
}


