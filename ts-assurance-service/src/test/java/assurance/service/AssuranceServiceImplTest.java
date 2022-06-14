package assurance.service;

import assurance.entity.Assurance;
import assurance.entity.AssuranceType;
import assurance.entity.AssuranceTypeBean;
import assurance.entity.PlainAssurance;
import assurance.repository.AssuranceRepository;
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
import java.util.UUID;

@RunWith(JUnit4.class)
public class AssuranceServiceImplTest {

    @InjectMocks
    private AssuranceServiceImpl assuranceServiceImpl;

    @Mock
    private AssuranceRepository assuranceRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAssuranceById1() {
        UUID id = UUID.randomUUID();
        Mockito.when(assuranceRepository.findById(id.toString())).thenReturn(null);
        Response result = assuranceServiceImpl.findAssuranceById(id, headers);
        Assert.assertEquals(new Response<>(0, "No Content by this id", null), result);
    }

    @Test
    public void testFindAssuranceById2() {
        UUID id = UUID.randomUUID();
        Assurance assurance = new Assurance(id.toString(), null, null);
        Mockito.when(assuranceRepository.findById(id.toString())).thenReturn(java.util.Optional.of(assurance));
        Response result = assuranceServiceImpl.findAssuranceById(id, headers);
        Assert.assertEquals(new Response<>(1, "Find Assurance Success", java.util.Optional.of(assurance)), result);
    }

    @Test
    public void testFindAssuranceByOrderId1() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(assuranceRepository.findByOrderId(orderId.toString())).thenReturn(null);
        Response result = assuranceServiceImpl.findAssuranceByOrderId(orderId, headers);
        Assert.assertEquals(new Response<>(0, "No Content by this orderId", null), result);
    }

    @Test
    public void testFindAssuranceByOrderId2() {
        UUID orderId = UUID.randomUUID();
        Assurance assurance = new Assurance(null, orderId.toString(), null);
        Mockito.when(assuranceRepository.findByOrderId(orderId.toString())).thenReturn(assurance);
        Response result = assuranceServiceImpl.findAssuranceByOrderId(orderId, headers);
        Assert.assertEquals(new Response<>(1, "Find Assurance Success", assurance), result);
    }

    @Test
    public void testCreate1() {
        UUID orderId = UUID.randomUUID();
        Assurance assurance = new Assurance();
        Mockito.when(assuranceRepository.findByOrderId(orderId.toString())).thenReturn(assurance);
        Response result = assuranceServiceImpl.create(0, orderId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "Fail.Assurance already exists", null), result);
    }

    @Test
    public void tesCreate2() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(assuranceRepository.findByOrderId(orderId.toString())).thenReturn(null);
        Response result = assuranceServiceImpl.create(0, orderId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "Fail.Assurance type doesn't exist", null), result);
    }

    @Test
    public void testCreate3() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(assuranceRepository.findByOrderId(orderId.toString())).thenReturn(null);
        Mockito.when(assuranceRepository.save(Mockito.any(Assurance.class))).thenReturn(null);
        Response result = assuranceServiceImpl.create(1, orderId.toString(), headers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testDeleteById1() {
        UUID assuranceId = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(assuranceRepository).deleteById(assuranceId.toString());
        Mockito.when(assuranceRepository.findById(assuranceId.toString())).thenReturn(null);
        Response result = assuranceServiceImpl.deleteById(assuranceId, headers);
        Assert.assertEquals(new Response<>(1, "Delete Success with Assurance id", null), result);
    }

    @Test
    public void testDeleteById2() {
        UUID assuranceId = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(assuranceRepository).deleteById(assuranceId.toString());
        Assurance assurance = new Assurance();
        Mockito.when(assuranceRepository.findById(assuranceId.toString())).thenReturn(java.util.Optional.of(assurance));
        Response result = assuranceServiceImpl.deleteById(assuranceId, headers);
        Assert.assertEquals(new Response<>(0, "Fail.Assurance not clear", assuranceId), result);
    }

    @Test
    public void testDeleteByOrderId1() {
        UUID orderId = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(assuranceRepository).removeAssuranceByOrderId(orderId.toString());
        Mockito.when(assuranceRepository.findByOrderId(orderId.toString())).thenReturn(null);
        Response result = assuranceServiceImpl.deleteByOrderId(orderId, headers);
        Assert.assertEquals(new Response<>(1, "Delete Success with Order Id", null), result);
    }

    @Test
    public void testDeleteByOrderId2() {
        UUID orderId = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(assuranceRepository).removeAssuranceByOrderId(orderId.toString());
        Assurance assurance = new Assurance();
        Mockito.when(assuranceRepository.findByOrderId(orderId.toString())).thenReturn(assurance);
        Response result = assuranceServiceImpl.deleteByOrderId(orderId, headers);
        Assert.assertEquals(new Response<>(0, "Fail.Assurance not clear", orderId), result);
    }

    @Test
    public void testModify2() {
        UUID assuranceId = UUID.randomUUID();
        Assurance assurance = new Assurance(null, null, null);
        Mockito.when(assuranceRepository.findById(assuranceId.toString())).thenReturn(java.util.Optional.of(assurance));
        Mockito.when(assuranceRepository.save(assurance)).thenReturn(null);
        Response result = assuranceServiceImpl.modify(assuranceId.toString(), "orderId", 1, headers);
        Assert.assertEquals(new Response<>(1, "Modify Success", new Assurance(null, null, AssuranceType.TRAFFIC_ACCIDENT)), result);
    }

    @Test
    public void testModify3() {
        UUID assuranceId = UUID.randomUUID();
        Assurance assurance = new Assurance(null, null, null);
        Mockito.when(assuranceRepository.findById(assuranceId.toString())).thenReturn(java.util.Optional.of(assurance));
        Mockito.when(assuranceRepository.save(assurance)).thenReturn(null);
        Response result = assuranceServiceImpl.modify(assuranceId.toString(), "orderId", 0, headers);
        Assert.assertEquals(new Response<>(0, "Assurance Type not exist", null), result);
    }

    @Test
    public void testGetAllAssurances1() {
        ArrayList<Assurance> assuranceList = new ArrayList<>();
        assuranceList.add(new Assurance(null, null, AssuranceType.TRAFFIC_ACCIDENT));
        ArrayList<PlainAssurance> plainAssuranceList = new ArrayList<>();
        plainAssuranceList.add(new PlainAssurance(null, null, 1, "Traffic Accident Assurance", 3.0));
        Mockito.when(assuranceRepository.findAll()).thenReturn(assuranceList);
        Response result = assuranceServiceImpl.getAllAssurances(headers);
        Assert.assertEquals(new Response<>(1, "Success", plainAssuranceList), result);
    }

    @Test
    public void testGetAllAssurances2() {
        Mockito.when(assuranceRepository.findAll()).thenReturn(null);
        Response result = assuranceServiceImpl.getAllAssurances(headers);
        Assert.assertEquals(new Response<>(0, "No Content, Assurance is empty", null), result);
    }

    @Test
    public void testGetAllAssuranceTypes() {
        List<AssuranceTypeBean> assuranceTypeBeanList = new ArrayList<>();
        AssuranceTypeBean assuranceTypeBean = new AssuranceTypeBean(1, "Traffic Accident Assurance", 3.0);
        assuranceTypeBeanList.add(assuranceTypeBean);
        Response result = assuranceServiceImpl.getAllAssuranceTypes(headers);
        Assert.assertEquals(new Response<>(1, "Find All Assurance", assuranceTypeBeanList), result);
    }

}
