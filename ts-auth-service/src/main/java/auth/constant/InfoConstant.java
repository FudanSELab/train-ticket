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

package auth.constant;

/**
 * @author fdse
 */
public class InfoConstant {

    private InfoConstant() {
        throw new IllegalStateException("Utility class");
    }

    // User service information. If require params, the suffix number is the number of params

    public static final String DUPLICATE = "Duplicate";
    public static final String USER_HAS_ALREADY_EXIST = "User has already exist.";
    public static final String PROPERTIES_CANNOT_BE_EMPTY_1 = "{0} cannot be empty.";
    public static final String USER_IS_NOT_EXIST_2 = "User is not exist, {0}: {1}.";
    public static final String PASSWORD_LEAST_CHAR_1 = "Passwords must contain at least {0} characters."; //NOSONAR
    public static final String USER_NAME_NOT_FOUND_1 = "Username not found: {0}.";

    // User properties

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password"; //NOSONAR
    public static final String ROLES = "roles";

}
