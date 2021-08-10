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

package adminuser.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author fdse
 */
@Data
public class Account {

    private UUID id;

    private String accountId;

    private String loginId;

    private String password;

    private int gender;

    private String name;

    private int documentType;

    private String documentNum;

    private String email;

    public Account(){
        gender = Gender.OTHER.getCode();
        password = "defaultPassword"; //NOSONAR
        name = "None";
        documentType = DocumentType.NONE.getCode();
        documentNum = "0123456789";
        email = "0123456789";
    }

}
