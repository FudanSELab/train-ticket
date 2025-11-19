package train.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import train.entity.TrainType;
import train.repository.TrainTypeRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TrainServiceImplTest {

    @InjectMocks
    private TrainServiceImpl trainServiceImpl;

    @Mock
    private TrainTypeRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate1() {
        TrainType trainType = sampleTrainType();
        when(repository.findByName(trainType.getName())).thenReturn(null);
        when(repository.save(any(TrainType.class))).thenReturn(trainType);
        boolean result = trainServiceImpl.create(trainType, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testCreate2() {
        TrainType trainType = sampleTrainType();
        when(repository.findByName(trainType.getName())).thenReturn(trainType);
        boolean result = trainServiceImpl.create(trainType, headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testRetrieve1() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        TrainType result = trainServiceImpl.retrieve("id", headers);
        Assert.assertNull(result);
    }

    @Test
    public void testRetrieve2() {
        TrainType trainType = sampleTrainType();
        when(repository.findById(anyString())).thenReturn(Optional.of(trainType));
        TrainType result = trainServiceImpl.retrieve("id", headers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdate1() {
        TrainType trainType = sampleTrainType();
        when(repository.findById(trainType.getId())).thenReturn(Optional.of(trainType));
        when(repository.save(any(TrainType.class))).thenReturn(trainType);
        boolean result = trainServiceImpl.update(trainType, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testUpdate2() {
        TrainType trainType = sampleTrainType();
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        boolean result = trainServiceImpl.update(trainType, headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testDelete1() {
        TrainType trainType = sampleTrainType();
        when(repository.findById(anyString())).thenReturn(Optional.of(trainType));
        boolean result = trainServiceImpl.delete("id", headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testDelete2() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        boolean result = trainServiceImpl.delete("id", headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testQuery() {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleTrainType()));
        Assert.assertNotNull(trainServiceImpl.query(headers));
    }

    private TrainType sampleTrainType() {
        TrainType type = new TrainType();
        type.setId("id");
        type.setName("G1");
        type.setEconomyClass(100);
        type.setConfortClass(50);
        type.setAverageSpeed(300);
        return type;
    }

}
