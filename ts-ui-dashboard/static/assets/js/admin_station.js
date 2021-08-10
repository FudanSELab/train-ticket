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

var stationModule = angular.module("myApp", []);

stationModule.factory('loadDataService', function ($http, $q) {

    var service = {};

    service.loadAdminBasic = function (url) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //返回的数据对象
        var information = new Object();

        $http({
            method: "get",
            url: url ,
            headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
            withCredentials: true
        }).success(function (data, status, headers, config) {
            if (data.status == 1) {

                information = data.data;
                deferred.resolve(information);
            }
            else {
                alert("Request the station list fail!" + data.msg);
            }
        }).error(function(data, header, config, status){
            alert(data.message)
        });
        return promise;
    };

    return service;
});

stationModule.controller("stationCtrl", function ($scope, $http, loadDataService, $window) {

    //首次加载显示数据
    loadDataService.loadAdminBasic("/api/v1/adminbasicservice/adminbasic/stations").then(function (result) {
        console.log(result);
        $scope.stations = result;
    });

    $scope.deleteStation = function (station) {
        $('#delete-station-confirm').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                $http({
                    method: "delete",
                    url: "/api/v1/adminbasicservice/adminbasic/stations",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: {
                        id: station.id,
                        name: station.name,
                        stayTime: station.stayTime
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status ==1) {
                        alert("Delete station successfully!");
                    } else {
                        alert("Update station failed!");
                    }
                    $window.location.reload();
                }).error(function(data, header, config, status){
                    alert(data.message)
                });
            },
            // closeOnConfirm: false,
            onCancel: function () {

            }
        });
    };

    $scope.updateStation = function (station) {
        $('#update-station-name').val(station.name);
        $('#update-station-stay-time').val(station.stayTime);


        $('#update-station-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseInt($('#update-station-stay-time').val())) {
                    var data = new Object();
                    data.id = station.id;
                    data.name = $('#update-station-name').val();
                    data.stayTime = parseInt($('#update-station-stay-time').val());
                    // alert(JSON.stringify(data));
                    $http({
                        method: "put",
                        url: "/api/v1/adminbasicservice/adminbasic/stations",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                        withCredentials: true,
                        data: data
                    }).success(function (data, status, headers, config) {
                        if (data.status ==1) {
                            alert("Update station successfully!");
                        } else {
                            alert("Update station failed!");
                        }
                        $window.location.reload();
                    }).error(function(data, header, config, status){
                        alert(data.message)
                    });
                } else {
                    alert("The stay time must be an integer!");
                }

            },
            onCancel: function () {

            }
        });
    };

    $scope.addStation = function () {
        $('#add-station-id').val("");
        $('#add-station-name').val("");
        $('#add-station-stay-time').val("");

        $('#add-station-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseInt($('#add-station-stay-time').val())) {
                    var data = new Object();
                    data.id = $('#add-station-id').val();
                    data.name = $('#add-station-name').val();
                    data.stayTime = parseInt($('#add-station-stay-time').val());
                    // alert(JSON.stringify(data));
                    $http({
                        method: "post",
                        url: "/api/v1/adminbasicservice/adminbasic/stations",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                        withCredentials: true,
                        data: data
                    }).success(function (data, status, headers, config) {
                        if (data.status == 1) {
                            alert("Add station successfully!");
                        } else {
                            alert("Add station failed!");
                        }
                        $window.location.reload();
                    }).error(function(data, header, config, status){
                        alert(data.message)
                    });
                } else {
                    alert("The staytime must be an integer!");
                }

            },
            onCancel: function () {

            }
        });
    };


});