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
/********************Function For Food Service************************/
var foodStoreListMap = null;

$("#query_food_button").click(function(){
    $("#query_food_button").attr("disabled",true);

    var data = new Object();
    data.date = $('#food_date').val() || "";
    data.startStation = $('#food_start_station').val() || "";
    data.endStation = $('#food_end_station').val() || "";
    data.tripId = $('#food_trip_id').val() || "";

    // alert(JSON.stringify(data));
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
                var trainFoodList = result.trainFoodList;
                console.log("trainFoodList:" );
                console.log(trainFoodList[0]);

                $("#food_of_store").find("tbody").html("");
                $("#food_stores_of_station_list").find("tbody").html("");
                $("#food_station_select").html("");

                $("#train_food_list_table").find("tbody").html("");
                for(var i = 0; trainFoodList[0] && i < trainFoodList[0]['foodList'].length; i++){
                    console.log("trainfood"+ i + ":");
                    console.log(trainFoodList[0]['foodList'][i]);
                    $("#train_food_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + (i+1) + "</td>" +
                        "<td>" + trainFoodList[0]["tripId"] + "</td>" +
                        "<td>" + trainFoodList[0]['foodList'][i]['foodName'] + "</td>" +
                        "<td>" + trainFoodList[0]['foodList'][i]['price'] + "</td>" +
                        "</tr>"
                    );
                }

                var stationSelect = document.getElementById ("food_station_select");
                var opt1 = document.createElement ("option");
                opt1.value = 0;
                opt1.innerText = "-- --";
                stationSelect.appendChild(opt1);

                // var stationIds = new Array();
                foodStoreListMap = result.foodStoreListMap;
                console.log("foodStoreListMap:");
                console.log(foodStoreListMap);

                var k = 1;
                for(var key in foodStoreListMap){
                    var opt2 = document.createElement ("option");
                    opt2.value = k;
                    k++;
                    opt2.innerText = key;
                    stationSelect.appendChild (opt2);
                }
            } else {
                alert(result.status + ":" + result.message);
            }

        },
        complete: function(){
            $("#query_food_button").attr("disabled",false);
        }
    });

});

function changeFoodStation(){
    var station = $('#food_station_select').find("option:selected").text();
    var  foodStoreList = foodStoreListMap[station];
    console.log("foodStoreList:");
    console.log(foodStoreList);
    showFoodStores(station, foodStoreList);

}

function showFoodStores(station, list){
    console.log("showFoodStores:");
    console.log(list);
    $("#food_of_store").find("tbody").html("");
    $("#food_stores_of_station_list").find("tbody").html("");
    for(var j = 0; j < list.length; j++){
        $("#food_stores_of_station_list").find("tbody").append(
            "<tr>" +
            "<td ><input class='food_index_of_store' type='text' value='" + (j+1) + "'/></td>" +
            "<td>" + list[j]["storeName"] + "</td>" +
            "<td>" + list[j]['telephone'] + "</td>" +
            "<td>" + list[j]['businessTime']  + "</td>" +
            "<td>" + list[j]['deliveryFee']  + "</td>" +
            "<td>" + "<button class='show_store_foods btn btn-primary' >Show Foods</button>" + "</td>" +
            "</tr>"
        );
    }
    addListenerToShowFood();
}

function  addListenerToShowFood(){
    var showFoodBtn = $(".show_store_foods");
    for(var i = 0;i < showFoodBtn.length;i++){
        showFoodBtn[i].onclick = function(){
            var station = $('#food_station_select').find("option:selected").text();
            var storeIndex = parseInt($(this).parents("tr").find(".food_index_of_store").val()) - 1;
            console.log("storeIndex=" + storeIndex);
            showFoods(station, storeIndex);
        }
    }
}


function showFoods(station, j){
    var foodList = foodStoreListMap[station][j]['foodList'];
    console.log("foodlist:");
    console.log(foodList);
    $("#food_of_store").find("tbody").html("");
   for(var j = 0; j < foodList.length; j++){
       $("#food_of_store").find("tbody").append(
           "<tr>" +
           "<td>" + (j+1) + "</td>" +
           "<td>" + foodList[j]["foodName"] + "</td>" +
           "<td>" + foodList[j]['price'] + "</td>" +
           "</tr>"
       );
   }
}

//////////////////////////////////////food order////////////////////////////////////////////
$("#query_food_order_button").click(function(){
    $("#query_food_order_button").attr("disabled",true);

    // alert(JSON.stringify(data));
    $.ajax({
        type: "get",
        url: "/food/findAllFoodOrder",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            console.log(result);
                $("#food_order_list_table").find("tbody").html("");
                for(var i = 0;  result && result.length && i < result.length; i++){
                    console.log("get food order"+ i + ":");
                    console.log(result[i]);
                    var type,stationName,storeName;
                    if(result[i]['foodType'] == 1){
                        type = "train food";
                        stationName = "-";
                        storeName = "-";
                    } else {
                        type = "station food store";
                        stationName = result[i]['stationName'];
                        storeName = result[i]['storeName'];
                    }
                    $("#food_order_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + (i+1) + "</td>" +
                        "<td>" + result[i]["id"] + "</td>" +
                        "<td>" + result[i]['orderId'] + "</td>" +
                        "<td>" + type + "</td>" +
                        "<td>" + stationName + "</td>" +
                        "<td>" + storeName + "</td>" +
                        "<td>" + result[i]['foodName'] + "</td>" +
                        "<td>" + result[i]['price'] + "</td>" +
                        "</tr>"
                    );
                }
        },
        complete: function(){
            $("#query_food_order_button").attr("disabled",false);
        }
    });

});