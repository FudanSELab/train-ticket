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

package order.entity;

/**
 * @author fdse
 */
public enum OrderStatus {

    /**
     * not paid
     */
    NOTPAID   (0,"Not Paid"),
    /**
     * paid and not collected
     */
    PAID      (1,"Paid & Not Collected"),
    /**
     * collected
     */
    COLLECTED (2,"Collected"),
    /**
     * cancel and rebook
     */
    CHANGE    (3,"Cancel & Rebook"),
    /**
     * cancel
     */
    CANCEL    (4,"Cancel"),
    /**
     * refunded
     */
    REFUNDS   (5,"Refunded"),
    /**
     * used
     */
    USED      (6,"Used");

    private int code;
    private String name;

    OrderStatus(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code){
        OrderStatus[] orderStatusSet = OrderStatus.values();
        for(OrderStatus orderStatus : orderStatusSet){
            if(orderStatus.getCode() == code){
                return orderStatus.getName();
            }
        }
        return orderStatusSet[0].getName();
    }

}
