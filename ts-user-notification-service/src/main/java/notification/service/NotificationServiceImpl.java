package notification.service;

import notification.entity.Mail;
import notification.entity.NotifyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fdse
 */
@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    MailService mailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    String email = "trainticket_notify@163.com";
    String username = "username";
    String startPlace = "startPlace";
    String endPlace = "endPlace";
    String startTime = "startTime";
    String seatClass = "seatClass";
    String seatNumber = "seatNumber";

    @Override
    public boolean preserveSuccess(NotifyInfo info, HttpHeaders headers){
        Mail mail = new Mail();
        mail.setMailFrom(email);
        mail.setMailTo(info.getEmail());
        mail.setMailSubject("Preserve Success");

        Map<String, Object> model = new HashMap<>();
        model.put(username, info.getUsername());
        model.put(startPlace,info.getStartPlace());
        model.put(endPlace,info.getEndPlace());
        model.put(startTime,info.getStartTime());
        model.put("date",info.getDate());
        model.put(seatClass,info.getSeatClass());
        model.put(seatNumber,info.getSeatNumber());
        model.put("price",info.getPrice());
        mail.setModel(model);

        try {
            mailService.sendEmail(mail,"preserve_success.ftl");
            return true;
        } catch (Exception e) {
            LOGGER.error("[preserveSuccess][mailService.sendEmai][Exception: {}]", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean orderCreateSuccess(NotifyInfo info, HttpHeaders headers){
        Mail mail = new Mail();
        mail.setMailFrom(email);
        mail.setMailTo(info.getEmail());
        mail.setMailSubject("Order Create Success");

        Map<String, Object> model = new HashMap<>();
        model.put(username, info.getUsername());
        model.put(startPlace,info.getStartPlace());
        model.put(endPlace,info.getEndPlace());
        model.put(startTime,info.getStartTime());
        model.put("date",info.getDate());
        model.put(seatClass,info.getSeatClass());
        model.put(seatNumber,info.getSeatNumber());
        model.put("orderNumber", info.getOrderNumber());
        mail.setModel(model);

        try {
            mailService.sendEmail(mail,"order_create_success.ftl");
            return true;
        } catch (Exception e) {
            LOGGER.error("[orderCreateSuccess][mailService.sendEmai][Exception: {}]", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean orderChangedSuccess(NotifyInfo info, HttpHeaders headers){
        Mail mail = new Mail();
        mail.setMailFrom(email);
        mail.setMailTo(info.getEmail());
        mail.setMailSubject("Order Changed Success");

        Map<String, Object> model = new HashMap<>();
        model.put(username, info.getUsername());
        model.put(startPlace,info.getStartPlace());
        model.put(endPlace,info.getEndPlace());
        model.put(startTime,info.getStartTime());
        model.put("date",info.getDate());
        model.put(seatClass,info.getSeatClass());
        model.put(seatNumber,info.getSeatNumber());
        model.put("orderNumber", info.getOrderNumber());
        mail.setModel(model);

        try {
            mailService.sendEmail(mail,"order_changed_success.ftl");
            return true;
        } catch (Exception e) {
            LOGGER.error("[orderChangedSuccess][mailService.sendEmai][Exception: {}]", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean orderCancelSuccess(NotifyInfo info, HttpHeaders headers){
        Mail mail = new Mail();
        mail.setMailFrom(email);
        mail.setMailTo(info.getEmail());
        mail.setMailSubject("Order Cancel Success");

        Map<String, Object> model = new HashMap<>();
        model.put(username, info.getUsername());
        model.put("price",info.getPrice());
        mail.setModel(model);

        try {
            mailService.sendEmail(mail,"order_cancel_success.ftl");
            return true;
        } catch (Exception e) {
            LOGGER.error("[orderCancelSuccess][mailService.sendEmai][Exception: {}]", e.getMessage());
            return false;
        }
    }
}
