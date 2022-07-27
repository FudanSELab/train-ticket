package food.service;

import edu.fudan.common.util.Response;
import food.entity.StationFoodStore;
import food.repository.StationFoodRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StationFoodServiceImplTest {

    @InjectMocks
    private StationFoodServiceImpl foodMapServiceImpl;

    @Mock
    private StationFoodRepository stationFoodRepository;

//    @Mock
//    private TrainFoodRepository trainFoodRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFoodStore1() {
        StationFoodStore fss=new StationFoodStore();
        Optional<StationFoodStore> fs = Optional.ofNullable(fss);
        Mockito.when(stationFoodRepository.findById(Mockito.any(String.class))).thenReturn(fs);
        Response result = foodMapServiceImpl.createFoodStore(fs.get(), headers);
        Assert.assertEquals(new Response<>(0, "Already Exists Id", null), result);
    }

    @Test
    public void testCreateFoodStore2() {
        StationFoodStore fs = new StationFoodStore();
        Mockito.when(stationFoodRepository.findById(Mockito.any(String.class))).thenReturn(null);
        Mockito.when(stationFoodRepository.save(Mockito.any(StationFoodStore.class))).thenReturn(null);
        Response result = foodMapServiceImpl.createFoodStore(fs, headers);
        Assert.assertEquals(new Response<>(1, "Save Success", fs), result);
    }

//    @Test
//    public void testCreateTrainFood1() {
//        TrainFood tf = new TrainFood();
//        Mockito.when(trainFoodRepository.findById(Mockito.any(UUID.class))).thenReturn(tf);
//        TrainFood result = foodMapServiceImpl.createTrainFood(tf, headers);
//        Assert.assertEquals(tf, result);
//    }
//
//    @Test
//    public void testCreateTrainFood2() {
//        TrainFood tf = new TrainFood();
//        Mockito.when(trainFoodRepository.findById(Mockito.any(UUID.class))).thenReturn(null);
//        Mockito.when(trainFoodRepository.save(Mockito.any(TrainFood.class))).thenReturn(null);
//        TrainFood result = foodMapServiceImpl.createTrainFood(tf, headers);
//        Assert.assertEquals(tf, result);
//    }

    @Test
    public void testListFoodStores1() {
        List<StationFoodStore> stationFoodStores = new ArrayList<>();
        stationFoodStores.add(new StationFoodStore());
        Mockito.when(stationFoodRepository.findAll()).thenReturn(stationFoodStores);
        Response result = foodMapServiceImpl.listFoodStores(headers);
        Assert.assertEquals(new Response<>(1, "Success", stationFoodStores), result);
    }

    @Test
    public void testListFoodStores2() {
        Mockito.when(stationFoodRepository.findAll()).thenReturn(null);
        Response result = foodMapServiceImpl.listFoodStores(headers);
        Assert.assertEquals(new Response<>(0, "Food store is empty", null), result);
    }

//    @Test
//    public void testListTrainFood1() {
//        List<TrainFood> trainFoodList = new ArrayList<>();
//        trainFoodList.add(new TrainFood());
//        Mockito.when(trainFoodRepository.findAll()).thenReturn(trainFoodList);
//        Response result = foodMapServiceImpl.listTrainFood(headers);
//        Assert.assertEquals(new Response<>(1, "Success", trainFoodList), result);
//    }
//
//    @Test
//    public void testListTrainFood2() {
//        Mockito.when(trainFoodRepository.findAll()).thenReturn(null);
//        Response result = foodMapServiceImpl.listTrainFood(headers);
//        Assert.assertEquals(new Response<>(0, "No content", null), result);
//    }

    @Test
    public void testListFoodStoresByStationName1() {
        List<StationFoodStore> stationFoodStoreList = new ArrayList<>();
        stationFoodStoreList.add(new StationFoodStore());
        Mockito.when(stationFoodRepository.findByStationName(Mockito.anyString())).thenReturn(stationFoodStoreList);
        Response result = foodMapServiceImpl.listFoodStoresByStationName("station_id", headers);
        Assert.assertEquals(new Response<>(1, "Success", stationFoodStoreList), result);
    }

    @Test
    public void testListFoodStoresByStationName2() {
        Mockito.when(stationFoodRepository.findByStationName(Mockito.anyString())).thenReturn(null);
        Response result = foodMapServiceImpl.listFoodStoresByStationName("station_id", headers);
        Assert.assertEquals(new Response<>(0, "Food store is empty", null), result);
    }

//    @Test
//    public void testListTrainFoodByTripId1() {
//        List<TrainFood> trainFoodList = new ArrayList<>();
//        trainFoodList.add(new TrainFood());
//        Mockito.when(trainFoodRepository.findByTripId(Mockito.anyString())).thenReturn(trainFoodList);
//        Response result = foodMapServiceImpl.listTrainFoodByTripId("trip_id", headers);
//        Assert.assertEquals(new Response<>(1, "Success", trainFoodList), result);
//    }
//
//    @Test
//    public void testListTrainFoodByTripId2() {
//        Mockito.when(trainFoodRepository.findByTripId(Mockito.anyString())).thenReturn(null);
//        Response result = foodMapServiceImpl.listTrainFoodByTripId("trip_id", headers);
//        Assert.assertEquals(new Response<>(0, "No content", null), result);
//    }

    @Test
    public void testGetFoodStoresByStationNames1() {
        List<String> stationIds = new ArrayList<>();
        List<StationFoodStore> stationFoodStoreList = new ArrayList<>();
        stationFoodStoreList.add(new StationFoodStore());
        Mockito.when(stationFoodRepository.findByStationNameIn(Mockito.anyList())).thenReturn(stationFoodStoreList);
        Response result = foodMapServiceImpl.getFoodStoresByStationNames(stationIds);
        Assert.assertEquals(new Response<>(1, "Success", stationFoodStoreList), result);
    }

    @Test
    public void testGetFoodStoresByStationNames2() {
        List<String> stationIds = new ArrayList<>();
        Mockito.when(stationFoodRepository.findByStationNameIn(Mockito.anyList())).thenReturn(null);
        Response result = foodMapServiceImpl.getFoodStoresByStationNames(stationIds);
        Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

}
