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

package contacts.service;

import contacts.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

/**
 * @author fdse
 */
public interface ContactsService {

    /**
     * create contacts
     *
     * @param contacts contacts
     * @param headers headers
     * @return Reaponse
     */
    Response createContacts(Contacts contacts, HttpHeaders headers);

    /**
     * create
     *
     * @param addContacts add contacts
     * @param headers headers
     * @return Reaponse
     */
    Response create(Contacts addContacts, HttpHeaders headers);

    /**
     * delete
     *
     * @param contactsId contacts id
     * @param headers headers
     * @return Reaponse
     */
    Response delete(UUID contactsId, HttpHeaders headers);

    /**
     * modify
     *
     * @param contacts contacts
     * @param headers headers
     * @return Reaponse
     */
    Response modify(Contacts contacts, HttpHeaders headers);

    /**
     * get all contacts
     *
     * @param headers headers
     * @return Reaponse
     */
    Response getAllContacts(HttpHeaders headers);

    /**
     * find contacts by id
     *
     * @param id id
     * @param headers headers
     * @return Reaponse
     */
    Response findContactsById(UUID id, HttpHeaders headers);

    /**
     * find contacts by account id
     *
     * @param accountId account id
     * @param headers headers
     * @return Reaponse
     */
    Response findContactsByAccountId(UUID accountId, HttpHeaders headers);

}
