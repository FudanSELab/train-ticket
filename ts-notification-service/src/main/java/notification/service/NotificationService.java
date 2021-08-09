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

package notification.service;

import notification.entity.NotifyInfo;
import org.springframework.http.HttpHeaders;

/**
 * @author Wenvi
 * @date 2017/6/15
 */
public interface NotificationService {

    /**
     * preserve success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean preserveSuccess(NotifyInfo info, HttpHeaders headers);

    /**S
     * order create success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean orderCreateSuccess(NotifyInfo info, HttpHeaders headers);

    /**
     * order changed success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean orderChangedSuccess(NotifyInfo info, HttpHeaders headers);

    /**
     * order cancel success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean orderCancelSuccess(NotifyInfo info, HttpHeaders headers);
}
