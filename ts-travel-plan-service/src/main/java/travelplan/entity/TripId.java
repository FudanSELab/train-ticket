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

package travelplan.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fdse
 */
@Data
public class TripId implements Serializable{

    private TrainTypeEnum type;

    private String number;

    public TripId(){
        //Default Constructor
    }

    public TripId(String trainNumber){
        char type0 = trainNumber.charAt(0);
        switch(type0){
            case 'G': this.type = TrainTypeEnum.G;
                break;
            case 'D': this.type = TrainTypeEnum.D;
                break;
            case 'Z': this.type = TrainTypeEnum.Z;
                break;
            case 'T': this.type = TrainTypeEnum.T;
                break;
            case 'K': this.type = TrainTypeEnum.K;
                break;
            default:break;
        }

        this.number = trainNumber.substring(1);
    }

    @Override
    public String toString(){
        return type.getName() + number;
    }
}
