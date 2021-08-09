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
/********************Function For Config Service*********************/
/********************For Config Service Single Microservice Test*****/
$("#config_update_button").click(function(){
    var configInfo = new Object();
    configInfo.name = $("#config_update_name").val();
    if(configInfo.name == null || configInfo.name == ""){
        alert("Please input the name of the new config you want to add.");
        return;
    }
    configInfo.value = $("#config_update_value").val();
    if(configInfo.value == null || configInfo.value == ""){
        alert("Please input the value of the new config you want to add.");
        return;
    }
    configInfo.description = $("#config_update_description").val();
    if(configInfo.description == null || configInfo.description == ""){
        alert("Please input the description of the new config you want to add.");
        return;
    }
    var data = JSON.stringify(configInfo);
    $("#config_update_button").attr("disabled",true);
    $("#single_update_config_status").text("false");
    $.ajax({
        type: "post",
        url: "/config/update",
        contentType: "application/json",
        dataType: "text",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#config_result").html(result);
            $("#single_update_config_status").text("true");
        },
        error: function(){
            $("#single_update_config_status").text("false");
        },
        complete: function(){
            $("#config_update_button").attr("disabled",false);
        }
    });
});

//------For config query------------
$("#config_query_button").click(function(){
    $("#config_query_button").attr("disabled",true);
    $("#single_list_config_status").text("false");
    $.ajax({
        type: "get",
        url: "/config/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_config_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_config_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["name"]     + "</td>" +
                    "<td>" + result[i]["value"]     + "</td>" +
                    "<td>" + result[i]["description"]     + "</td>" +
                    "</tr>"
                );
            }
            $("#single_list_config_status").text("true");
            //$("#config_result").html(result);
        },
        error: function(){
            $("#single_list_config_status").text("false");
        },
        complete: function(){
            $("#config_query_button").attr("disabled",false);
        }
    });
});

//------For Config delete------------
// document.getElementById("config_delete_button").onclick = function post_config_delete(){
//     var configInfo = new Object();
//     configInfo.name = $("#config_delete_name").val();
//     var data = JSON.stringify(configInfo);
//     $.ajax({
//         type: "post",
//         url: "/config/delete",
//         contentType: "application/json",
//         dataType: "text",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#config_result").html(result);
//         }
//     });
// }

//------For Config------------
//------For Config create------------
// document.getElementById("config_create_button").onclick = function post_config_create(){
//     var configInfo = new Object();
//     configInfo.name = $("#config_create_name").val();
//     configInfo.value = $("#config_create_value").val();
//     configInfo.description = $("#config_create_description").val();
//     var data = JSON.stringify(configInfo);
//     $.ajax({
//         type: "post",
//         url: "/config/create",
//         contentType: "application/json",
//         dataType: "text",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#config_result").html(result);
//         }
//     });
// }
