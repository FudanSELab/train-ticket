package fdse.microservice.service;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.Station;
import fdse.microservice.repository.StationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class StationServiceImplTest {

    @InjectMocks
    private StationServiceImpl stationServiceImpl;

    @Mock
    private StationRepository repository;

	private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testCreate1() {
		Station request = new Station();
		request.setName("shanghai");
		request.setStayTime(5);

		Station savedStation = new Station("shanghai", 5);
		savedStation.setId("station_id");

		Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(null);
		Mockito.when(repository.save(Mockito.any(Station.class))).thenReturn(savedStation);

		Response<Station> result = stationServiceImpl.create(request, headers);

		Assert.assertEquals(Integer.valueOf(1), result.getStatus());
		Assert.assertEquals("Create success", result.getMsg());
		Assert.assertEquals(savedStation, result.getData());
    }

    @Test
    public void testCreate2() {
		Station request = new Station();
		request.setName("shanghai");
		request.setStayTime(5);

		Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(new Station("shanghai", 5));

		Response<Station> result = stationServiceImpl.create(request, headers);

		Assert.assertEquals(Integer.valueOf(0), result.getStatus());
		Assert.assertEquals("Already exists", result.getMsg());
		Assert.assertEquals(request, result.getData());
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
		info.setId("station_id");
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Response<Station> result = stationServiceImpl.update(info, headers);
		Assert.assertEquals(new Response<>(0, "Station not exist", null), result);
    }

    @Test
    public void testUpdate2() {
        Station info = new Station();
		info.setId("station_id");
		info.setName("beijing");
		info.setStayTime(10);

		Station existing = new Station();
		existing.setId("station_id");
		existing.setName("shanghai");
		existing.setStayTime(5);

		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(existing));
		Mockito.when(repository.save(Mockito.any(Station.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<Station> result = stationServiceImpl.update(info, headers);
		Assert.assertEquals("Update success", result.getMsg());
		Assert.assertEquals("beijing", result.getData().getName());
		Assert.assertEquals(10, result.getData().getStayTime());
    }

    @Test
    public void testDelete1() {
        Station info = new Station();
		info.setId("station_id");
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(info));
		Mockito.doNothing().when(repository).delete(Mockito.any(Station.class));
        Response<Station> result = stationServiceImpl.delete(info.getId(), headers);
        Assert.assertEquals("Delete success", result.getMsg());
    }

    @Test
    public void testDelete2() {
        Station info = new Station();
		info.setId("station_id");
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Response<Station> result = stationServiceImpl.delete(info.getId(), headers);
		Assert.assertEquals(new Response<>(0, "Station not exist", null), result);
    }

    @Test
    public void testQuery1() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station());
        Mockito.when(repository.findAll()).thenReturn(stations);
        Response<List<Station>> result = stationServiceImpl.query(headers);
        Assert.assertEquals(new Response<>(1, "Find all content", stations), result);
    }

    @Test
    public void testQuery2() {
		Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
        Response<List<Station>> result = stationServiceImpl.query(headers);
		Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

    @Test
    public void testQueryForId1() {
        Station station = new Station();
		station.setId("station_id");
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(station);
        Response<String> result = stationServiceImpl.queryForId("station_name", headers);
		Assert.assertEquals(new Response<>(1, "Success", station.getId()), result);
    }

    @Test
    public void testQueryForId2() {
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(null);
        Response<String> result = stationServiceImpl.queryForId("station_name", headers);
        Assert.assertEquals(new Response<>(0, "Not exists", "station_name"), result);
    }

    @Test
    public void testQueryForIdBatch1() {
        List<String> nameList = new ArrayList<>();
		Mockito.when(repository.findByNames(nameList)).thenReturn(Collections.emptyList());
		Response<Map<String, String>> result = stationServiceImpl.queryForIdBatch(nameList, headers);
		Assert.assertEquals(new Response<>(0, "No content according to name list", null), result);
    }

    @Test
    public void testQueryForIdBatch2() {
        List<String> nameList = new ArrayList<>();
        nameList.add("station_name");
		Station station = new Station();
		station.setId("station_id");
		station.setName("station_name");
		Mockito.when(repository.findByNames(nameList)).thenReturn(Collections.singletonList(station));
        Response<Map<String, String>> result = stationServiceImpl.queryForIdBatch(nameList, headers);
		Assert.assertEquals("Success", result.getMsg());
		Assert.assertEquals("station_id", result.getData().get("station_name"));
    }

    @Test
    public void testQueryById1() {
        Station station = new Station();
		station.setName("station_name");
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(station));
        Response<String> result = stationServiceImpl.queryById("station_id", headers);
		Assert.assertEquals(new Response<>(1, "Success", "station_name"), result);
    }

    @Test
    public void testQueryById2() {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Response<String> result = stationServiceImpl.queryById("station_id", headers);
        Assert.assertEquals(new Response<>(0, "No that stationId", "station_id"), result);
    }

    @Test
    public void testQueryByIdBatch1() {
        List<String> idList = new ArrayList<>();
        Response<List<String>> result = stationServiceImpl.queryByIdBatch(idList, headers);
        List<String> expectedNames = new ArrayList<>();
        Assert.assertEquals(new Response<>(0, "No stationNamelist according to stationIdList", expectedNames), result);
    }

    @Test
    public void testQueryByIdBatch2() {
        Station station = new Station();
        List<String> idList = new ArrayList<>();
        idList.add("station_id");
		station.setName("station_name");
		Mockito.when(repository.findById("station_id")).thenReturn(Optional.of(station));
        Response<List<String>> result = stationServiceImpl.queryByIdBatch(idList, headers);
        Assert.assertEquals("Success", result.getMsg());
		Assert.assertEquals(Collections.singletonList("station_name"), result.getData());
    }

}
