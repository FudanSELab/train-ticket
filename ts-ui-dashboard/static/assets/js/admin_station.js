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
                    url: "/api/v1/adminbasicservice/adminbasic/stations/"+station.id,
                    headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
                    withCredentials: true,
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
        $('#add-station-name').val("");
        $('#add-station-stay-time').val("");

        $('#add-station-table').modal({
            relatedTarget: this,
            onConfirm: function (options) {
                if (parseInt($('#add-station-stay-time').val())) {
                    var data = new Object();
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