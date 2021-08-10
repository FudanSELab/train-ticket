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

var priceModule = angular.module("myApp", []);

priceModule.factory('loadDataService', function ($http, $q) {

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
            if (data.status) {
                information = data.data;
                deferred.resolve(information);
            }
            else {
                alert("Request the Price list fail!" + data.msg);
            }
        }).error(function(data, header, config, status){
            alert(data.msg)
        });
        return promise;
    };

    return service;
});

priceModule.controller("priceCtrl", function ($scope, $http, loadDataService, $window) {

    //首次加载显示数据
    loadDataService.loadAdminBasic("/api/v1/adminbasicservice/adminbasic/prices").then(function (result) {
        console.log(result);
        $scope.prices = result;
    });

    $scope.deletePrice = function (price) {
        $('#delete-price-confirm').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                $http({
                    method: "delete",
                    url: "/api/v1/adminbasicservice/adminbasic/prices",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: {
                        id: price.id,
                        routeId: price.routeId,
                        trainType: price.trainType,
                        basicPriceRate: price.basicPriceRate,
                        firstClassPriceRate: price.firstClassPriceRate
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status ==1) {
                        alert("Delete price successfully!");
                    } else {
                        alert("Update price failed!");
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

    $scope.updatePrice = function (price) {
        $('#update-price-route-id').val(price.routeId);
        $('#update-price-train-type').val(price.trainType);
        $('#update-price-basic-price-rate').val(price.basicPriceRate);
        $('#update-price-first-class-price-rate').val(price.firstClassPriceRate);

        $('#update-price-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseFloat($('#update-price-basic-price-rate').val()) && parseFloat($('#update-price-first-class-price-rate').val())) {
                    var data = new Object();
                    data.id = price.id;
                    data.routeId = $('#update-price-route-id').val();
                    data.trainType = $('#update-price-train-type').val();
                    data.basicPriceRate = parseFloat($('#update-price-basic-price-rate').val());
                    data.firstClassPriceRate = parseFloat($('#update-price-first-class-price-rate').val());
                    // alert(JSON.stringify(data));
                    $http({
                        method: "put",
                        url: "/api/v1/adminbasicservice/adminbasic/prices",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                        withCredentials: true,
                        data: data
                    }).success(function (data, status, headers, config) {
                        if (data.status ==1 ) {
                            alert("Update price successfully!");
                        } else {
                            alert("Update price failed!");
                        }
                        $window.location.reload();
                    }).error(function(data, header, config, status){
                        alert(data.message)
                    });
                } else {
                    alert("The basic price rate and the first class price rate must be a number!");
                }


            },
            onCancel: function () {

            }
        });
    };

    $scope.addPrice = function () {
        $('#add-price-route-id').val("");
        $('#add-price-train-type').val("");
        $('#add-price-basic-price-rate').val("");
        $('#add-price-first-class-price-rate').val("");

        $('#add-price-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseFloat($('#add-price-basic-price-rate').val()) && parseFloat($('#add-price-first-class-price-rate').val())) {
                    var data = new Object();
                    data.routeId = $('#add-price-route-id').val();
                    data.trainType = $('#add-price-train-type').val();
                    data.basicPriceRate = parseFloat($('#add-price-basic-price-rate').val());
                    data.firstClassPriceRate = parseFloat($('#add-price-first-class-price-rate').val());
                    // alert(JSON.stringify(data));
                    $http({
                        method: "post",
                        url: "/api/v1/adminbasicservice/adminbasic/prices",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                        withCredentials: true,
                        data: data
                    }).success(function (data, status, headers, config) {
                        if (data.status ==1) {
                            alert("Add Price successfully!");
                        } else {
                            alert("Add Price failed!");
                        }
                        $window.location.reload();
                    }).error(function(data, header, config, status){
                        alert(data.message)
                    });
                } else {
                    alert("The basic price rate and the first class price rate must be a number!");
                }
            },
            onCancel: function () {

            }
        });
    };


});