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

var configModule = angular.module("myApp", []);

configModule.factory('loadDataService', function ($http, $q) {

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
                information = data;
                deferred.resolve(information);
            }
            else {
                alert("Request the configure list fail!" + data.msg);
            }
        }).error(function (data, header, config, status) {
            alert(data.message)
        });
        return promise;
    };

    return service;
});

configModule.controller("configCtrl", function ($scope, $http, loadDataService, $window) {

    //首次加载显示数据
    loadDataService.loadAdminBasic("/api/v1/adminbasicservice/adminbasic/configs").then(function (result) {
        console.log(result);
        $scope.configs = result.data;
    });

    $scope.deleteConfig = function (config) {
        $('#delete-config-confirm').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                $http({
                    method: "delete",
                    url: "/api/v1/adminbasicservice/adminbasic/configs/" + config.name,
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert("Delete config successfully!");
                    } else {
                        alert("Update config failed!");
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

    $scope.updateConfig = function (config) {
        $('#update-config-name').val(config.name);
        $('#update-config-value').val(config.value);
        $('#update-config-desc').val(config.description);

        $('#update-config-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                var data = new Object();
                data.name = $('#update-config-name').val();
                data.value = $('#update-config-value').val();
                data.description = $('#update-config-desc').val();
                // alert(JSON.stringify(data));
                $http({
                    method: "put",
                    url: "/api/v1/adminbasicservice/adminbasic/configs",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: data
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert("Update configure successfully!");
                    } else {
                        alert("Update configure failed!");
                    }
                    $window.location.reload();
                }).error(function (data, header, config, status) {
                    alert(data.message)
                });

            },
            onCancel: function () {

            }
        });
    };

    $scope.addConfig = function () {
        $('#add-config-name').val("");
        $('#add-config-value').val("");
        $('#add-config-desc').val("");

        $('#add-config-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                var data = new Object();
                data.name = $('#add-config-name').val();
                data.value = $('#add-config-value').val();
                data.description = $('#add-config-desc').val();
                // alert(JSON.stringify(data));
                $http({
                    method: "post",
                    url: "/api/v1/adminbasicservice/adminbasic/configs",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: data
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert("Add Configure successfully!");
                    } else {
                        alert("Add Configure failed!");
                    }
                    $window.location.reload();
                }).error(function (data, header, config, status) {
                    alert(data.message)
                });

            },
            onCancel: function () {

            }
        });
    };
});