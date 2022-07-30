package waitorder.config;

import edu.fudan.common.security.jwt.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * @author fdse
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    String admin = "ADMIN";
    String order = "/api/v1/orderservice/order";

    /**
     * load password encoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * allow cors domain
     * header  By default, only six fields can be taken from the header, and the other fields can only be specified in the header.
     * credentials   Cookies are not sent by default and can only be true if a Cookie is needed
     * Validity of this request
     *
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALL)
                        .allowedMethods(ALL)
                        .allowedHeaders(ALL)
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable()
                // close default csrf
                .csrf().disable()
                // close session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, order).hasAnyRole(admin, "USER")
                .antMatchers(HttpMethod.PUT, order).hasAnyRole(admin, "USER")
                .antMatchers(HttpMethod.DELETE, order).hasAnyRole(admin, "USER")
                .antMatchers(HttpMethod.POST, "/api/v1/orderservice/order/admin").hasAnyRole(admin)
                .antMatchers(HttpMethod.PUT, "/api/v1/orderservice/order/admin").hasAnyRole(admin)
                .antMatchers("/api/v1/orderservice/order/**").permitAll()
                .antMatchers("/swagger-ui.html", "/webjars/**", "/images/**",
                        "/configuration/**", "/swagger-resources/**", "/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        // close cache
        httpSecurity.headers().cacheControl();
    }
}
