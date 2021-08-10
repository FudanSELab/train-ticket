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

/**
 * Created by lwh on 2017/11/16.
 */
/*
 * 显示管理员名字
 * */
var loadBody = function () {
    var username = sessionStorage.getItem("admin_name");
    if (username == null) {
        alert("Please login first!");
        location.href = "adminlogin.html";
    }
    else {
        document.getElementById("admin_name").innerHTML = username;
    }
};

/*
 * 登出
 * */
var logout = function () {
    sessionStorage.clear();
    location.href = "adminlogin.html";
}

/*
 * 将加载数据封装为一个服务
 * */
var app = angular.module('myApp', []);
app.factory('loadDataService', function ($http, $q) {

    var service = {};

    //获取并返回数据
    service.loadRecordList = function (param) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //返回的数据对象
        var information = new Object();

        $http({
            method: "get",
            url: "/api/v1/adminrouteservice/adminroute",
            headers: {"Authorization": "Bearer "+param.admin_token},
            withCredentials: true,
        }).success(function (data, status, headers, config) {
            console.log(data);
            if (data.status == 1) {
                information.routeRecords = data.data;
                deferred.resolve(information);
            }
            else{
                alert("Request the order list fail!" + data.msg);
            }
        });

        return promise;
    };

    return service;
});

/*
 * 加载列表
 * */
app.controller('indexCtrl', function ($scope, $http,$window,loadDataService) {
    var param = {};
    param.admin_token = sessionStorage.getItem("admin_token");

    //刷新页面
    $scope.reloadRoute = function () {
        $window.location.reload();
    };

    //首次加载显示数据
    loadDataService.loadRecordList(param).then(function (result) {
        $scope.records = result.routeRecords;
        //$scope.decodeInfo(result.orderRecords[0]);
    });

    $scope.decodeInfo = function (obj) {
        var des = "";
        for(var name in obj){
            des += name + ":" + obj[name] + ";";
        }
        alert(des);
    }
    
    //Add new route
    // has route id ---> update , no add new
    $scope.addNewRoute = function () {
        $('#add_prompt').modal({
            relatedTarget: this,
            onConfirm: function(e) {
                $http({
                    method: "post",
                    url: "/api/v1/adminrouteservice/adminroute",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data:{
                        stationList: $scope.add_route_stations,
                        distanceList: $scope.add_route_distances,
                        startStation: $scope.add_route_start_station,
                        endStation: $scope.add_route_terminal_station
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Add the route fail!" + data.msg);
                    }
                }).error(function(data, header, config, status){
                    alert(data.message)
                });
            },
            onCancel: function(e) {
                alert('You have canceled the operation!');
            }
        });
    }
    
    //Update exist route
    $scope.updateRoute = function (record) {
        $scope.update_route_id = record.id;
        $scope.update_route_stations = record.stations;
        $scope.update_route_distances = record.distances;
        $scope.update_route_start_station = record.startStationId;
        $scope.update_route_terminal_station = record.terminalStationId;

        $('#update_prompt').modal({
            relatedTarget: this,
            onConfirm: function(e) {
                $http({
                    method: "post",
                    url: "/api/v1/adminrouteservice/adminroute",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data:{
                        id: $scope.update_route_id,
                        stationList: $scope.update_route_stations + "",
                        distanceList: $scope.update_route_distances + "",
                        startStation: $scope.update_route_start_station,
                        endStation: $scope.update_route_terminal_station
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Update the route fail!" + data.msg);
                    }
                }).error(function(data, header, config, status){
                    alert(data.message)
                });
            },
            onCancel: function(e) {
                alert('You have canceled the operation!');
            }
        });
    }

    //Delete route
    $scope.deleteRoute = function(routeId){
        $('#delete_confirm').modal({
            relatedTarget: this,
            onConfirm: function(options) {
                $http({
                    method: "delete",
                    url: "/api/v1/adminrouteservice/adminroute/" + routeId,
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Delete the route fail!" + data.msg);
                    }
                }).error(function(data, header, config, status){
                    alert(data.message)
                });
            },
            // closeOnConfirm: false,
            onCancel: function() {
                alert('You have canceled the operation!');
            }
        });
    }
});