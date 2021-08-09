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

function onLoadBody(){
    //请求信息封装
    var url = location.search;
    var request = new Object();
    if(url.indexOf("?") != -1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for(var i = 0;i<strs.length;i++)
        {
            request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
        }
    }
    var requestInfo = new Object();
    requestInfo.orderId = request["orderId"];
    var tripType = request["train_number"].charAt(0);
    if(tripType == 'G' || tripType == 'D'){
        requestInfo.type = 1;
    }else{
        requestInfo.type = 0;
    }
    var data = JSON.stringify(requestInfo);

    //发送请求
    $.ajax({
        type: "post",
        url: "/getVoucher",
        contentType: "application/json",
        dataType: "json",
        data:data,
        success: function(result){
            document.getElementById("voucher_id").innerText = "10000" + result.voucher_id;
            document.getElementById("order_id").innerText = result.order_id;
            document.getElementById("travel_date").innerText = result.travelDate;;
            document.getElementById("passenger_name").innerText = result.contactName;
            document.getElementById("train_number").innerText = result.train_number;
            document.getElementById("seat_number").innerText = result.seat_number;
            document.getElementById("start_station").innerText = result.start_station;
            document.getElementById("dest_station").innerText = result.dest_station;
            document.getElementById("price").innerText = result.price;
        },
        complete: function(){

        }
    });
}
