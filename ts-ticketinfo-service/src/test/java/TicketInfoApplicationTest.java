import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import ticketinfo.TicketInfoApplication;

public class TicketInfoApplicationTest {
    TicketInfoApplication ticketInfoApplication;

    @Before
    public void before() {
        ticketInfoApplication = new TicketInfoApplication();
    }

    @Test
    public void test() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        ticketInfoApplication.restTemplate(builder);
    }
}
