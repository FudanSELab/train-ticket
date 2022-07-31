var adSearch = new Vue({
    el: '#adSearch',
    data: {
        startPlace: 'Nan Jing',
        treminalPlace: 'Shang Hai',
        selectedDate: '',
        selectedSearchType: 1,
        typeOptions: [
            {text: 'Minimum Station Number', value: 0},
            {text: 'Cheapest', value: 1},
            {text: 'Quickest', value: 2}
        ],
        adTicketSearchResult: [],
        selectedSeats: []

    },
    methods: {
        initPage() {
            this.selectedDate = this.calcauateToday();
            this.checkLogin();
        },
        checkLogin() {
            var username = sessionStorage.getItem("client_name");
            if (username == null || username == "Not Login") {
                // alert("Please login first!");
                // location.href = "client_login.html";
            }
            else {
                document.getElementById("client_name").innerHTML = username;
            }
        },
        adSearchPath() {
            var advanceSearchInfo = new Object();
            advanceSearchInfo.startPlace = this.startPlace;
            advanceSearchInfo.endPlace = this.treminalPlace;
            advanceSearchInfo.departureTime = this.selectedDate;
            if (advanceSearchInfo.departureTime == null || this.checkDateFormat(advanceSearchInfo.departureTime) == false) {
                alert("Departure Date Format Wrong.");
                return;
            }
            // add hh:mm:ss to date
            advanceSearchInfo.departureTime = this.selectedDate + " 00:00:00";
            var advanceSearchData = JSON.stringify(advanceSearchInfo);

            var selectType = this.selectedSearchType;
            if (selectType == 0) {
                this.advanceSearchForMinStopInfo(advanceSearchData, "/api/v1/travelplanservice/travelPlan/minStation");
            } else if (selectType == 1) {
                this.advanceSearchForCheapestInfo(advanceSearchData, "/api/v1/travelplanservice/travelPlan/cheapest");
            } else if (selectType == 2) {
                this.advanceSearchForQuickestInfo(advanceSearchData, "/api/v1/travelplanservice/travelPlan/quickest");
            } else {
                alert("Select Search Type Wrong");
            }

        },
        advanceSearchForMinStopInfo(data, path) {
            var that = this;
            $("#ad_search_booking_button").attr("disabled", true);
            $('#my-svg').shCircleLoader({namespace: 'runLoad',});
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result.status = 1) {
                        var obj = result.data;
                        that.adTicketSearchResult = obj;
                        that.initSeatClaass(obj.length);
                        for (var i = 0, l = obj.length; i < l; i++) {
                            that.adTicketSearchResult[i].startTime = that.flow_advance_convertNumberToTimeString(obj[i]["startTime"]);
                            that.adTicketSearchResult[i].endTime = that.flow_advance_convertNumberToTimeString(obj[i]["endTime"]);
                        }
                    }
                },error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                },
                complete: function () {
                    $('#my-svg').shCircleLoader('destroy');
                    $("#ad_search_booking_button").attr("disabled", false);
                }
            });
        },
        initSeatClaass(size) {
            this.selectedSeats = new Array(size);
            for (var i = 0; i < size; i++)
                this.selectedSeats[i] = 2;
        },
        advanceSearchForCheapestInfo(data, path) {
            $("#ad_search_booking_button").attr("disabled", true);
            $('#my-svg').shCircleLoader({namespace: 'runLoad',});
            var that = this;
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result.status == 1) {
                        var obj = result.data;
                        that.adTicketSearchResult = obj;
                        that.initSeatClaass(obj.length);
                        for (var i = 0; i < obj.length; i++) {
                            that.adTicketSearchResult[i].startTime = that.flow_advance_convertNumberToTimeString(obj[i]["startTime"]);
                            that.adTicketSearchResult[i].endTime = that.flow_advance_convertNumberToTimeString(obj[i]["endTime"]);
                        }
                    }
                },error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                },
                complete: function () {
                    $('#my-svg').shCircleLoader('destroy');
                    $("#ad_search_booking_button").attr("disabled", false);
                }
            });
        },
        advanceSearchForQuickestInfo(data, path) {
            $("#ad_search_booking_button").attr("disabled", true);
            $('#my-svg').shCircleLoader({namespace: 'runLoad',});
            var that = this;
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result.status == 1) {
                        var obj = result.data;
                        that.adTicketSearchResult = obj;
                        that.initSeatClaass(obj.length);
                        for (var i = 0, l = obj.length; i < l; i++) {
                            that.adTicketSearchResult[i].startTime = that.flow_advance_convertNumberToTimeString(obj[i]["startTime"]);
                            that.adTicketSearchResult[i].endTime = that.flow_advance_convertNumberToTimeString(obj[i]["endTime"]);
                        }
                     //   flow_advance_addListenerToBookingTable();
                    }
                },error: function (e) {
                    var message = e.responseJSON.message;
                    console.log(message);
                    if (message.indexOf("Token") != -1) {
                        alert("Token is expired! please login first!");
                    }
                },
                complete: function () {
                    $('#my-svg').shCircleLoader('destroy');
                    $("#ad_search_booking_button").attr("disabled", false);
                }
            });
        },
        adBooking(index, tripId, from, to) {
            var seatPrice = "";
            if (this.selectedSeats[index] == 2 || this.selectedSeats[index] == '2')
                seatPrice = this.adTicketSearchResult[index].priceForFirstClassSeat;
            else
                seatPrice = this.adTicketSearchResult[index].priceForSecondClassSeat;
            location.href = "client_ticket_book.html?tripId=" + tripId + "&from=" + from + "&to=" + to + "&seatType=" + this.selectedSeats[index] + "&seat_price=" + seatPrice + "&date=" + this.selectedDate;
        },
        checkDateFormat(date) {
            var dateFormat = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
            if (!dateFormat.test(date)) {
                return false;
            } else {
                return true;
            }
        },
        flow_advance_convertNumberToTimeString(timeNumber) {
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
        }
    },
    mounted() {
        this.initPage();
    }
});