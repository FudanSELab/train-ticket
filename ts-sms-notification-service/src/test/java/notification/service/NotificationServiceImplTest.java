package notification.service;

import notification.entity.Mail;
import notification.entity.NotifyInfo;
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

@RunWith(JUnit4.class)
public class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationServiceImpl;

    @Mock
    private MailService mailService;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPreserveSuccess1() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doNothing().doThrow(new RuntimeException()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.preserveSuccess(info, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testPreserveSuccess2() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doThrow(new Exception()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.preserveSuccess(info, headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testOrderCreateSuccess1() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doNothing().doThrow(new RuntimeException()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.orderCreateSuccess(info, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testOrderCreateSuccess2() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doThrow(new Exception()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.orderCreateSuccess(info, headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testOrderChangedSuccess1() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doNothing().doThrow(new RuntimeException()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.orderChangedSuccess(info, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testOrderChangedSuccess2() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doThrow(new Exception()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.orderChangedSuccess(info, headers);
        Assert.assertFalse(result);
    }

    @Test
    public void testOrderCancelSuccess1() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doNothing().doThrow(new RuntimeException()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.orderCancelSuccess(info, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testOrderCancelSuccess2() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.doThrow(new Exception()).when(mailService).sendEmail(Mockito.any(Mail.class), Mockito.anyString());
        boolean result = notificationServiceImpl.orderCancelSuccess(info, headers);
        Assert.assertFalse(result);
    }

}
