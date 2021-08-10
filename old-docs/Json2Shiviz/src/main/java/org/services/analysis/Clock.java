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

package org.services.analysis;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/18.
 */
public class Clock {

    private String type;
    private String host;
    private String src;
    private String traceId;
    private String spanId;
    private String parentId;

    private HashMap<String,Integer> clock;

    public Clock(String type, String host, String src, String traceId, String spanId, String parentId, HashMap<String,Integer> clock) {
        this.type = type;
        this.host = host;
        this.src = src;
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentId = parentId;
        this.clock = clock;
    }

    public HashMap<String,Integer> getClock() {
        return clock;
    }

    public boolean isSrc(String traceId, String spanId, String type, String queue, String parentId){
        boolean result = false;

        if("queue".equals(queue)){
            if(traceId.equals(this.traceId) && parentId.equals(this.spanId)){
                if(type.equals("sr") && this.type.equals("cs")){
                    result = true;
                }
            }
        }else{
            if(traceId.equals(this.traceId) && spanId.equals(this.spanId)){
                if(type.equals("sr") && this.type.equals("cs")){
                    result = true;
                }else if(type.equals("cr") && this.type.equals("ss")){
                    result = true;
                }
            }
        }


        return result;
    }
}
