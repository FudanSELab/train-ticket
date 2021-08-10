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

var contactsModule = angular.module("myApp", []);

contactsModule.factory('loadDataService', function ($http, $q) {

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
                alert("Request the order list fail!" + data.msg);
            }
        }).error(function(data, header, config, status){
            alert(data.message)
        });
        return promise;
    };

    return service;
});

contactsModule.controller("contactCtrl", function ($scope, $http, loadDataService, $window) {

    //首次加载显示数据
    loadDataService.loadAdminBasic("/api/v1/adminbasicservice/adminbasic/contacts").then(function (result) {
        $scope.contacts = result;
    });

    $scope.deleteContact = function (contact) {
        $('#delete-contact-confirm').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                // var msg = '你要删除的链接 ID 为 ' + contact.id;
                // alert(msg);
                $http({
                    method: "delete",
                    url: "/api/v1/adminbasicservice/adminbasic/contacts/" + contact.id,
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert("Delete contact successfully!");
                    } else {
                        alert(data.msg);
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

    $scope.updateContact = function (contact) {
        $('#update-contact-name').val(contact.name);
        $('#update-contact-document-type').val(contact.documentType);
        $('#update-contact-document-number').val(contact.documentNumber);
        $('#update-contact-phone-number').val(contact.phoneNumber);

        $('#update-contact-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                var data = new Object();
                data.id = contact.id;
                data.name = $('#update-contact-name').val();
                data.documentType = $('#update-contact-document-type').val();
                data.documentNumber = $('#update-contact-document-number').val();
                data.phoneNumber = $('#update-contact-phone-number').val();
                // alert(JSON.stringify(data));
                $http({
                    method: "put",
                    url: "/api/v1/adminbasicservice/adminbasic/contacts",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: data
                }).success(function (data, status, headers, config) {
                    if (data.status ==1 ) {
                        alert("Update contact successfully!");
                    } else {
                        alert(data.msg);
                    }
                    $window.location.reload();
                }).error(function(data, header, config, status){
                    alert(data.message)
                });
            },
            onCancel: function () {

            }
        });
    };

    $scope.addContact = function () {
        $('#add-contact-account-id').val("");
        $('#add-contact-name').val("");
        $('#add-contact-document-type').val("");
        $('#add-contact-document-number').val("");
        $('#add-contact-phone-number').val("");
        $('#add-contact-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseInt($('#add-contact-document-type').val())) {
                    var data = new Object();
                    data.accountId = $('#add-contact-account-id').val();
                    data.name = $('#add-contact-name').val();
                    data.documentType = $('#add-contact-document-type').val();
                    data.documentNumber = $('#add-contact-document-number').val();
                    data.phoneNumber = $('#add-contact-phone-number').val();
                    // alert(JSON.stringify(data));
                    $http({
                        method: "post",
                        url: "/api/v1/adminbasicservice/adminbasic/contacts",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                        withCredentials: true,
                        data: data
                    }).success(function (data, status, headers, config) {
                        if (data.status == 1) {
                            alert("Add contact successfully!");
                        } else {
                            alert(data.msg);
                        }
                        $window.location.reload();
                    }).error(function(data, header, config, status){
                        alert(data.message)
                    });
                } else {
                    alert("The documentType must be an integer!");
                }


            },
            onCancel: function () {
                // alert('算求，不弄了');
            }
        });
    };

});