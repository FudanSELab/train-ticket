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
            url: "/api/v1/admintravelservice/admintravel",
            headers: {"Authorization": "Bearer " + param.admin_token},
            withCredentials: true,
        }).success(function (data, status, headers, config) {
            if (data.status == 1) {
                information.travelRecords = data.data;
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
        $scope.records = result.travelRecords;
        console.log(result.travelRecords)
        //$scope.decodeInfo(result.orderRecords[0]);
    });

    $scope.decodeInfo = function (obj) {
        var des = "";
        for (var name in obj) {
            des += name + ":" + obj[name] + ";";
        }
        alert(des);
    }

    //init optional table item
    getOptionsData();

    //Add new travel
    $scope.addNewTravel = function () {
        $('#add_prompt').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                $http({
                    method: "post",
                    url: "/api/v1/admintravelservice/admintravel",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: {
                        tripId: $scope.add_travel_id,
                        trainTypeName: $('#add_travel_train_type_id').find("option:selected").val(),
                        routeId: $('#add_travel_route_id').find("option:selected").val(),
                        startStationName: $('#add_travel_start_station').find("option:selected").val(),
                        stationsName: $('#add_travel_station_name').find("option:selected").val(),
                        terminalStationName: $('#add_travel_terminal_station').find("option:selected").val(),
                        startTime: $('#add_travel_start_time').val(),
                        endTime: $('#add_travel_end_time').val(),
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status) {
                        alert(data.status + data.msg);
                        $scope.reloadRoute();
                    }
                    else {
                        alert(data.status + data.msg);
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

    //Update exist travel
    $scope.updateTravel = function (record) {
        $scope.update_travel_id = record.trip.tripId.type + "" + record.trip.tripId.number;
        $scope.update_travel_train_type_id = record.trip.trainTypeId;
        $scope.update_travel_route_id = record.trip.routeId;
        $scope.update_travel_start_station = record.trip.startStationName;
        $scope.update_travel_station_name = record.trip.stationsName;
        $scope.update_travel_terminal_station = record.trip.terminalStationName;
        $scope.update_travel_start_time = record.trip.startTime;
        $scope.update_travel_end_time = record.trip.endTime;

        $("#update_travel_start_time").datetimepicker('setDate',new Date($scope.update_travel_start_time));
        $("#update_travel_end_time").datetimepicker('setDate',new Date($scope.update_travel_end_time));

        $('#update_prompt').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                $http({
                    method: "put",
                    url: "/api/v1/admintravelservice/admintravel",
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
                    data: {
                        tripId: $scope.update_travel_id,
                        trainTypeName: $('#update_travel_train_type_id').find("option:selected").val(),
                        routeId: $scope.update_travel_route_id,
                        startStationName: $scope.update_travel_start_station,
                        stationsName: $scope.update_travel_station_name,
                        terminalStationName: $scope.update_travel_terminal_station,
                        startTime: $('#update_travel_start_time').val(),
                        endTime: $('#update_travel_end_time').val()
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else {
                        alert(data.msg);
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

    //Delete travel
    $scope.deleteTravel = function (travelId) {
        var tripId = travelId.type + "" + travelId.number;
        $('#delete_confirm').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                $http({
                    method: "delete",
                    url: "/api/v1/admintravelservice/admintravel/" + tripId,
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true
                }).success(function (data, status, headers, config) {
                    if (data.status == 1) {
                        alert(data.msg);
                        $scope.reloadRoute();
                    }
                    else {
                        alert(data.msg);
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

function getOptionsData(){
    getTrainTypes();
    getRouteList();
    getStationList();
}

function getTrainTypes(){
    $.ajax({
        type: "get",
        url: "/api/v1/trainservice/trains",
        contentType: "application/json",
        dataType: "json",
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == 1) {
                var obj = result.data;
                var add_travel_train_type = document.getElementById("add_travel_train_type_id");
                var update_travel_train_type = document.getElementById("update_travel_train_type_id");
                //use data to build options
                for (var i = 0, l = obj.length; i < l; i++) {
                    var opt = document.createElement("option");
                    opt.value = obj[i]["name"];
                    opt.innerText = obj[i]["name"];
                    add_travel_train_type.appendChild(opt);
                    update_travel_train_type.appendChild(opt.cloneNode(true));
                }
            } else {
                alert(result.msg);
            }
        }, error: function (e) {
            var message = e.responseJSON.message;
            console.log(message);
            if (message.indexOf("Token") != -1) {
                alert("Token is expired! please login first!");
            }
        },
        complete: function () {

        }
    });
}

function getRouteList(){
    $.ajax({
        type: "get",
        url: "/api/v1/routeservice/routes",
        contentType: "application/json",
        dataType: "json",
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == 1) {
                var obj = result.data;
                var types = document.getElementById("add_travel_route_id");
                //use data to build options
                for (var i = 0, l = obj.length; i < l; i++) {
                    var opt = document.createElement("option");
                    opt.value = obj[i]["id"];
                    opt.innerText = obj[i]["id"];
                    types.appendChild(opt);
                }
            } else {
                alert(result.msg);
            }
        }, error: function (e) {
            var message = e.responseJSON.message;
            console.log(message);
            if (message.indexOf("Token") != -1) {
                alert("Token is expired! please login first!");
            }
        },
        complete: function () {

        }
    });
}

function getStationList(){
    $.ajax({
        type: "get",
        url: "/api/v1/stationservice/stations",
        contentType: "application/json",
        dataType: "json",
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == 1) {
                var obj = result.data;
                var start_station = document.getElementById("add_travel_start_station");
                var terminal_station = document.getElementById("add_travel_terminal_station")
                var station_name = document.getElementById("add_travel_station_name")
                //use data to build options
                for (var i = 0, l = obj.length; i < l; i++) {
                    var opt = document.createElement("option");
                    opt.value = obj[i]["name"];
                    opt.innerText = obj[i]["name"];
                    start_station.appendChild(opt);
                    station_name.appendChild(opt.cloneNode(true));
                    terminal_station.appendChild(opt.cloneNode(true));
                }
            } else {
                alert(result.msg);
            }
        }, error: function (e) {
            var message = e.responseJSON.message;
            console.log(message);
            if (message.indexOf("Token") != -1) {
                alert("Token is expired! please login first!");
            }
        },
        complete: function () {

        }
    });
}

var add_travel_start_time=$("#add_travel_start_time")
var add_travel_end_time=$("#add_travel_end_time")
var update_travel_start_time=$("#update_travel_start_time")
var update_travel_end_time=$("#update_travel_end_time")

add_travel_start_time.datetimepicker({
    format : 'yyyy-mm-dd hh:ii:00',
    autoclose : true,
    startView : 4,
    minView : 0,
    minuteStep: 1
});

add_travel_end_time.datetimepicker({
    format : 'yyyy-mm-dd hh:ii:00',
    autoclose : true,
    startView : 4,
    minView : 0,
    minuteStep: 1
});

update_travel_start_time.datetimepicker({
    format : 'yyyy-mm-dd hh:ii:00',
    autoclose : true,
    startView : 4,
    minView : 0,
    minuteStep: 1
});

update_travel_end_time.datetimepicker({
    format : 'yyyy-mm-dd hh:ii:00',
    autoclose : true,
    startView : 4,
    minView : 0,
    minuteStep: 1
});

add_travel_start_time.datetimepicker('setDate',new Date());
add_travel_end_time.datetimepicker('setDate',new Date());

function parseTime(timeNumber){
    var temp=timeNumber.split('T');
    var date=temp[0];
    var moment=temp[1].split('.')[0];
    var result= date+ ' '+moment
    return result;
}