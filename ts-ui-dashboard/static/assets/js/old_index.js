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

    getOptionsData();
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
                        boughtDate: $('#add_order_bought_date').val(),
                        travelDate: $('#add_order_travel_date').val(),
                        travelTime:  $('#add_order_travel_time').val(),
                        accountId: $('#add_order_account').find("option:selected").val(),
                        contactsName: $('#add_order_passenger').find("option:selected").val(),
                        documentType: $('#add_order_document_type').find("option:selected").val(),
                        contactsDocumentNumber: $scope.add_order_document_number,
                        trainNumber: $('#add_order_train_number').find("option:selected").val(),
                        coachNumber: $scope.add_order_coach_number,
                        seatClass: $('#add_order_seat_class').find("option:selected").val(),
                        seatNumber: $scope.add_order_seat_number,
                        from: $scope.add_order_from,
                        to: $scope.add_order_to,
                        status: $('#add_order_status').find("option:selected").val(),
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

var bought_date_selector=$("#add_order_bought_date")
var travel_date_selector=$("#add_order_travel_date")
var travel_time_selector=$("#add_order_travel_time")



bought_date_selector.datetimepicker({
    format : 'yyyy-mm-dd hh:ii:00', // 展现格式
    autoclose : true, // 选择日期后关闭
    // 选择器打开之后首先显示的视图
    // 0表示分钟(默认),1表示小时,2表示天,3表示月,4表示年
    startView : 4,
    // 选择器所能够提供的最精确的时间选择视图
    // 0表示分钟(默认),1表示小时,2表示天,3表示月,4表示年
    minView : 0,
    minuteStep: 1
}).on('changeDate',function(ev){
    var datetimepicker=bought_date_selector.val();
    console.log(datetimepicker);
});
travel_date_selector.datetimepicker({
    format : 'yyyy-mm-dd hh:ii:00',
    autoclose : true,
    startView : 4,
    minView : 0,
    minuteStep: 1
}).on('changeDate',function(ev){
    var datetimepicker=travel_date_selector.val();
    console.log(datetimepicker);
});
travel_time_selector.datetimepicker({
    format : 'yyyy-mm-dd hh:ii:00',
    autoclose : true,
    startView : 4,
    minView : 0,
    minuteStep: 1
}).on('changeDate',function(ev){
    var datetimepicker=travel_time_selector.val();
    console.log(datetimepicker);
});

bought_date_selector.datetimepicker('setDate',new Date());
travel_date_selector.datetimepicker('setDate',new Date());
travel_time_selector.datetimepicker('setDate',new Date());


/**
 * get options data
 */


function getOptionsData(){
    getUserList();
    getTravelOptions();
}


function getUserList(){
    $.ajax({
        type: "get",
        url: "/api/v1/userservice/users",
        contentType: "application/json",
        dataType: "json",
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == 1) {
                var obj = result.data;
                var types = document.getElementById("add_order_account");
                //use data to build options
                for (var i = 0, l = obj.length; i < l; i++) {
                    var opt = document.createElement("option");
                    opt.value = obj[i]["userId"];
                    opt.innerText = obj[i]["userName"];
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

$("#add_order_account").on('change',function (){
    var e=document.getElementById("add_order_passenger");
    e.options.length=0;
    e.style.visibility="hidden";
    getContactOptions();
})

function getContactOptions(){
    var accountId=$('#add_order_account').find("option:selected").val();
    $.ajax({
        type: "get",
        url: "/api/v1/contactservice/contacts/account/"+accountId,
        contentType: "application/json",
        dataType: "json",
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == 1) {
                var obj = result.data;
                var types = document.getElementById("add_order_passenger");
                //display passenger options
                types.style.visibility="visible"
                //use data to build options
                for (var i = 0, l = obj.length; i < l; i++) {
                    var opt = document.createElement("option");
                    opt.value = obj[i]["id"];
                    opt.innerText = obj[i]["name"];
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

function getTravelOptions(){
    $.ajax({
        type: "get",
        url: "/api/v1/admintravelservice/admintravel",
        contentType: "application/json",
        dataType: "json",
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("admin_token")},
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == 1) {
                var obj = result.data;
                var types = document.getElementById("add_order_train_number");
                //use data to build options
                for (var i = 0, l = obj.length; i < l; i++) {
                    var opt = document.createElement("option");
                    opt.value = obj[i]["trip"]["tripId"]["type"]+obj[i]["trip"]["tripId"]["number"];
                    opt.innerText = obj[i]["trip"]["tripId"]["type"]+obj[i]["trip"]["tripId"]["number"];
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