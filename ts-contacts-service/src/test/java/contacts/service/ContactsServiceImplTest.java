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

import contacts.entity.Contacts;
import contacts.repository.ContactsRepository;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(JUnit4.class)
public class ContactsServiceImplTest {

    @InjectMocks
    private ContactsServiceImpl contactsServiceImpl;

    @Mock
    private ContactsRepository contactsRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindContactsById1() {
        UUID id = UUID.randomUUID();
        Contacts contacts = new Contacts();
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(contacts);
        Response result = contactsServiceImpl.findContactsById(id, headers);
        Assert.assertEquals(new Response<>(1, "Success", contacts), result);
    }

    @Test
    public void testFindContactsById2() {
        UUID id = UUID.randomUUID();
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Response result = contactsServiceImpl.findContactsById(id, headers);
        Assert.assertEquals(new Response<>(0, "No contacts according to contacts id", null), result);
    }

    @Test
    public void testFindContactsByAccountId() {
        UUID accountId = UUID.randomUUID();
        ArrayList<Contacts> arr = new ArrayList<>();
        Mockito.when(contactsRepository.findByAccountId(Mockito.any(UUID.class))).thenReturn(arr);
        Response result = contactsServiceImpl.findContactsByAccountId(accountId, headers);
        Assert.assertEquals(new Response<>(1, "Success", arr), result);
    }

    @Test
    public void testCreateContacts1() {
        Contacts contacts = new Contacts();
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(contacts);
        Response result = contactsServiceImpl.createContacts(contacts, headers);
        Assert.assertEquals(new Response<>(0, "Already Exists", contacts), result);
    }

    @Test
    public void testCreateContacts2() {
        Contacts contacts = new Contacts();
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Mockito.when(contactsRepository.save(Mockito.any(Contacts.class))).thenReturn(null);
        Response result = contactsServiceImpl.createContacts(contacts, headers);
        Assert.assertEquals(new Response<>(1, "Create Success", null), result);
    }

    @Test
    public void testCreate1() {
        Contacts addContacts = new Contacts(UUID.randomUUID(), UUID.randomUUID(), "name", 1, "12", "10001");
        ArrayList<Contacts> accountContacts = new ArrayList<>();
        accountContacts.add(addContacts);
        Mockito.when(contactsRepository.findByAccountId(Mockito.any(UUID.class))).thenReturn(accountContacts);
        Response result = contactsServiceImpl.create(addContacts, headers);
        Assert.assertEquals(new Response<>(0, "Contacts already exists", null), result);
    }

    @Test
    public void testCreate2() {
        Contacts addContacts = new Contacts(UUID.randomUUID(), UUID.randomUUID(), "name", 1, "12", "10001");
        ArrayList<Contacts> accountContacts = new ArrayList<>();
        Mockito.when(contactsRepository.findByAccountId(Mockito.any(UUID.class))).thenReturn(accountContacts);
        Mockito.when(contactsRepository.save(Mockito.any(Contacts.class))).thenReturn(null);
        Response result = contactsServiceImpl.create(addContacts, headers);
        Assert.assertEquals(new Response<>(1, "Create contacts success", addContacts), result);
    }

    @Test
    public void testDelete1() {
        UUID contactsId = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(contactsRepository).deleteById(Mockito.any(UUID.class));
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Response result = contactsServiceImpl.delete(contactsId, headers);
        Assert.assertEquals(new Response<>(1, "Delete success", contactsId), result);
    }

    @Test
    public void testDelete2() {
        UUID contactsId = UUID.randomUUID();
        Contacts contacts = new Contacts();
        Mockito.doNothing().doThrow(new RuntimeException()).when(contactsRepository).deleteById(Mockito.any(UUID.class));
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(contacts);
        Response result = contactsServiceImpl.delete(contactsId, headers);
        Assert.assertEquals(new Response<>(0, "Delete failed", contactsId), result);
    }

    @Test
    public void testModify1() {
        Contacts contacts = new Contacts(UUID.randomUUID(), UUID.randomUUID(), "name", 1, "12", "10001");
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Response result = contactsServiceImpl.modify(contacts, headers);
        Assert.assertEquals(new Response<>(0, "Contacts not found", null), result);
    }

    @Test
    public void testModify2() {
        Contacts contacts = new Contacts(UUID.randomUUID(), UUID.randomUUID(), "name", 1, "12", "10001");
        Mockito.when(contactsRepository.findById(Mockito.any(UUID.class))).thenReturn(contacts);
        Mockito.when(contactsRepository.save(Mockito.any(Contacts.class))).thenReturn(null);
        Response result = contactsServiceImpl.modify(contacts, headers);
        Assert.assertEquals(new Response<>(1, "Modify success", contacts), result);
    }

    @Test
    public void testGetAllContacts1() {
        ArrayList<Contacts> contacts = new ArrayList<>();
        contacts.add(new Contacts());
        Mockito.when(contactsRepository.findAll()).thenReturn(contacts);
        Response result = contactsServiceImpl.getAllContacts(headers);
        Assert.assertEquals(new Response<>(1, "Success", contacts), result);
    }

    @Test
    public void testGetAllContacts2() {
        Mockito.when(contactsRepository.findAll()).thenReturn(null);
        Response result = contactsServiceImpl.getAllContacts(headers);
        Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

}
