
var reserveApp = new Vue({
    el: '#reserveApp',
    data: {
        from: 'Shang Hai',
        to: 'Su Zhou',
        selectedDate: '',
        selectedTrainType: 1,
        trainTypes: [
            {text: 'All', value: 0},
            {text: 'GaoTie DongChe', value: 1},
            {text: 'Other', value: 2}
        ],
        travelList: [],
        tempTravelList: [],
        selectedSeats: [],
        email: 'fdse_microservice@163.com',
        password: '111111',
        verifyCode: '1234'
    },
    methods: {
        initPage() {
            this.setTodayDatePreserve();
            this.checkLogin();
        },
        setTodayDatePreserve() {
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
            this.selectedDate = today;
            document.getElementById("travel_booking_date").setAttribute("min", today);
        },
        checkLogin() {
            var username = sessionStorage.getItem("client_name");
            if (username == null) {
                // alert("Please login first!");

            }
            else {
                document.getElementById("client_name").innerHTML = username;
            }
        },
        logOutClient() {
            var logoutInfo = new Object();
            logoutInfo.id = this.getCookie("loginId");
            if (logoutInfo.id == null || logoutInfo.id == "") {
                alert("No cookie named 'loginId' exist. please login");
                location.href = "client_login.html";
                return;
            }
            logoutInfo.token = this.getCookie("loginToken");
            if (logoutInfo.token == null || logoutInfo.token == "") {
               // alert("No cookie named 'loginToken' exist.  please login");
                location.href = "client_login.html";
                return;
            }
            var data = JSON.stringify(logoutInfo);
            var that = this;
            $.ajax({
                type: "post",
                url: "/logout",
                contentType: "application/json",
                dataType: "json",
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    if (result["status"] == true) {
                        that.setCookie("loginId", "", -1);
                        that.setCookie("loginToken", "", -1);
                    } else if (result["message"] == "Not Login") {
                        that.setCookie("loginId", "", -1);
                        that.setCookie("loginToken", "", -1);
                    }
                    sessionStorage.setItem("client_id", "-1");
                    sessionStorage.setItem("client_name", "Not Login");
                    document.getElementById("client_name").innerHTML = "Not Login";
                    location.href = "client_login.html";
                },
                error: function (e) {
                    alert("logout error");
                }
            });
        },
        initSeatClass(size) {
            this.selectedSeats = new Array(size);
            for (var i = 0; i < size; i++)
                this.selectedSeats[i] = 2;
        },
        searchTravel() {
            var travelQueryInfo = new Object();
            travelQueryInfo.startPlace = this.from;
            travelQueryInfo.endPlace = this.to;
            travelQueryInfo.departureTime = this.selectedDate;
            if (travelQueryInfo.departureTime == null || this.checkDateFormat(travelQueryInfo.departureTime) == false) {
                alert("Departure Date Format Wrong.");
                return;
            }
            // add hh:mm:ss to date
            travelQueryInfo.departureTime = this.selectedDate + " 00:00:00";
            var travelQueryData = JSON.stringify(travelQueryInfo);
            var train_type = this.selectedTrainType;
            this.tempTravelList = [];
            this.travelList =[];

            if (train_type == 0) {
                this.queryForTravelInfo(travelQueryData, "/api/v1/travelservice/trips/left");
                this.queryForTravelInfo(travelQueryData, "/api/v1/travel2service/trips/left");
            } else if (train_type == 1) {

                this.queryForTravelInfo(travelQueryData, "/api/v1/travelservice/trips/left");
            } else if (train_type == 2) {
                this.queryForTravelInfo(travelQueryData, "/api/v1/travel2service/trips/left");
            }

        },
        queryForTravelInfo(data, path) {
            $("#travel_booking_button").attr("disabled", true);
            var that = this;
            $('#my-svg').shCircleLoader({namespace: 'runLoad'});
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
                    if (result.status ==1) {
                        var obj = result.data;
                        var size = obj.length;
                        that.tempTravelList = obj;
                        that.initSeatClass(size);
                        for (var i = 0; i < size; i++) {
                            that.tempTravelList[i].startTime = that.convertNumberToTimeString(obj[i].startTime);
                            that.tempTravelList[i].endTime = that.convertNumberToTimeString(obj[i].endTime);
                        }
                        that.travelList = that.travelList.concat(that.tempTravelList);
                    }
                },
                complete: function () {
                    $('#my-svg').shCircleLoader('destroy');
                    $("#travel_booking_button").attr("disabled", false);
                }
            });
        },
        preserverBooking(index, tripType, tripNum, from, to) {
            var tripId = tripType + tripNum;
            var seatPrice = "0.0";
            if (this.selectedSeats[index] == 2)
                seatPrice = this.travelList[index].priceForConfortClass;
            else if (this.selectedSeats[index] == 3) {
                seatPrice = this.travelList[index].priceForEconomyClass;
            }
            var that = this;
            // if (sessionStorage.getItem("client_id") == "-1" || sessionStorage.getItem("client_id") == null) {
                // login window
            //     $('#my-prompt').modal({
            //         relatedTarget: this,
            //         onConfirm: function (e) {
            //             var loginInfo = new Object();
            //             loginInfo.email = e.data[0];
            //             if (loginInfo.email == null || loginInfo.email == "") {
            //                 alert("Email Can Not Be Empty.");
            //                 return;
            //             }
            //             if (that.checkEmailFormat(loginInfo.email) == false) {
            //                 alert("Email Format Wrong.");
            //                 return;
            //             }
            //
            //             loginInfo.password = e.data[1];
            //             if (loginInfo.password == null || loginInfo.password == "") {
            //                 alert("Password Can Not Be Empty.");
            //                 return;
            //             }
            //             loginInfo.verificationCode = e.data[2];
            //             if (loginInfo.verificationCode == null || loginInfo.verificationCode == "") {
            //                 alert("Verification Code Can Not Be Empty.");
            //                 return;
            //             }
            //
            //             var data = JSON.stringify(loginInfo);
            //             $.ajax({
            //                 type: "post",
            //                 url: "/login",
            //                 contentType: "application/json",
            //                 dataType: "json",
            //                 data: data,
            //                 xhrFields: {
            //                     withCredentials: true
            //                 },
            //                 success: function (result) {
            //                     var obj = result;
            //                     if (obj["status"] == true) {
            //                         sessionStorage.setItem("client_id", obj["account"].id);
            //                         sessionStorage.setItem("client_name", obj["account"].name);
            //                         document.cookie = "loginId=" + obj["account"].id;
            //                         document.cookie = "loginToken=" + obj["token"];
            //                         document.getElementById("client_name").innerHTML = obj["account"].name;
            //                         location.href = "client_ticket_book.html?tripId=" + tripId + "&from=" + from + "&to=" + to + "&seatType=" +
            //                             that.selectedSeats[index] + "&seat_price=" + seatPrice + "&date=" + that.selectedDate;
            //
            //                     } else {
            //                         alert(obj["message"]);
            //                     }
            //                 },
            //                 error: function (e) {
            //                     alert("login error, please login again!");
            //                 }
            //             });
            //         },
            //         onCancel: function (e) {
            //             // alert('cancel!');
            //         }
            //     });
            // } else {
            //     location.href = "client_ticket_book.html?tripId=" + tripId + "&from=" + from + "&to=" + to + "&seatType=" +
            //         this.selectedSeats[index] + "&seat_price=" + seatPrice + "&date=" + this.selectedDate;
            // }
            location.href = "client_ticket_book.html?tripId=" + tripId + "&from=" + from + "&to=" + to + "&seatType=" +
                this.selectedSeats[index] + "&seat_price=" + seatPrice + "&date=" + this.selectedDate;
        },
        checkDateFormat(date) {
            var dateFormat = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
            if (!dateFormat.test(date)) {
                return false;
            } else {
                return true;
            }
        },
        convertNumberToTimeString(timeNumber) {
            var str = new Date(timeNumber);
            var newStr = str.getHours() + ":" + str.getMinutes() + "";
            return newStr;
        },
        login() {
            var loginInfo = new Object();
            loginInfo.email = this.email;
            if (loginInfo.email == null || loginInfo.email == "") {
                alert("Email Can Not Be Empty.");
                return;
            }
            if (this.checkEmailFormat(loginInfo.email) == false) {
                alert("Email Format Wrong.");
                return;
            }
            loginInfo.password = this.password;
            if (loginInfo.password == null || loginInfo.password == "") {
                alert("Password Can Not Be Empty.");
                return;
            }
            loginInfo.verificationCode = this.verifyCode;
            if (loginInfo.verificationCode == null || loginInfo.verificationCode == "") {
                alert("Verification Code Can Not Be Empty.");
                return;
            }
            var data = JSON.stringify(loginInfo);
            $.ajax({
                type: "post",
                url: "/login",
                contentType: "application/json",
                dataType: "json",
                data: data,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    var obj = result;
                    if (obj["status"] == true) {
                        sessionStorage.setItem("client_id", obj["account"].id);
                        sessionStorage.setItem("client_name", obj["account"].name);
                        document.cookie = "loginId=" + obj["account"].id;
                        document.cookie = "loginToken=" + obj["token"];
                        document.getElementById("client_name").innerHTML = obj["account"].name;
                        //  alert(obj["message"] + obj["account"].name + "======-");
                        // login in success
                    } else {
                        setCookie("loginId", "", -1);
                        setCookie("loginToken", "", -1);
                        sessionStorage.setItem("client_id","-1");
                        sessionStorage.setItem("client_name", "Not Login");
                        document.getElementById("client_name").innerHTML = "Not Login";
                    }
                }
            });
        },
        checkEmailFormat(email) {
            var emailFormat = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if (!emailFormat.test(email)) {
                return false;
            } else {
                return true;
            }
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
        },
        setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toUTCString();
            document.cookie = cname + "=" + cvalue + "; " + expires;
        }
    },
    mounted() {
        this.initPage();
    }
});