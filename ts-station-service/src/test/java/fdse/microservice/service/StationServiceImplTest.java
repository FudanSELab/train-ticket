package fdse.microservice.service;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.Station;
import fdse.microservice.repository.StationRepository;
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
public class StationServiceImplTest {

    @InjectMocks
    private StationServiceImpl stationServiceImpl;

    @Mock
    private StationRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate1() {
        Station station = new Station();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(null);
        Mockito.when(repository.save(Mockito.any(Station.class))).thenReturn(null);
        Response result = stationServiceImpl.create(station, headers);
        Assert.assertEquals(new Response<>(1, "Create success", station), result);
    }

    @Test
    public void testCreate2() {
        Station station = new Station();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(station);
        Response result = stationServiceImpl.create(station, headers);
        Assert.assertEquals(new Response<>(0, "Already exists", station), result);
    }

    @Test
    public void testExist1() {
        Station station = new Station();
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(station);
        Assert.assertTrue(stationServiceImpl.exist("station_name", headers));
    }

    @Test
    public void testExist2() {
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(null);
        Assert.assertFalse(stationServiceImpl.exist("station_name", headers));
    }

    @Test
    public void testUpdate1() {
        Station info = new Station();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        Response result = stationServiceImpl.update(info, headers);
        Assert.assertEquals(new Response<>(0, "Station not exist", null), result);
    }

    @Test
    public void testUpdate2() {
        Station info = new Station();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(info);
        Mockito.when(repository.save(Mockito.any(Station.class))).thenReturn(null);
        Response result = stationServiceImpl.update(info, headers);
        Assert.assertEquals("Update success", result.getMsg());
    }

    @Test
    public void testDelete1() {
        Station info = new Station();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(info);
        Mockito.doNothing().doThrow(new RuntimeException()).when(repository).delete(Mockito.any(Station.class));
        Response result = stationServiceImpl.delete(info.getId(), headers);
        Assert.assertEquals("Delete success", result.getMsg());
    }

    @Test
    public void testDelete2() {
        Station info = new Station();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        Response result = stationServiceImpl.delete(info.getId(), headers);
        Assert.assertEquals(new Response<>(0, "Station not exist", null), result);
    }

    @Test
    public void testQuery1() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station());
        Mockito.when(repository.findAll()).thenReturn(stations);
        Response result = stationServiceImpl.query(headers);
        Assert.assertEquals(new Response<>(1, "Find all content", stations), result);
    }

    @Test
    public void testQuery2() {
        Mockito.when(repository.findAll()).thenReturn(null);
        Response result = stationServiceImpl.query(headers);
        Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

    @Test
    public void testQueryForId1() {
        Station station = new Station();
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(station);
        Response result = stationServiceImpl.queryForId("station_name", headers);
        Assert.assertEquals(new Response<>(1, "Success", station.getId()), result);
    }

    @Test
    public void testQueryForId2() {
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(null);
        Response result = stationServiceImpl.queryForId("station_name", headers);
        Assert.assertEquals(new Response<>(0, "Not exists", "station_name"), result);
    }

    @Test
    public void testQueryForIdBatch1() {
        List<String> nameList = new ArrayList<>();
        Response result = stationServiceImpl.queryForIdBatch(nameList, headers);
        Assert.assertEquals(new Response<>(0, "No content according to name list", null), result);
    }

    @Test
    public void testQueryForIdBatch2() {
        List<String> nameList = new ArrayList<>();
        nameList.add("station_name");
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(null);
        Response result = stationServiceImpl.queryForIdBatch(nameList, headers);
        Assert.assertEquals("Success", result.getMsg());
    }

    @Test
    public void testQueryById1() {
        Station station = new Station();
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(station);
        Response result = stationServiceImpl.queryById("station_id", headers);
        Assert.assertEquals(new Response<>(1, "Success", ""), result);
    }

    @Test
    public void testQueryById2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(null);
        Response result = stationServiceImpl.queryById("station_id", headers);
        Assert.assertEquals(new Response<>(0, "No that stationId", "station_id"), result);
    }

    @Test
    public void testQueryByIdBatch1() {
        List<String> idList = new ArrayList<>();
        Response result = stationServiceImpl.queryByIdBatch(idList, headers);
        Assert.assertEquals(new Response<>(0, "No stationNamelist according to stationIdList", new ArrayList<>()), result);
    }

    @Test
    public void testQueryByIdBatch2() {
        Station station = new Station();
        List<String> idList = new ArrayList<>();
        idList.add("station_id");
        Mockito.when(repository.findById(Mockito.anyString()).get()).thenReturn(station);
        Response result = stationServiceImpl.queryByIdBatch(idList, headers);
        Assert.assertEquals("Success", result.getMsg());
    }

}
