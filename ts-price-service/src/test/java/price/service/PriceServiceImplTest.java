package price.service;

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
import price.entity.PriceConfig;
import price.repository.PriceConfigRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class PriceServiceImplTest {

    @InjectMocks
    private PriceServiceImpl priceServiceImpl;

    @Mock
    private PriceConfigRepository priceConfigRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateNewPriceConfig1() {
        PriceConfig createAndModifyPriceConfig = new PriceConfig();
        Mockito.when(priceConfigRepository.save(Mockito.any(PriceConfig.class))).thenReturn(null);
        Response result = priceServiceImpl.createNewPriceConfig(createAndModifyPriceConfig, headers);
        Assert.assertNotNull(result.getData());
    }

    @Test
    public void testCreateNewPriceConfig2() {
        PriceConfig createAndModifyPriceConfig = new PriceConfig(UUID.randomUUID().toString(), "G", "G1255", 1.0, 2.0);
        Mockito.when(priceConfigRepository.findById(Mockito.any(UUID.class).toString())).thenReturn(null);
        Mockito.when(priceConfigRepository.save(Mockito.any(PriceConfig.class))).thenReturn(null);
        Response result = priceServiceImpl.createNewPriceConfig(createAndModifyPriceConfig, headers);
        Assert.assertEquals(new Response<>(1, "Create success", createAndModifyPriceConfig), result);
    }

    @Test
    public void testFindById() {
        Mockito.when(priceConfigRepository.findById(Mockito.any(UUID.class).toString())).thenReturn(null);
        PriceConfig result = priceServiceImpl.findById(UUID.randomUUID().toString(), headers);
        Assert.assertNull(result);
    }

    @Test
    public void testFindByRouteIdAndTrainType1() {
        Mockito.when(priceConfigRepository.findByRouteIdAndTrainType(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        Response result = priceServiceImpl.findByRouteIdAndTrainType("route_id", "train_type", headers);
        Assert.assertEquals(new Response<>(0, "No that config", null), result);
    }

    @Test
    public void testFindByRouteIdAndTrainType2() {
        PriceConfig priceConfig = new PriceConfig();
        Mockito.when(priceConfigRepository.findByRouteIdAndTrainType(Mockito.anyString(), Mockito.anyString())).thenReturn(priceConfig);
        Response result = priceServiceImpl.findByRouteIdAndTrainType("route_id", "train_type", headers);
        Assert.assertEquals(new Response<>(1, "Success", priceConfig), result);
    }

    @Test
    public void testFindAllPriceConfig1() {
        Mockito.when(priceConfigRepository.findAll()).thenReturn(null);
        Response result = priceServiceImpl.findAllPriceConfig(headers);
        Assert.assertEquals(new Response<>(0, "No price config", null), result);
    }

    @Test
    public void testFindAllPriceConfig2() {
        List<PriceConfig> list = new ArrayList<>();
        list.add(new PriceConfig());
        Mockito.when(priceConfigRepository.findAll()).thenReturn(list);
        Response result = priceServiceImpl.findAllPriceConfig(headers);
        Assert.assertEquals(new Response<>(1, "Success", list), result);
    }

    @Test
    public void testDeletePriceConfig1() {
        PriceConfig c = new PriceConfig();
        Mockito.when(priceConfigRepository.findById(Mockito.any(UUID.class).toString())).thenReturn(null);
        Response result = priceServiceImpl.deletePriceConfig(c.getId(), headers);
        Assert.assertEquals(new Response<>(0, "No that config", null), result);
    }

    @Test
    public void testDeletePriceConfig2() {
        PriceConfig c = new PriceConfig();
        Mockito.when(priceConfigRepository.findById(Mockito.any(UUID.class).toString()).get()).thenReturn(c);
        Mockito.doNothing().doThrow(new RuntimeException()).when(priceConfigRepository).delete(Mockito.any(PriceConfig.class));
        Response result = priceServiceImpl.deletePriceConfig(c.getId(), headers);
        Assert.assertEquals(new Response<>(1, "Delete success", c), result);
    }

    @Test
    public void testUpdatePriceConfig1() {
        PriceConfig c = new PriceConfig();
        Mockito.when(priceConfigRepository.findById(Mockito.any(UUID.class).toString())).thenReturn(null);
        Response result = priceServiceImpl.updatePriceConfig(c, headers);
        Assert.assertEquals(new Response<>(0, "No that config", null), result);
    }

    @Test
    public void testUpdatePriceConfig2() {
        PriceConfig c = new PriceConfig();
        Mockito.when(priceConfigRepository.findById(Mockito.any(UUID.class).toString()).get()).thenReturn(c);
        Mockito.when(priceConfigRepository.save(Mockito.any(PriceConfig.class))).thenReturn(null);
        Response result = priceServiceImpl.updatePriceConfig(c, headers);
        Assert.assertEquals(new Response<>(1, "Update success", c), result);
    }

}
