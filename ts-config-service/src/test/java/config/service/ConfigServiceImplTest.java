package config.service;

import config.entity.Config;
import config.repository.ConfigRepository;
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
import java.util.List;

@RunWith(JUnit4.class)
public class ConfigServiceImplTest {

    @InjectMocks
    private ConfigServiceImpl configServiceImpl;

    @Mock
    private ConfigRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate1() {
        Config info = new Config();
        Mockito.when(repository.findByName(info.getName())).thenReturn(info);
        Response<Config> result = configServiceImpl.create(info, headers);
        Assert.assertEquals(new Response<Config>(0, "Config  already exists.", null), result);
    }

    @Test
    public void testCreate2() {
        Config info = new Config("", "", "");
        Mockito.when(repository.findByName(info.getName())).thenReturn(null);
        Mockito.when(repository.save(Mockito.any(Config.class))).thenReturn(null);
        Response<Config> result = configServiceImpl.create(info, headers);
        Assert.assertEquals(new Response<Config>(1, "Create success", new Config("", "", "")), result);
    }

    @Test
    public void testUpdate1() {
        Config info = new Config();
        Mockito.when(repository.findByName(info.getName())).thenReturn(null);
        Response<Config> result = configServiceImpl.update(info, headers);
        Assert.assertEquals(new Response<Config>(0, "Not found", null), result);
    }

    @Test
    public void testUpdate2() {
        Config info = new Config("", "", "");
        Mockito.when(repository.findByName(info.getName())).thenReturn(info);
        Mockito.when(repository.save(Mockito.any(Config.class))).thenReturn(null);
        Response<Config> result = configServiceImpl.update(info, headers);
        Assert.assertEquals(new Response<Config>(1, "Update success", new Config("", "", "")), result);
    }

    @Test
    public void testQuery1() {
        Mockito.when(repository.findByName("name")).thenReturn(null);
        Response<Config> result = configServiceImpl.query("name", headers);
        Assert.assertEquals(new Response<Config>(0, "No content", null), result);
    }

    @Test
    public void testQuery2() {
        Config info = new Config();
        Mockito.when(repository.findByName("name")).thenReturn(info);
        Response<Config> result = configServiceImpl.query("name", headers);
        Assert.assertEquals(new Response<Config>(1, "Success", new Config()), result);
    }

    @Test
    public void testDelete1() {
        Mockito.when(repository.findByName("name")).thenReturn(null);
        Response<Config> result = configServiceImpl.delete("name", headers);
        Assert.assertEquals(new Response<Config>(0, "No content", null), result);
    }

    @Test
    public void testDelete2() {
        Config info = new Config();
        Mockito.when(repository.findByName("name")).thenReturn(info);
        Mockito.doNothing().doThrow(new RuntimeException()).when(repository).deleteByName("name");
        Response<Config> result = configServiceImpl.delete("name", headers);
        Assert.assertEquals(new Response<Config>(1, "Delete success", info), result);
    }

    @Test
    public void testQueryAll1() {
        List<Config> configList = new ArrayList<>();
        configList.add(new Config());
        Mockito.when(repository.findAll()).thenReturn(configList);
        Response<List<Config>> result = configServiceImpl.queryAll(headers);
        Assert.assertEquals(new Response<List<Config>>(1, "Find all config success", configList), result);
    }
}
