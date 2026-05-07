package com.learning.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * The whole boot stage in seven lines.
 *
 * @SpringBootApplication is shorthand for:
 *
 *     @Configuration       — this class can declare @Bean methods
 *     @EnableAutoConfiguration  — turn auto-config on (the magic)
 *     @ComponentScan       — scan THIS package and below for @Component / @Service / @Controller / @Repository
 *
 * SpringApplication.run(...) starts an embedded Tomcat, builds the
 * ApplicationContext, runs all auto-config, exposes /api/users, and
 * blocks until you hit Ctrl-C. No WAR, no external server.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
