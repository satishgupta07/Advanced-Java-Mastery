package com.learning.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/*
 * Spring MVC's "wiring" lives here.
 *
 *   @EnableWebMvc            — turn on Spring MVC's defaults
 *                              (HandlerMapping, HandlerAdapter, message converters, etc.)
 *   @ComponentScan           — find @Controller beans
 *   InternalResourceViewResolver — translates a returned view name like "users"
 *                              into the file path /WEB-INF/views/users.jsp
 *
 *  Returning "users" from a controller method now becomes:
 *      prefix=/WEB-INF/views/  +  "users"  +  suffix=.jsp
 *      → /WEB-INF/views/users.jsp
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.learning.springmvc")
public class WebConfig {

    @org.springframework.context.annotation.Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/views/");
        vr.setSuffix(".jsp");
        return vr;
    }
}
