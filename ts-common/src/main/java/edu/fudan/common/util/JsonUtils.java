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

package edu.fudan.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author fdse
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * <p>
     * Object to JSON string
     * </p>
     */
    public static String object2Json(Object obj) {
        String result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            JsonUtils.LOGGER.error(e.getMessage());
        }
        return result;
    }


    public static Map object2Map(Object obj) {
        String object2Json = object2Json(obj);
        Map<?, ?> result = jsonToMap(object2Json);
        return result;
    }

    /**
     * <p>
     * JSON string to Map object
     * </p>
     */
    public static Map<?, ?> jsonToMap(String json) {
        return json2Object(json, Map.class);
    }

    /**
     * <p>
     * JSON to Object
     * </p>
     */
    public static <T> T json2Object(String json, Class<T> cls) {
        T result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(json, cls);
        } catch (NullPointerException e) {
            JsonUtils.LOGGER.error(e.getMessage());
        } catch (IOException e) {
            JsonUtils.LOGGER.error(e.getMessage());
        }

        return result;
    }


    public static <T> T conveterObject(Object srcObject, Class<T> destObjectType) {
        String jsonContent = object2Json(srcObject);
        return json2Object(jsonContent, destObjectType);
    }
}
