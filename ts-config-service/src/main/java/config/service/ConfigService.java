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

package config.service;

import config.entity.Config;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;


/**
 * @author fdse
 */
public interface ConfigService {

    /**
     * create by config information and headers
     *
     * @param info info
     * @param headers headers
     * @return Response
     */
    Response create(Config info, HttpHeaders headers);

    /**
     * update by config information and headers
     *
     * @param info info
     * @param headers headers
     * @return Response
     */
    Response update(Config info, HttpHeaders headers);

    /**
     * Config retrieve
     *
     * @param name name
     * @param headers headers
     * @return Response
     */
    Response query(String name, HttpHeaders headers);

    /**
     * delete by name and headers
     *
     * @param name name
     * @param headers headers
     * @return Response
     */
    Response delete(String name, HttpHeaders headers);

    /**
     * query all by headers
     *
     * @param headers headers
     * @return Response
     */
    Response queryAll(HttpHeaders headers);
}
