package notification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author fdse
 */
@Component
@Configuration
public class EmailConfig {

    @Autowired
    EmailProperties emailProperties;




}
