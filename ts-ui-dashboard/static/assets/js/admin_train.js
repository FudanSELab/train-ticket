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

var trainModule = angular.module("myApp", []);

trainModule.factory('loadDataService', function ($http, $q) {

    var service = {};

    service.loadAdminBasic = function (url) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //返回的数据对象
        var information = new Object();

        $http({
            method: "get",
            url: url,
            headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
            withCredentials: true
        }).success(function (data, status, headers, config) {
            if (data.status == 1) {
                information = data.data;
                deferred.resolve(information);
            }
            else {
                alert("Request the train list fail!" + data.msg);
            }
        }).error(function (data, header, config, status) {
            alert(data.message)
        });
        return promise;
    };

    return service;
});

trainModule.controller("trainCtrl", function ($scope, $http, loadDataService, $window) {

    //首次加载显示数据
    loadDataService.loadAdminBasic("/api/v1/adminbasicservice/adminbasic/trains").then(function (result) {
        console.log(result);
        $scope.trains = result;
    });

    $scope.deleteTrain = function (train) {
        $('#delete-train-confirm').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                $http({
                    method: "delete",
                    url: "/api/v1/adminbasicservice/adminbasic/trains/" + train.id,
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true
                }).success(function (data, status, headers, config) {
                    if (data.status ==1) {
                        alert("Delete train successfully!");
                    } else {
                        alert("Update train failed!");
                    }
                    $window.location.reload();
                }).error(function (data, header, config, status) {
                    alert(data.message)
                });
            },
            // closeOnConfirm: false,
            onCancel: function () {

            }
        });
    };

    $scope.updateTrain = function (train) {
        $('#update-train-id').val(train.id);
        $('#update-train-economy-class').val(train.economyClass);
        $('#update-train-confort-class').val(train.confortClass);
        $('#update-train-average-speed').val(train.averageSpeed);

        $('#update-train-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseInt($('#update-train-economy-class').val()) && parseInt($('#update-train-confort-class').val()) && parseInt($('#update-train-average-speed').val())) {
                    var data = new Object();
                    data.id = train.id;
                    data.economyClass = parseInt($('#update-train-economy-class').val());
                    data.confortClass = parseInt($('#update-train-confort-class').val());
                    data.averageSpeed = parseInt($('#update-train-average-speed').val());
                    // alert(JSON.stringify(data));
                    $http({
                        method: "put",
                        url: "/api/v1/adminbasicservice/adminbasic/trains",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                        withCredentials: true,
                        data: data
                    }).success(function (data, status, headers, config) {
                        if (data.status == 1) {
                            alert("Update train successfully!");
                        } else {
                            alert("Update train failed!");
                        }
                        $window.location.reload();
                    }).error(function (data, header, config, status) {
                        alert(data.message)
                    });
                } else {
                    alert("The economyClass, confortClass and averageSpeed must be an integer!");
                }
            },
            onCancel: function () {

            }
        });
    };

    $scope.addTrain = function () {
        $('#add-train-id').val("");
        $('#add-train-economy-class').val("");
        $('#add-train-confort-class').val("");
        $('#add-train-average-speed').val("");

        $('#add-train-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseInt($('#add-train-economy-class').val()) && parseInt($('#add-train-confort-class').val()) && parseInt($('#add-train-average-speed').val())) {
                    var data = new Object();
                    data.id = $('#add-train-id').val();
                    data.economyClass = parseInt($('#add-train-economy-class').val());
                    data.confortClass = parseInt($('#add-train-confort-class').val());
                    data.averageSpeed = parseInt($('#add-train-average-speed').val());
                    // alert(JSON.stringify(data));
                    $http({
                        method: "post",
                        url: "/api/v1/adminbasicservice/adminbasic/trains",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                        withCredentials: true,
                        data: data
                    }).success(function (data, status, headers, config) {
                        if (data.status ==1) {
                            alert("Add Train successfully!");
                        } else {
                            alert("Add Train failed!");
                        }
                        $window.location.reload();
                    }).error(function (data, header, config, status) {
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