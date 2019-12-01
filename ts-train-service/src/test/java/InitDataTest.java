import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import train.init.InitData;
import train.service.TrainService;

public class InitDataTest {
    @Mock
    private TrainService trainService;

    @InjectMocks
    private InitData initData;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void serInfoCreateTest() {
        try {
            initData.run("");
        } catch (Exception e) {}
    }
}
