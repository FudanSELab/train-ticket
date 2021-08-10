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



$("#refresh_news_button").click(function(){
    $.ajax({
        type: "get",
        url: "/news-service/news",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#news_div").html("");
            for(var i = 0;i < result.length;i++){
                alert(result[i]["Title"]);
                alert(result[i]["Content"]);
                $("#news_div").append(
                    "<div class='panel-heading'>" +
                        "<h3 class='panel-title'>" +
                            result[i]["Title"] +
                        "</h3>" +
                    "</div>" +
                    "<div class='input-box panel-body'>" +
                        result[i]["Content"] +
                    "</div>"
                );
            }
        }
    });
});
