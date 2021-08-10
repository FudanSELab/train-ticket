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

package user.controller;

import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.dto.UserDto;
import user.service.UserService;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/userservice/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/hello")
    public String testHello() {
        return "Hello";
    }

    @GetMapping
    public ResponseEntity<Response> getAllUser(@RequestHeader HttpHeaders headers) {
        UserController.LOGGER.info("Get all user");
        return ok(userService.getAllUsers(headers));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<Response> getUserByUserName(@PathVariable String userName, @RequestHeader HttpHeaders headers) {
        UserController.LOGGER.info("Get user by user name,UserName: {}",userName);
        return ok(userService.findByUserName(userName, headers));
    }
    @GetMapping("/id/{userId}")
    public ResponseEntity<Response> getUserByUserId(@PathVariable String userId, @RequestHeader HttpHeaders headers) {
        UserController.LOGGER.info("Get user by user id,UserId: {}",userId);
        return ok(userService.findByUserId(userId, headers));
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        UserController.LOGGER.info("Register user,User name: {}",userDto.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto, headers));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteUserById(@PathVariable String userId,
                                                   @RequestHeader HttpHeaders headers) {
        // only admin token can delete
        UserController.LOGGER.info("Delete user,UserId: {}",userId);
        return ok(userService.deleteUser(UUID.fromString(userId), headers));
    }

    @PutMapping
    public ResponseEntity<Response> updateUser(@RequestBody UserDto user,
                                               @RequestHeader HttpHeaders headers) {
        UserController.LOGGER.info("Update user,UserId: {}",user.getUserId());
        return ok(userService.updateUser(user, headers));
    }

}
