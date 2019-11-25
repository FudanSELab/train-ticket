/**
 * @author shenxinyao
 * @date 2019/11/19
 */
package other.config;

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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * load password encoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * allow cors domain
     * header 在默认的情况下只能从头部取出6个字段，想要其他字段只能自己在头里指定
     * credentials 默认不发送Cookie, 如果需要Cookie,这个值只能为true
     * 本次请求检查的有效期
     *
     * @return
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
        String base = "/api/v1/orderOtherService/";
        String allPath = base + "orderOther";
        String adminPath = base + "admin";
        String admin = "ADMIN";
        String user = "USER";
        httpSecurity.httpBasic().disable()
                // close default csrf
                .csrf().disable()
                // close session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/orderOtherService/orderOther/**").permitAll()
                .antMatchers(HttpMethod.POST, allPath).hasAnyRole(admin, user)
                .antMatchers(HttpMethod.PUT, allPath).hasAnyRole(admin, user)
                .antMatchers(HttpMethod.DELETE, allPath).hasAnyRole(admin, user)
                .antMatchers(HttpMethod.POST, adminPath).hasAnyRole(admin)
                .antMatchers(HttpMethod.PUT, adminPath).hasAnyRole(admin)

                .antMatchers("/swagger-ui.html", "/webjars/**", "/images/**",
                        "/configuration/**", "/swagger-resources/**", "/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        // close cache
        httpSecurity.headers().cacheControl();
    }
}
