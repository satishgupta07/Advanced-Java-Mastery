package com.learning.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * Annotation-driven config (Step 3 of the phase).
 *
 *   - @Configuration  : "this class declares Spring beans"
 *   - @ComponentScan  : "scan the given package(s) for @Component / @Service / @Repository"
 *
 * The XML <bean> entries are gone. Beans are discovered automatically
 * by their stereotype annotations:
 *
 *   @Component      generic bean
 *   @Service        a service-layer bean
 *   @Repository     a data-access bean
 *   @Controller     a web-controller bean (used in Spring MVC, Phase 10)
 *
 * All four are exactly the same to the container — the names just
 * communicate intent and can be matched separately by AOP pointcuts.
 */
@Configuration
@ComponentScan(basePackages = "com.learning.spring")
public class AnnotationConfig {
}
