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
/********************Function For Train Service**********************/
/********************For Train Service Single Microservice Test******/
$("#train_update_button").click(function(){
    var trainInfo = new Object();
    trainInfo.id = $("#train_update_id").val();
    if(trainInfo.id == null || trainInfo.id == ""){
        alert("Please input train ID.");
        return;
    }
    trainInfo.economyClass = $("#train_update_economyClass").val();
    if(trainInfo.economyClass == null || trainInfo.economyClass == ""){
        alert("Please input the number of the economy class seat.");
        return;
    }
    trainInfo.confortClass = $("#train_update_confortClass").val();
    if(trainInfo.confortClass == null || trainInfo.confortClass == ""){
        alert("Please input the number of the confort class seat.");
        return;
    }
    trainInfo.averageSpeed = $("#train_update_averageSpeed").val();
    if(trainInfo.averageSpeed == null || trainInfo.averageSpeed == ""){
        alert("Please input the number of the average speed.");
        return;
    }
    var data = JSON.stringify(trainInfo);
    $("#train_update_button").attr("disabled",true);
    $("#single_update_train_status").text("false");
    $.ajax({
        type: "post",
        url: "/train/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            //$("#train_result").html(JSON.stringify(result));
            $("#single_update_train_status").text("true");
        },
        complete: function(){
            $("#train_update_button").attr("disabled",false);
        }
    });
});


// document.getElementById("train_update_button").onclick = function post_train_update(){
//
// }
$("#train_query_button").click(function(){
    // var trainInfo = new Object();
    // trainInfo.id = $("#train_query_id").val();
    // var data = JSON.stringify(trainInfo);
    $("#train_query_button").attr("disabled",true);
    $("#single_list_train_status").text("false");
    $.ajax({
        type: "get",
        url: "/train/query",
        // contentType: "application/json",
        dataType: "json",
        // data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_train_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_train_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"]     + "</td>" +
                    "<td>" + result[i]["confortClass"]     + "</td>" +
                    "<td>" + result[i]["economyClass"]     + "</td>" +
                    "<td>" + result[i]["averageSpeed"]     + "</td>" +
                    "</tr>"
                );
            }
            $("#single_list_train_status").text("true");
            //$("#train_result").html(JSON.stringify(result));
        },
        complete: function(){
            $("#train_query_button").attr("disabled",false);
        }
    });
});

//------For Train delete------------
// document.getElementById("train_delete_button").onclick = function post_train_delete(){
//     var trainInfo = new Object();
//     trainInfo.id = $("#train_delete_id").val();
//     var data = JSON.stringify(trainInfo);
//     $.ajax({
//         type: "post",
//         url: "/train/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#train_result").html(JSON.stringify(result));
//         }
//     });
// }

//------For Train------------
//------For Train create------------
// document.getElementById("train_create_button").onclick = function post_train_create(){
//     var trainInfo = new Object();
//     trainInfo.id = $("#train_create_id").val();
//     trainInfo.economyClass = $("#train_create_economyClass").val();
//     trainInfo.confortClass = $("#train_create_confortClass").val();
//     var data = JSON.stringify(trainInfo);
//     $.ajax({
//         type: "post",
//         url: "/train/create",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#train_result").html(JSON.stringify(result));
//         }
//     });
// }
