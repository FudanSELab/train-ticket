package contacts.controller;

import com.alibaba.fastjson.JSONObject;
import contacts.entity.Contacts;
import contacts.service.ContactsService;
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
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

@RunWith(JUnit4.class)
public class ContactsControllerTest {

    @InjectMocks
    private ContactsController contactsController;

    @Mock
    private ContactsService contactsService;
    private MockMvc mockMvc;
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contactsController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contact/contacts/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Contacts Service ] !"));
    }

    @Test
    public void testGetAllContacts() throws Exception {
        Mockito.when(contactsService.getAllContacts(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contact/contacts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testCreateNewContacts() throws Exception {
        Contacts aci = new Contacts();
        Mockito.when(contactsService.create(Mockito.any(Contacts.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(aci);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/contact/contacts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testCreateNewContactsAdmin() throws Exception {
        Contacts aci = new Contacts();
        Mockito.when(contactsService.createContacts(Mockito.any(Contacts.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(aci);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/contact/contacts/admin").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteContacts() throws Exception {
        UUID contactsId = UUID.randomUUID();
        Mockito.when(contactsService.delete(Mockito.any(UUID.class).toString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/contact/contacts/" + contactsId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyContacts() throws Exception {
        Contacts info = new Contacts();
        Mockito.when(contactsService.modify(Mockito.any(Contacts.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/contact/contacts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testFindContactsByAccountId() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(contactsService.findContactsByAccountId(Mockito.any(UUID.class).toString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contact/contacts/account/" + accountId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetContactsByContactsId() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(contactsService.findContactsById(Mockito.any(UUID.class).toString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contact/contacts/" + id.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

}
