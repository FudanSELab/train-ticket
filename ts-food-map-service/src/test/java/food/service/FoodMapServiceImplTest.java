package food.service;

import edu.fudan.common.util.Response;
import food.entity.FoodStore;
import food.entity.TrainFood;
import food.repository.FoodStoreRepository;
import food.repository.TrainFoodRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoodMapServiceImplTest {

    @InjectMocks
    private FoodMapServiceImpl foodMapServiceImpl;

    @Mock
    private FoodStoreRepository foodStoreRepository;

    @Mock
    private TrainFoodRepository trainFoodRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFoodStore1() {
        FoodStore fs = new FoodStore();
        Mockito.when(foodStoreRepository.findById(Mockito.any(UUID.class))).thenReturn(fs);
        Response result = foodMapServiceImpl.createFoodStore(fs, headers);
        Assert.assertEquals(new Response<>(0, "Already Exists Id", null), result);
    }

    @Test
    public void testCreateFoodStore2() {
        FoodStore fs = new FoodStore();
        Mockito.when(foodStoreRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Mockito.when(foodStoreRepository.save(Mockito.any(FoodStore.class))).thenReturn(null);
        Response result = foodMapServiceImpl.createFoodStore(fs, headers);
        Assert.assertEquals(new Response<>(1, "Save Success", fs), result);
    }

    @Test
    public void testCreateTrainFood1() {
        TrainFood tf = new TrainFood();
        Mockito.when(trainFoodRepository.findById(Mockito.any(UUID.class))).thenReturn(tf);
        TrainFood result = foodMapServiceImpl.createTrainFood(tf, headers);
        Assert.assertEquals(tf, result);
    }

    @Test
    public void testCreateTrainFood2() {
        TrainFood tf = new TrainFood();
        Mockito.when(trainFoodRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
        Mockito.when(trainFoodRepository.save(Mockito.any(TrainFood.class))).thenReturn(null);
        TrainFood result = foodMapServiceImpl.createTrainFood(tf, headers);
        Assert.assertEquals(tf, result);
    }

    @Test
    public void testListFoodStores1() {
        List<FoodStore> foodStores = new ArrayList<>();
        foodStores.add(new FoodStore());
        Mockito.when(foodStoreRepository.findAll()).thenReturn(foodStores);
        Response result = foodMapServiceImpl.listFoodStores(headers);
        Assert.assertEquals(new Response<>(1, "Success", foodStores), result);
    }

    @Test
    public void testListFoodStores2() {
        Mockito.when(foodStoreRepository.findAll()).thenReturn(null);
        Response result = foodMapServiceImpl.listFoodStores(headers);
        Assert.assertEquals(new Response<>(0, "Food store is empty", null), result);
    }

    @Test
    public void testListTrainFood1() {
        List<TrainFood> trainFoodList = new ArrayList<>();
        trainFoodList.add(new TrainFood());
        Mockito.when(trainFoodRepository.findAll()).thenReturn(trainFoodList);
        Response result = foodMapServiceImpl.listTrainFood(headers);
        Assert.assertEquals(new Response<>(1, "Success", trainFoodList), result);
    }

    @Test
    public void testListTrainFood2() {
        Mockito.when(trainFoodRepository.findAll()).thenReturn(null);
        Response result = foodMapServiceImpl.listTrainFood(headers);
        Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

    @Test
    public void testListFoodStoresByStationId1() {
        List<FoodStore> foodStoreList = new ArrayList<>();
        foodStoreList.add(new FoodStore());
        Mockito.when(foodStoreRepository.findByStationId(Mockito.anyString())).thenReturn(foodStoreList);
        Response result = foodMapServiceImpl.listFoodStoresByStationId("station_id", headers);
        Assert.assertEquals(new Response<>(1, "Success", foodStoreList), result);
    }

    @Test
    public void testListFoodStoresByStationId2() {
        Mockito.when(foodStoreRepository.findByStationId(Mockito.anyString())).thenReturn(null);
        Response result = foodMapServiceImpl.listFoodStoresByStationId("station_id", headers);
        Assert.assertEquals(new Response<>(0, "Food store is empty", null), result);
    }

    @Test
    public void testListTrainFoodByTripId1() {
        List<TrainFood> trainFoodList = new ArrayList<>();
        trainFoodList.add(new TrainFood());
        Mockito.when(trainFoodRepository.findByTripId(Mockito.anyString())).thenReturn(trainFoodList);
        Response result = foodMapServiceImpl.listTrainFoodByTripId("trip_id", headers);
        Assert.assertEquals(new Response<>(1, "Success", trainFoodList), result);
    }

    @Test
    public void testListTrainFoodByTripId2() {
        Mockito.when(trainFoodRepository.findByTripId(Mockito.anyString())).thenReturn(null);
        Response result = foodMapServiceImpl.listTrainFoodByTripId("trip_id", headers);
        Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

    @Test
    public void testGetFoodStoresByStationIds1() {
        List<String> stationIds = new ArrayList<>();
        List<FoodStore> foodStoreList = new ArrayList<>();
        foodStoreList.add(new FoodStore());
        Mockito.when(foodStoreRepository.findByStationIdIn(Mockito.anyList())).thenReturn(foodStoreList);
        Response result = foodMapServiceImpl.getFoodStoresByStationIds(stationIds);
        Assert.assertEquals(new Response<>(1, "Success", foodStoreList), result);
    }

    @Test
    public void testGetFoodStoresByStationIds2() {
        List<String> stationIds = new ArrayList<>();
        Mockito.when(foodStoreRepository.findByStationIdIn(Mockito.anyList())).thenReturn(null);
        Response result = foodMapServiceImpl.getFoodStoresByStationIds(stationIds);
        Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

}
