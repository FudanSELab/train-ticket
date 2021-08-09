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

package user.dto;

/**
 * @author fdse
 */
public enum Gender {

    /**
     * null
     */
    NONE(0, "Null"),
    /**
     * male
     */
    MALE(1, "Male"),
    /**
     * female
     */
    FEMALE(2, "Female"),
    /**
     * other
     */
    OTHER(3, "Other");

    private int code;
    private String name;

    Gender(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code) {
        Gender[] genderSet = Gender.values();
        for (Gender gender : genderSet) {
            if (gender.getCode() == code) {
                return gender.getName();
            }
        }
        return genderSet[0].getName();
    }

}
