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
/********************Function For Ticket Office Service************************/
var regionList = [];
// var selectProvince = 0, selectCity = 0, selectRegion = 0;

$(function () {
    getRegionList();
});

function changeProvince(){
    // console.log("get into the province change");
    var city = document.getElementById ("office_city");
    // selectProvince = $('select#office_province').val();
    $("#office_city").empty();
    $("#office_region").empty();
    $("#office_list_table").find("tbody").html("");
    for (var i = 0, l = regionList.length; i < l; i++) {
        // console.log("get into the province loop:" + $('#office_province').find("option:selected").text());
        if(regionList[i]['province'] == $('#office_province').find("option:selected").text()){
            var opt1 = document.createElement ("option");
            opt1.value = 0;
            opt1.innerText = "-- --";
            city.appendChild(opt1);
            for(var j = 0, k = regionList[i]['cities'].length; j < k; j++){
                var opt11 = document.createElement ("option");
                opt11.value = j + 1;
                opt11.innerText = regionList[i]['cities'][j]['city'];
                city.appendChild (opt11);
                // console.log(regionList[i]['cities'][j]['city']);
            }
            city.value = 0;
            // selectCity = 0;
        }
    }
}

function changeCity(){
    var region = document.getElementById ("office_region");
    // selectCity = $('select#office_city').val();
    $("#office_region").empty();
    $("#office_list_table").find("tbody").html("");
    for (var i = 0, l = regionList.length; i < l; i++) {
        if(regionList[i]['province'] == $("#office_province").find("option:selected").text()){
            for(var m = 0; m < regionList[i]['cities'].length; m++){
                if(regionList[i]['cities'][m]['city'] == $('#office_city').find("option:selected").text()){
                    var opt2 = document.createElement ("option");
                    opt2.value = 0;
                    opt2.innerText = "-- --";
                    region.appendChild(opt2);
                    for(var j = 0, k = regionList[i]['cities'][m]['regions'].length; j < k; j++){
                        var opt22 = document.createElement ("option");
                        opt22.value = j + 1;
                        opt22.innerText = regionList[i]['cities'][m]['regions'][j]['region'];
                        // console.log(regionList[i]['cities'][m]['regions'][j]['region']);
                        region.appendChild (opt22);
                    }
                    region.value = 0;
                    // selectRegion = 0;
                }
            }
        }
    }
}

function changeRegion(){
    $("#office_list_table").find("tbody").html("");
}

function getRegionList(){
    $.ajax({
        type: "get",
        url: "/office/getRegionList",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            regionList = result;
            var province = document.getElementById ("office_province");

            var opt0 = document.createElement ("option");
            opt0.value = 0;
            opt0.innerText = "-- --";
            province.appendChild (opt0);
            for (var i = 0, l = regionList.length; i < l; i++) {
                var opt00 = document.createElement ("option");
                opt00.value = i + 1;
                opt00.innerText = regionList[i]["province"];
                province.appendChild (opt00);
            }
            province.value = 0;
        },
        complete: function(){

        }
    });
}

//获取特定省市区的代售点列表
$("#query_office_button").click(function(){
    if($('#office_province').val() != 0 && $('#office_city').val() != 0 && $('#office_region').val() != 0){
        $("#query_office_button").attr("disabled",true);
        $("#office_list_status").text("false");

        var data = new Object();
        data.province = $('#office_province').find("option:selected").text();
        data.city = $('#office_city').find("option:selected").text();
        data.region = $('#office_region').find("option:selected").text();

        $.ajax({
            type: "post",
            url: "/office/getSpecificOffices",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            xhrFields: {
                withCredentials: true
            },
            success: function (obj) {
                // console.log(obj);
                var result = obj[0]['offices'];
                var size = result.length;
                $("#office_list_table").find("tbody").html("");
                for (var i = 0; i < size; i++) {
                    $("#office_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + (i + 1) + "</td>" +
                        "<td >" +
                        "<input class='all_office_name form-control' value='" + result[i]["officeName"] + "'>" +
                        "</td>" +
                        "<td>" +
                        "<input class='all_office_address form-control' value='" + result[i]["address"] + "'>" +
                        "</td>" +
                        "<td>" +
                        "<input class='all_office_work_time form-control' value='" + result[i]["workTime"] + "'>" +
                        "</td>" +
                        "<td>" +
                        "<input class='all_office_window_num form-control' value='" + result[i]["windowNum"] + "'>" +
                        "</td>" +
                        "<td>" + "<button class='all_office_update btn btn-primary'>Update</button>" +
                        "   <button class='all_office_delete btn btn-primary'>Delete</button>" + "</td>" +
                        "<td>" +
                        "<input class='all_office_oldName noshow_component' value='" + result[i]["officeName"] + "'>" +
                        "</td>" +
                        "</tr>"
                    );
                }
                $("#office_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>new: </td>" +
                    "<td>" +
                    "<input class='add_office_name form-control'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='add_office_address form-control'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='add_office_work_time form-control'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='add_office_window_num form-control'>" +
                    "</td>" +
                    "<td>" + "<button id='create_office_button' onclick='createOffice()' class='btn btn-primary'>Add</button>" + "</td>" +
                    "</tr>"
                );
                addListenerToAllOfficeTable();
                $("#office_list_status").text("true");
            },
            complete: function(){
                $("#query_office_button").attr("disabled",false);
            }
        });

    } else {
        alert("Please select the province, city and province first!");
    }

});


function addListenerToAllOfficeTable(){
    var allOfficeUpdateBtnSet = $(".all_office_update");
    for(var i = 0;i < allOfficeUpdateBtnSet.length;i++){

        allOfficeUpdateBtnSet[i].onclick = function(){
            var data = new Object();
            data.province = $('#office_province').find("option:selected").text();
            data.city = $('#office_city').find("option:selected").text();
            data.region = $('#office_region').find("option:selected").text();
            data.oldOfficeName = $(this).parents("tr").find(".all_office_oldName").val();

            var newOffice = new Object();
            newOffice.officeName = $(this).parents("tr").find(".all_office_name").val();
            newOffice.address = $(this).parents("tr").find(".all_office_address").val();
            newOffice.workTime = $(this).parents("tr").find(".all_office_work_time").val();
            newOffice.windowNum = $(this).parents("tr").find(".all_office_window_num").val();

            data.newOffice = newOffice;

            data = JSON.stringify(data);
            alert(data);

            $.ajax({
                type: "post",
                url: "/office/updateOffice",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    console.log(JSON.stringify(result));
                    if(result.ok){
                        alert("Update Office Success!");
                    }
                },
                complete: function(){
                }
            });
        }
    }


    var allOfficeDeleteBtnSet = $(".all_office_delete");

    for(var i = 0;i < allOfficeDeleteBtnSet.length;i++){
        allOfficeDeleteBtnSet[i].onclick = function(){

            var data = new Object();
            data.province = $('#office_province').find("option:selected").text();
            data.city = $('#office_city').find("option:selected").text();
            data.region = $('#office_region').find("option:selected").text();
            data.officeName = $(this).parents("tr").find(".all_office_oldName").val();

            data = JSON.stringify(data);
            alert(data);

            $.ajax({
                type: "post",
                url: "/office/deleteOffice",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    console.log(JSON.stringify(result));
                    if(result.ok){
                        alert("Update Office Success!");
                    }
                },
                complete: function(){
                    $("#query_office_button").click();
                }
            });
        }
    }
}

function createOffice(){
    // alert($(".add_office_name").val() + $(".add_office_address").val() + $(".add_office_work_time").val() + $(".add_office_window_num").val());

    if($(".add_office_name").val() && $(".add_office_address").val() && $(".add_office_work_time").val() && $(".add_office_window_num").val()){
        var data = new Object();
        data.province = $('#office_province').find("option:selected").text();
        data.city = $('#office_city').find("option:selected").text();
        data.region = $('#office_region').find("option:selected").text();
        var office = new Object();
        office.officeName = $(".add_office_name").val();
        office.address = $(".add_office_address").val();
        office.workTime = $(".add_office_work_time").val();
        office.windowNum = $(".add_office_window_num").val();

        data.office = office;

        data = JSON.stringify(data);
        // alert(data);

        $("#create_office_button").attr("disabled",true);

        $.ajax({
            type: "post",
            url: "/office/addOffice",
            contentType: "application/json",
            dataType: "json",
            data: data,
            xhrFields: {
                withCredentials: true
            },
            success: function(result){
                // console.log(JSON.stringify(result));
                if(result.ok){
                    alert("Add Office Success!");
                }
            },
            complete: function(){
                // $("#create_office_button").attr("disabled",false);
                $("#query_office_button").click();
            }
        });

    } else {
        alert("Please fill all the blank input!");
    }

};
