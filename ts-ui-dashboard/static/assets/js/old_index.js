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
            url: "/api/v1/adminorderservice/adminorder",
            headers: {"Authorization": "Bearer " + param.admin_token},
            withCredentials: true,
        }).success(function (data, status, headers, config) {
            if (data.status == 1) {
                console.log(data);
                information.orderRecords = data.data;
                deferred.resolve(information);
            }
            else {
                alert("Request the order list fail!" + data.message);
            }
        }).error(function (data, header, config, status) {
            alert(data.message)
        });

        return promise;
    };

    return service;
});

/*
 * 加载列表
 * */
app.controller('indexCtrl', function ($scope, $http, $window, loadDataService) {
    var param = {};
    param.admin_token = sessionStorage.getItem("admin_token");

    //刷新页面
    $scope.reloadRoute = function () {
        $window.location.reload();
    };

    //首次加载显示数据
    loadDataService.loadRecordList(param).then(function (result) {
        $scope.records = result.orderRecords;
        //$scope.decodeInfo(result.orderRecords[0]);
    });

    $scope.decodeInfo = function (obj) {
        var des = "";
        for (var name in obj) {
            des += name + ":" + obj[name] + ";";
        }
        alert(des);
    }

    //Add new order
    $scope.addNewOrder = function () {
        $('#add_prompt').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                $http({
                    method: "post",
                    url: "/api/v1/adminorderservice/adminorder",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: {
                        boughtDate: $scope.add_order_bought_date,
                        travelDate: $scope.add_order_travel_date,
                        travelTime: $scope.add_order_travel_time,
                        accountId: $scope.add_order_account,
                        contactsName: $scope.add_order_passenger,
                        documentType: $scope.add_order_document_type,
                        contactsDocumentNumber: $scope.add_order_document_number,
                        trainNumber: $scope.add_order_train_number,
                        coachNumber: $scope.add_order_coach_number,
                        seatClass: $scope.add_order_seat_class,
                        seatNumber: $scope.add_order_seat_number,
                        from: $scope.add_order_from,
                        to: $scope.add_order_to,
                        status: $scope.add_order_status,
                        price: $scope.add_order_price
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else {
                        alert("Request the order list fail!" + data.msg);
                    }
                }).error(function (data, header, config, status) {
                    alert(data.message)
                });
            },
            onCancel: function (e) {
                alert('You have canceled the operation!');
            }
        });
    }

    //Update exist order
    $scope.updateOrder = function (record) {
        $scope.update_order_id = record.id;
        $scope.update_order_bought_date = record.boughtDate;
        $scope.update_order_travel_date = record.travelDate;
        $scope.update_order_travel_time = record.travelTime;
        $scope.update_order_account = record.accountId;
        $scope.update_order_passenger = record.contactsName;
        $scope.update_add_order_document_type = record.documentType;
        $scope.update_order_document_number = record.contactsDocumentNumber;
        $scope.update_order_train_number = record.trainNumber;
        $scope.update_order_coach_number = record.coachNumber;
        $scope.update_order_seat_class = record.seatClass;
        $scope.update_order_seat_number = record.seatNumber;
        $scope.update_order_from = record.from;
        $scope.update_order_to = record.to;
        $scope.update_order_status = record.status;
        $scope.update_order_price = record.price;

        $('#update_prompt').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                $http({
                    method: "put",
                    url: "/api/v1/adminorderservice/adminorder",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: {

                        id: $scope.update_order_id,
                        boughtDate: $scope.update_order_bought_date,
                        travelDate: $scope.update_order_travel_date,
                        travelTime: $scope.update_order_travel_time,
                        accountId: $scope.update_order_account,
                        contactsName: $scope.update_order_passenger,
                        documentType: $scope.update_add_order_document_type,
                        contactsDocumentNumber: $scope.update_order_document_number,
                        trainNumber: $scope.update_order_train_number,
                        coachNumber: $scope.update_order_coach_number,
                        seatClass: $scope.update_order_seat_class,
                        seatNumber: $scope.update_order_seat_number,
                        from: $scope.update_order_from,
                        to: $scope.update_order_to,
                        status: $scope.update_order_status,
                        price: $scope.update_order_price

                    }
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else {
                        alert("Request the order list fail!" + data.msg);
                    }
                }).error(function (data, header, config, status) {
                    alert(data.message)
                });
            },
            onCancel: function (e) {
                alert('You have canceled the operation!');
            }
        });
    }

    //Delete order
    $scope.deleteOrder = function (orderId, trainNumber) {
        $('#delete_confirm').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                $http({
                    method: "delete",
                    url: "/api/v1/adminorderservice/adminorder/" + orderId + "/" + trainNumber,
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else {
                        alert("Request the order list fail!" + data.msg);
                    }
                }).error(function (data, header, config, status) {
                    alert(data.message)
                });
            },
            // closeOnConfirm: false,
            onCancel: function () {
                alert('You have canceled the operation!');
            }
        });
    }
});