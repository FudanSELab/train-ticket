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
var controllerModule = angular.module("myApp", []);
controllerModule.controller("loginCtrl", function ($scope,$http) {
    $scope.login = function() {
        var username = $scope.username;
        var password = $scope.password;
        $http({
            method:"post",
            url: "/api/v1/users/login",
            withCredentials: true,
            data:{
                username: username,
                password: password
            }
        }).success(function(data, status, headers, config){
            if (data.status == 1) {
                sessionStorage.setItem("admin_name", data.data.username);
                sessionStorage.setItem("admin_token", data.data.token);
                location.href = "../../admin.html";
            }else{
                alert("Wrong user name and password!");
            }
        }).error(function(data, header, config, status){
            alert(data.message)
        });
    }

    $scope.decodeInfo = function (obj) {
        var des = "";
        for(var name in obj){
            des += name + ":" + obj[name] + ";";
        }
        alert(des);
    }
});