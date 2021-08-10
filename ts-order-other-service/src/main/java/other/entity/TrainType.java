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

package other.entity;

/**
 * @author fdse
 */
public enum TrainType {

    /**
     * G
     */
    GAOTIE    (0,"G"),
    /**
     * D
     */
    DONGCHE   (1,"D"),
    /**
     * C
     */
    CHENGJI   (2,"C"),
    /**
     * Z
     */
    ZHIDA     (3,"Z"),
    /**
     * T
     */
    TEKUAI    (4,"T"),
    /**
     * E
     */
    KUAISU    (5,"K"),
    /**
     * L
     */
    LINKE     (6,"L"),
    /**
     * Y
     */
    YOULAN    (7,"Y"),
    /**
     * S
     */
    CHENGJIAO (8,"S"),
    /**
     *
     */
    OTHER     (9,"");

    private int code;
     private String name;

    TrainType(int code, String name){
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
        TrainType[] trainTypeSet = TrainType.values();
        for(TrainType trainType : trainTypeSet){
            if(trainType.getCode() == code){
                return trainType.getName();
            }
        }
        return trainTypeSet[0].getName();
    }
}
