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

package travel.entity;

/**
 * @author fdse
 */
public enum SeatClass {

    /**
     * no seat
     */
    NONE        (0,"NoSeat"),
    /**
     * green seat
     */
    BUSINESS    (1,"GreenSeat"),
    /**
     * first class seat
     */
    FIRSTCLASS  (2,"FirstClassSeat"),
    /**
     * second class seat
     */
    SECONDCLASS (3,"SecondClassSeat"),
    /**
     * hard seat
     */
    HARDSEAT    (4,"HardSeat"),
    /**
     * hard seat
     */
    SOFTSEAT    (5,"SoftSeat"),
    /**
     * hard bed
     */
    HARDBED     (6,"HardBed"),
    /**
     * soft bed
     */
    SOFTBED     (7,"SoftBed"),
    /**
     * high soft seat
     */
    HIGHSOFTBED (8,"HighSoftSeat");

    private int code;
    private String name;

    SeatClass(int code, String name){
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
        SeatClass[] seatClassSet = SeatClass.values();
        for(SeatClass seatClass : seatClassSet){
            if(seatClass.getCode() == code){
                return seatClass.getName();
            }
        }
        return seatClassSet[0].getName();
    }
}
