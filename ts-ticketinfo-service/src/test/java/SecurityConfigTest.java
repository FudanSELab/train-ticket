import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ticketinfo.config.SecurityConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class Demo extends SecurityConfig {
    public boolean A() {
        passwordEncoder();
        corsConfigurer().addCorsMappings(new CorsRegistry());
        try {
            configure(getHttp());
        } catch (Exception e) {
            return false;}
        return true;
    }
}

public class SecurityConfigTest extends SecurityConfig{
    Demo demo;

    @Before
    public void before() {
        demo = new Demo();
    }

    @Test
    public void test() {
        demo.A();
    }
}
