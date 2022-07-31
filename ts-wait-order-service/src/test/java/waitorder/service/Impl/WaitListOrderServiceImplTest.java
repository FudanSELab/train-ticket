package waitorder.service.Impl;

import java.util.Optional;
import edu.fudan.common.util.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import waitorder.entity.WaitListOrder;
import waitorder.repository.WaitListOrderRepository;


@RunWith(JUnit4.class)
public class WaitListOrderServiceImplTest {

    @InjectMocks
    private WaitListOrderServiceImpl waitListOrderServiceImpl;

    @Mock
    private WaitListOrderRepository repository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findOrderById() {

        Mockito.when(repository.findById(Mockito.any(String.class))).thenReturn(Optional.ofNullable(null));
        Response response=waitListOrderServiceImpl.findOrderById("id",headers);
        Assertions.assertEquals(new Response<>(0, "No Content by this id", null), response);
    }

    @Test
    public void getAllOrders() {
        Mockito.when(repository.findAll()).thenReturn(null);
        Response res = waitListOrderServiceImpl.getAllOrders(headers);
        Assertions.assertEquals(new Response<>(0,"No Content.",null),res);
    }


}