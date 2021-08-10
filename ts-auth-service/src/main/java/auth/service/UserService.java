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

package auth.service;

import auth.dto.AuthDto;
import auth.entity.User;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
public interface UserService {

    /**
     * save user
     *
     * @param user user
     * @return user
     */
    User saveUser(User user);

    /**
     * get all users
     *
     * @param headers headers
     * @return List<User>
     */
    List<User> getAllUser(HttpHeaders headers);

    /**
     * create default auth user
     *
     * @param dto dto
     * @return user
     */
    User createDefaultAuthUser(AuthDto dto);

    /**
     * delete by user id
     *
     * @param userId user id
     * @param headers headers
     * @return Response
     */
    Response deleteByUserId(UUID userId, HttpHeaders headers);

}
