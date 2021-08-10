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

package rebook.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdse
 */
@Data
public class RebookInfo {

    @Valid
    @NotNull
    private String loginId;

    @Valid
    @NotNull
    private String orderId;

    @Valid
    @NotNull
    private String oldTripId;

    @Valid
    @NotNull
    private String tripId;

    @Valid
    @NotNull
    private int seatType;

    @Valid
    @NotNull
    private Date date;

    public RebookInfo(){
        //Default Constructor
        this.loginId = "";
        this.orderId = "";
        this.oldTripId = "";
        this.tripId = "";
        this.seatType = 0;
        this.date = new Date();
    }

}
