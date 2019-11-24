package travelto.config; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/** 
* SecurityConfig Tester. 
* 
* @author <Authors name> 
* @since <pre>11月 24, 2019</pre> 
* @version 1.0 
*/ 
public class SecurityConfigTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: passwordEncoder() 
* 
*/ 
@Test
public void testPasswordEncoder() throws Exception {
    SecurityConfig sc =new SecurityConfig();
    sc.passwordEncoder();

//TODO: Test goes here... 
} 

/** 
* 
* Method: corsConfigurer() 
* 
*/ 
@Test
public void testCorsConfigurer() throws Exception {
    SecurityConfig sc =new SecurityConfig();
    sc.corsConfigurer().addCorsMappings(new CorsRegistry());

//TODO: Test goes here...

} 

/** 
* 
* Method: configure(HttpSecurity httpSecurity) 
* 
*/ 
@Test
public void testConfigure() throws Exception { 
//TODO: Test goes here...
    SecurityConfig sc =new SecurityConfig();
    //sc.configure(new HttpSecurity());//参数给不出
}

/** 
* 
* Method: authenticate(Authentication authentication) 
* 
*/ 
@Test
public void testAuthenticate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: loadUserByUsername(String username) 
* 
*/ 
@Test
public void testLoadUserByUsername() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getAuthenticationManagerBeanNames(ApplicationContext applicationContext) 
* 
*/ 
@Test
public void testGetAuthenticationManagerBeanNames() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SecurityConfig.getClass().getMethod("getAuthenticationManagerBeanNames", ApplicationContext.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: validateBeanCycle(Object auth, Set<String> beanNames) 
* 
*/ 
@Test
public void testValidateBeanCycle() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SecurityConfig.getClass().getMethod("validateBeanCycle", Object.class, Set<String>.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
