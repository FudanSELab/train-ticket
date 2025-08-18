var loginApp = new Vue({
    el: '#loginApp',
    data: {
        userName: 'fdse_microservice',
        password: '111111',
        verifiCode: '1234'
    },
    methods: {
        initPage() {
            this.checkIfLogin();
        },
        checkIfLogin() {
            var username = sessionStorage.getItem("client_name");
            if (username == null || username == 'Not Login') {
                alert("Please login first!");
            }
            else {
                document.getElementById("client_name").innerHTML = username;
            }
        },
        reloadYZM() {
            console.log("brush verification code")
            document.getElementById("flow_preserve_login_verification_code_img").src = "/api/v1/auth/verifycode/generate?" +Math.random();
        },
        login() {
            var loginInfo = new Object();
            loginInfo.username = this.userName;
            if (loginInfo.username == null || loginInfo.username == "") {
                alert("UserName Can Not Be Empty.");
                return;
            }

            loginInfo.password = this.password;
            if (loginInfo.password == null || loginInfo.password == "") {
                alert("Password Can Not Be Empty.");
                return;
            }
            loginInfo.verificationCode = this.verifiCode;
            if (loginInfo.verificationCode == null || loginInfo.verificationCode == "") {
                alert("Verification Code Can Not Be Empty.");
                return;
            }

            var data = JSON.stringify(loginInfo);
            $.ajax({
                type: "post",
                url: "/api/v1/auth/users/login",
                contentType: "application/json",
                dataType: "json",
                data: data,

                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    var obj = result;

                    if (obj["status"] == 1) {
                        sessionStorage.setItem("client_token", obj["data"].token);
                        sessionStorage.setItem("client_id", obj["data"].userId);
                        sessionStorage.setItem("client_name", obj["data"].username);
                        document.getElementById("client_name").innerHTML = obj["data"].username;
                        $("#flow_preserve_login_status").text(obj["status"]);
                        $("#flow_preserve_login_msg").text(obj["msg"]);
                    } else {
                        // alert(obj["message"]);
                        sessionStorage.setItem("client_token", "-1");
                        sessionStorage.setItem("client_name", "Not Login");
                        document.getElementById("client_name").innerHTML = "Not Login";
                        $("#flow_preserve_login_msg").text(obj["msg"]);
                    }
                }
            });
        }
    },
    mounted() {
        this.initPage();
    }
});