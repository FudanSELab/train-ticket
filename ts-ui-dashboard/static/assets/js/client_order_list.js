var appConsign = new Vue({
    el: '#orderListApp',
    data: {
        myOrderList: [],
        tempOrderList: [],
        statusExpress: ["Not Paid", "Paid & Not Collected", "Collected", "Cancel & Rebook", "Cancel", "Refunded", "Used", "other"],
        orderId: '',
        tripId: '',
        newTripId: '',
        price: '',
        from: "Shang Hai",
        to: "Su Zhou",
        selectedOrderId: '',
        oldTripId: '',
        status: -1,
        dateOfToday: '',
        trainTypeSelected: 1,
        options: [
            {text: 'All', value: 0},
            {text: 'GaoTie DongChe', value: 1},
            {text: 'Other', value: 2}
        ],
        selectSeatOptions: 2,
        selectedSeats: [],
        seatOptions: [
            {text: 'priceForEconomyClass', value: 2},
            {text: 'priceForConfortClass', value: 3}
        ],
        searchRoutes: [],
        tempSearchRoutes: [],
        differenceMoney: '0.0',
        consignName: '',
        consignPhone: '',
        consignWeight: '',
        consignId: '',
        vancher: {}
    },
    methods: {
        queryMyOrderList() {
            var myOrdersQueryInfo = new Object();

            myOrdersQueryInfo.loginId = sessionStorage.getItem("client_id");
            myOrdersQueryInfo.enableStateQuery = false;
            myOrdersQueryInfo.enableTravelDateQuery = false;
            myOrdersQueryInfo.enableBoughtDateQuery = false;
            myOrdersQueryInfo.travelDateStart = null;
            myOrdersQueryInfo.travelDateEnd = null;
            myOrdersQueryInfo.boughtDateStart = null;
            myOrdersQueryInfo.boughtDateEnd = null;

            this.myOrderList = [];
            var myOrdersQueryData = JSON.stringify(myOrdersQueryInfo);
            this.queryForMyOrderThree("/api/v1/orderservice/order/refresh", myOrdersQueryData);
            this.queryForMyOrderThree("/api/v1/orderOtherService/orderOther/refresh", myOrdersQueryData);
        },
        queryForMyOrderThree(path, data) {
            var that = this;
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                data: data,
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    console.log(result);
                    that.tempOrderList = [];
                    var size = result.data.length;
                    for (var i = 0; i < size; i++) {
                        that.tempOrderList[i] = result.data[i];
                        // that.tempOrderList[i].from = that.getStationNameById(that.tempOrderList[i].from);
                        // that.tempOrderList[i].to = that.getStationNameById(that.tempOrderList[i].to);
                        // that.tempOrderList[i].boughtDate = that.convertNumberToDateTimeString(that.tempOrderList[i].boughtDate)
                    }
                    that.myOrderList = that.myOrderList.concat(that.tempOrderList);
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                }
            });
        },
        getStationNameById(stationId) {
            var stationName;
            $.ajax({
                type: "get",
                url: "/api/v1/stationservice/stations/name/" + stationId,
                contentType: "application/json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                dataType: "json",
                async: false,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    stationName = result.data["name"];
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                }
            });
            return stationName;
        },
        payMyOrder(num, orderId, tripId, price) {
            this.orderId = orderId;
            this.tripId = tripId;
            this.price = price;
            var that = this;
            $('#my-prompt').modal({
                relatedTarget: this,
                onConfirm: function (e) {
                    $("#pay_for_preserve").attr("disabled", true);
                    var info = new Object();
                    info.orderId = that.orderId;
                    info.tripId = that.tripId;
                    var data = JSON.stringify(info);
                    $.ajax({
                        type: "post",
                        url: "/api/v1/inside_pay_service/inside_payment",
                        contentType: "application/json",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                        dataType: "json",
                        data: data,
                        xhrFields: {
                            withCredentials: true
                        },
                        success: function (result) {
                            if (result.status == 1) {
                                $("#preserve_collect_order_id").val(info.orderId);
                                alert("Success");
                                window.location.reload();
                            } else {
                                alert("Pay Fail. Reason Not Clear.Please check the order status before you try.");
                            }
                        }, error: function (e) {
                            var message = e.responseJSON.message;
                            console.log(message);
                            if (message.indexOf("Token") != -1) {
                                alert("Token is expired! please login first!");
                            }
                        },
                        complete: function () {
                            $("#pay_for_preserve").attr("disabled", false);
                        }
                    });
                },
                onCancel: function (e) {
                    // aalert('you hava canceled!');
                }
            });
        },
        cancelOrder(orderId, orderStatus) {
            if (orderStatus != 0 && orderStatus != 1 && orderStatus != 3) {
                alert("Order Can Not Be Cancel");
                return;
            }

            $("#ticket_cancel_order_id").text(orderId);

            $("#ticket_cancel_panel").css('display', 'block');
            $('#my-svg-change-order').shCircleLoader({namespace: 'runLoad'});
            $.ajax({
                type: "get",
                url: "/api/v1/cancelservice/cancel/refound/" + orderId,
                contentType: "application/json",
                dataType: "json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result["status"] == 1) {
                        $("#cancel_money_refund").text(result["refund"]);
                    } else {
                        $("#cancel_money_refund").text("Error ");
                    }
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                },
                complete: function () {
                    $('#my-svg-change-order').shCircleLoader('destroy');
                }
            });
        },
        reBook(index, type, number) {
            var $modal = $('#doc-modal-2');
            $modal.modal('close');
            var tripId = type + number;
            this.newTripId = tripId;
            var that = this;
            $('#my-prompt1').modal({
                relatedTarget: this,
                onConfirm: function (e) {
                    var rebookInfo = new Object();
                    rebookInfo.orderId = that.selectedOrderId;
                    rebookInfo.oldTripId = that.oldTripId;
                    rebookInfo.tripId = that.newTripId;
                    rebookInfo.seatType = that.selectedSeats[index];
                    rebookInfo.date = that.dateOfToday;
                    var data = JSON.stringify(rebookInfo);
                    $.ajax({
                        type: "post",
                        url: "/api/v1/rebookservice/rebook ",
                        contentType: "application/json",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                        dataType: "json",
                        data: data,
                        xhrFields: {
                            withCredentials: true
                        },
                        success: function (result) {
                            if (result["status"] == 1) {
                                alert(result["msg"]);
                            } else if (result["status"] == 2) {
                                // pay difference money
                                that.differenceMoney = result.data["differenceMoney"];
                                if (result.data['differenceMoney'] != null || result.data['differenceMoney'] != 'null') {
                                    $('#my-prompt2').modal({
                                        relatedTarget: this,
                                        onConfirm: function (e) {
                                            var rebookPayInfoData = data;
                                            $.ajax({
                                                type: "post",
                                                url: "/api/v1/rebookservice/rebook/difference",
                                                contentType: "application/json",
                                                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                                                dataType: "json",
                                                data: rebookPayInfoData,
                                                xhrFields: {
                                                    withCredentials: true
                                                },
                                                success: function (result) {
                                                    alert(result['msg']);
                                                    window.location.reload();
                                                },
                                                error: function (e) {
                                                    alert("unKnow payDifference error!")
                                                }
                                            });
                                        },
                                        onCancel: function (e) {
                                            // alert('you hava canceled!');
                                        }
                                    });
                                }
                            } else {
                                alert(result["msg"]);
                            }
                        },
                        error: function (e) {

                            var message = e.responseJSON.message;
                            console.log(message);
                            if (message.indexOf("Token") != -1) {
                                alert("Token is expired! please login first!");
                            } else {
                                alert("unKnow rebook error！")
                            }
                        }
                    });
                },
                onCancel: function (e) {
                    // alert('you hava canceled!');
                }
            });
        },
        onPay() {

            var orderId = $("#ticket_cancel_order_id").text();
            if (orderId == null || orderId == "") {
                alert(" Order ID that you want to cancel is Not Exists!.");
                return;
            }

            $.ajax({
                type: "get",
                url: "/api/v1/cancelservice/cancel/" + orderId + "/" + sessionStorage.getItem("client_id"),
                contentType: "application/json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                dataType: "json",
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result["status"] == 1) {
                        $("#ticket_cancel_panel").css('display', 'none');
                        alert(result["msg"]);
                    }

                    window.location.reload();
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                }
            });
        },
        initSeatClaass(size) {
            this.selectedSeats = new Array(size);
            for (var i = 0; i < size; i++)
                this.selectedSeats[i] = 2;
        },
        consignOrder(orderId, from, to, buyghtDate) {
            // 根据order Id 查询出consign
            this.queryConsignByOrderId(orderId);

            var that = this;
            $('#my-prompt-consign').modal({
                relatedTarget: this,
                onConfirm: function (e) {
                    var consignInfo = new Object();
                    consignInfo.accountId = sessionStorage.getItem("client_id");
                    var date = new Date();
                    var seperator1 = "-";
                    var year = date.getFullYear();
                    var month = date.getMonth() + 1;
                    var strDate = date.getDate();
                    if (month >= 1 && month <= 9) {
                        month = "0" + month;
                    }
                    if (strDate >= 0 && strDate <= 9) {
                        strDate = "0" + strDate;
                    }
                    var currentdate = year + seperator1 + month + seperator1 + strDate;

                    consignInfo.handleDate = currentdate;
                    consignInfo.targetDate = buyghtDate;
                    consignInfo.from = from;
                    consignInfo.to = to;
                    consignInfo.orderId = orderId;
                    consignInfo.consignee = that.consignName;

                    if (!that.checkNum(that.consignPhone)) {
                        alert('Please input a positive integer (phone)!')
                        return;
                    }

                    consignInfo.phone = that.consignPhone;
                    // weight must be a number
                    if (!that.checkNum(that.consignWeight)) {
                        alert('Please input a positive integer (weight)!')
                        return;
                    }

                    consignInfo.weight = that.consignWeight;
                    consignInfo.id = that.consignId;
                    consignInfo.isWithin = false;
                    var data = JSON.stringify(consignInfo);

                    $.ajax({
                        type: "put",
                        url: "/api/v1/consignservice/consigns",
                        contentType: "application/json",
                        headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                        dataType: "json",
                        data: data,
                        xhrFields: {
                            withCredentials: true
                        },
                        success: function (result) {
                            if (result["status"] == 1) {
                                alert(result["msg"]);
                            } else {
                                alert(result["msg"]);
                            }
                        }, error: function (e) {
                            var message = e.responseJSON.message;
                            console.log(message);
                            if (message.indexOf("Token") != -1) {
                                alert("Token is expired! please login first!");
                            }
                        }
                    });
                },
                onCancel: function (e) {
                    // alert('You have canceled!');
                }
            });
        },

        queryConsignByOrderId(orderId) {
            var that = this;
            $.ajax({
                type: "get",
                url: "/api/v1/consignservice/consigns/order/" + orderId,
                contentType: "application/json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                dataType: "json",
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result["status"] == 1) {
                        that.consignName = result.data.consignee;
                        that.consignPhone = result.data.phone;
                        that.consignWeight = result.data.weight;
                        that.consignId = result.data.id;
                    } else {
                        that.consignName = '';
                        that.consignPhone = '';
                        that.consignWeight = '';
                        that.consignId = '';
                    }
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                }
            });
        },

        changeMyOrder(from, to, status, selectedOrderId, oldTripId) {
            this.from = from;
            this.to = to;
            this.status = status;
            this.dateOfToday = this.calcauateToday();
            this.selectedOrderId = selectedOrderId;
            this.oldTripId = oldTripId;
        },
        searchRouteList() {
            var travelQueryInfo = new Object();
            travelQueryInfo.startPlace = this.from;
            travelQueryInfo.endPlace = this.to;
            travelQueryInfo.departureTime = this.dateOfToday;
            if (travelQueryInfo.departureTime == null || this.checkDateFormat(travelQueryInfo.departureTime) == false) {
                alert("Departure Date Format Wrong.");
                return;
            }
            var travelQueryData = JSON.stringify(travelQueryInfo);

            this.tempSearchRoutes = [];
            this.searchRoutes = [];

            if (this.trainTypeSelected == 0) {
                this.queryForTravelInfo(travelQueryData, "/api/v1/travel2service/trips/left");
                this.queryForRebookTravelInfo(travelQueryData, "/api/v1/travelservice/trips/left");
            }
            if (this.trainTypeSelected == 1) {
                this.queryForRebookTravelInfo(travelQueryData, "/api/v1/travelservice/trips/left");
            }
            if (this.trainTypeSelected == 2) {
                this.queryForTravelInfo(travelQueryData, "/api/v1/travel2service/trips/left");
            }
        },
        checkNum(num) {
            if (num == "") {
                return false;
            }
            if (!(/(^[1-9]\d*$)/.test(num))) {

                return false;
            } else {
                return true;
            }
        },
        checkDateFormat(date) {
            var dateFormat = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
            if (!dateFormat.test(date)) {
                return false;
            } else {
                return true;
            }
        },
        queryForTravelInfo(data, path) {
            $("#travel_booking_button").attr("disabled", true);
            $('#my-svg').shCircleLoader({namespace: 'runLoad'});

            var that = this;
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result["status"] == 1) {
                        var obj = result.data;
                        var size = obj.length;
                        that.tempSearchRoutes = obj;
                        // that.initSeatClass(size);
                        for (var i = 0; i < size; i++) {
                            that.tempSearchRoutes[i].startTime = that.convertNumberToTimeString(obj[i].startTime);
                            that.tempSearchRoutes[i].endTime = that.convertNumberToTimeString(obj[i].endTime);
                        }
                        that.searchRoutes = that.searchRoutes.concat(that.tempSearchRoutes);
                        that.initSeatClaass(that.searchRoutes.length);
                    }
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                },
                complete: function () {
                    $('#my-svg').shCircleLoader('destroy');
                    //  $("#travel_booking_button").attr("disabled", false);
                }
            });
        },
        queryForRebookTravelInfo(data, path) {
            var that = this;
            $('#my-svg').shCircleLoader({namespace: 'runLoad',});
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result["status"] == 1) {
                        var obj = result.data;
                        var size = obj.length;
                        for (var i = 0, l = obj.length; i < l; i++) {
                            that.tempSearchRoutes[i] = obj[i];
                            that.tempSearchRoutes[i].startTime = that.convertNumberToTimeString(that.tempSearchRoutes[i].startTime);
                            that.tempSearchRoutes[i].endTime = that.convertNumberToTimeString(that.tempSearchRoutes[i].endTime);
                        }
                        that.searchRoutes = that.searchRoutes.concat(that.tempSearchRoutes);
                        that.initSeatClaass(that.searchRoutes.length);
                    }
                }, error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                },
                complete: function () {
                    $('#my-svg').shCircleLoader('destroy');
                }
            });
        },
        printVancher(orderId, trainNum) {
            var requestInfo = new Object();
            requestInfo.orderId = orderId;
            var tripType = trainNum.charAt(0);
            if (tripType == 'G' || tripType == 'D') {
                requestInfo.type = 1;
            } else {
                requestInfo.type = 0;
            }
            var data = JSON.stringify(requestInfo);
            var that = this;
            //发送请求
            $.ajax({
                type: "post",
                url: "/getVoucher",
                contentType: "application/json",
                headers: {"Authorization": "Bearer " + sessionStorage.getItem("client_token")},
                dataType: "json",
                data: data,
                success: function (result) {
                    that.vancher = result;
                    that.vancher.travelDate = that.convertToYYYYMMDD(that.vancher.travelDate);
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
        },
        noPay() {
            $("#ticket_cancel_panel").css('display', 'none');
        },
        convertNumberToTimeString(timeNumber) {
            var str = new Date(timeNumber);
            var newStr = str.getHours() + ":" + str.getMinutes() + "";
            return newStr;
        },
        calcauateToday() {
            var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1; //January is 0!
            var yyyy = today.getFullYear();
            if (dd < 10) {
                dd = '0' + dd
            }
            if (mm < 10) {
                mm = '0' + mm
            }
            today = yyyy + '-' + mm + '-' + dd;
            return today;
        },
        convertToYYYYMMDD(timeNumber) {
            var date = new Date(Number(timeNumber));
            var year = date.getFullYear(),
                month = date.getMonth() + 1,
                day = date.getDate();
            var newTime = year + '-' +
                (month < 10 ? '0' + month : month) + '-' +
                (day < 10 ? '0' + day : day);
            return newTime;
        },
        convertNumberToDateTimeString(timeNumber) {
            var date = new Date(Number(timeNumber));
            var year = date.getFullYear(),
                month = date.getMonth() + 1,//月份是从0开始的
                day = date.getDate(),
                hour = date.getHours(),
                min = date.getMinutes(),
                sec = date.getSeconds();

            var newTime = year + '-' +
                (month < 10 ? '0' + month : month) + '-' +
                (day < 10 ? '0' + day : day) + ' ' +
                (hour < 10 ? '0' + hour : hour) + ':' +
                (min < 10 ? '0' + min : min) + ':' +
                (sec < 10 ? '0' + sec : sec);
            return newTime;
        },
        getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i].trim();
                if (c.indexOf(name) == 0)
                    return c.substring(name.length, c.length);
            }
            return "";
        }
    },
    mounted() {
        var username = sessionStorage.getItem("client_name");
        console.log("username: " + username);
        if (username == null || username == "Not Login") {

            location.href = "client_login.html";
        } else {
            document.getElementById("client_name").innerHTML = username;
            this.queryMyOrderList();
        }
    }
});