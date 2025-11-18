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
import price.entity.Price;
import price.repository.PriceConfigRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Price payload = new Price();
        Mockito.when(priceConfigRepository.save(Mockito.any(Price.class))).thenReturn(null);
        Response<Price> result = priceServiceImpl.createPrice(payload, headers);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(Integer.valueOf(1), result.getStatus());
        Assert.assertEquals("Create success", result.getMsg());
    }

    @Test
    public void testCreateNewPriceConfig2() {
        Price payload = new Price(UUID.randomUUID().toString(), "G", "G1255", 1.0, 2.0);
        Mockito.when(priceConfigRepository.save(Mockito.any(Price.class))).thenReturn(null);
        Response<Price> result = priceServiceImpl.createPrice(payload, headers);
        Assert.assertEquals(Integer.valueOf(1), result.getStatus());
        Assert.assertEquals("Create success", result.getMsg());
        Price created = result.getData();
        Assert.assertEquals(payload.getRouteId(), created.getRouteId());
        Assert.assertEquals(payload.getTrainType(), created.getTrainType());
        Assert.assertEquals(payload.getBasicPriceRate(), created.getBasicPriceRate(), 0.0);
        Assert.assertEquals(payload.getFirstClassPriceRate(), created.getFirstClassPriceRate(), 0.0);
    }

    @Test
    public void testFindById() {
        Mockito.when(priceConfigRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Price result = priceServiceImpl.findById(UUID.randomUUID().toString(), headers);
        Assert.assertNull(result);
    }

    @Test
    public void testFindByRouteIdAndTrainType1() {
        Mockito.when(priceConfigRepository.findByRouteIdAndTrainType(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        Response<Price> result = priceServiceImpl.findByRouteIdAndTrainType("route_id", "train_type", headers);
        Assert.assertEquals(new Response<>(0, "No that config", null), result);
    }

    @Test
    public void testFindByRouteIdAndTrainType2() {
        Price priceConfig = new Price();
        Mockito.when(priceConfigRepository.findByRouteIdAndTrainType(Mockito.anyString(), Mockito.anyString())).thenReturn(priceConfig);
        Response<Price> result = priceServiceImpl.findByRouteIdAndTrainType("route_id", "train_type", headers);
        Assert.assertEquals(new Response<>(1, "Success", priceConfig), result);
    }

    @Test
    public void testFindAllPriceConfig1() {
        Mockito.when(priceConfigRepository.findAll()).thenReturn(null);
        Response<List<Price>> result = priceServiceImpl.findAllPrice(headers);
        Assert.assertEquals(new Response<>(1, "Success", null), result);
    }

    @Test
    public void testFindAllPriceConfig2() {
        List<Price> list = new ArrayList<>();
        list.add(new Price());
        Mockito.when(priceConfigRepository.findAll()).thenReturn(list);
        Response<List<Price>> result = priceServiceImpl.findAllPrice(headers);
        Assert.assertEquals(new Response<>(1, "Success", list), result);
    }

    @Test
    public void testDeletePriceConfig1() {
        Mockito.when(priceConfigRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Response<Price> result = priceServiceImpl.deletePrice(UUID.randomUUID().toString(), headers);
        Assert.assertEquals(new Response<>(0, "No that config", null), result);
    }

    @Test
    public void testDeletePriceConfig2() {
        Price price = new Price();
        Mockito.when(priceConfigRepository.findById(price.getId())).thenReturn(Optional.of(price));
        Mockito.doNothing().when(priceConfigRepository).delete(price);
        Response<Price> result = priceServiceImpl.deletePrice(price.getId(), headers);
        Assert.assertEquals(new Response<>(1, "Delete success", price), result);
        Mockito.verify(priceConfigRepository).delete(price);
    }

    @Test
    public void testUpdatePriceConfig1() {
        Price price = new Price();
        Mockito.when(priceConfigRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Response<Price> result = priceServiceImpl.updatePrice(price, headers);
        Assert.assertEquals(new Response<>(0, "No that config", null), result);
    }

    @Test
    public void testUpdatePriceConfig2() {
        Price payload = new Price();
        payload.setRouteId("route");
        payload.setTrainType("G");
        payload.setBasicPriceRate(1.0);
        payload.setFirstClassPriceRate(2.0);

        Price persisted = new Price(payload.getId(), "old", "oldRoute", 0.5, 0.6);
        Mockito.when(priceConfigRepository.findById(payload.getId())).thenReturn(Optional.of(persisted));
        Mockito.when(priceConfigRepository.save(Mockito.any(Price.class))).thenReturn(persisted);

        Response<Price> result = priceServiceImpl.updatePrice(payload, headers);
        Assert.assertEquals(new Response<>(1, "Update success", persisted), result);
        Assert.assertEquals(payload.getRouteId(), persisted.getRouteId());
        Assert.assertEquals(payload.getTrainType(), persisted.getTrainType());
        Assert.assertEquals(payload.getBasicPriceRate(), persisted.getBasicPriceRate(), 0.0);
        Assert.assertEquals(payload.getFirstClassPriceRate(), persisted.getFirstClassPriceRate(), 0.0);
    }

}
