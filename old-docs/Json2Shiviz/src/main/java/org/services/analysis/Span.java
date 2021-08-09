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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
public class Span {
    private String traceId;
    private String spanId;
    private String parentId;
    private String spanname;
    private List<String> childs;
    private List<HashMap<String,String>> logs;

    public Span(String traceId, String spanId, String parentId, String spanname) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentId = parentId;
        this.spanname = spanname;
        logs = new ArrayList<HashMap<String,String>>();
    }

    public void addLog(HashMap<String,String> log){
        logs.add(log);
    }

    public List<HashMap<String,String>> getLogs(){
        return logs;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public String getParentId() {
        return parentId;
    }

    public List<String> getChilds() {
        return childs;
    }

    public void setChilds(List<String> childs) {
        this.childs = childs;
    }

    public void addChild(String childId){
        childs.add(childId);
    }

    public String getSpanname() {
        return spanname;
    }

    public void setSpanname(String spanname) {
        this.spanname = spanname;
    }
}
