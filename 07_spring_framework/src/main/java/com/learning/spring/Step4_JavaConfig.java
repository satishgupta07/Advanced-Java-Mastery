package com.learning.spring;

import com.learning.spring.config.JavaConfig;
import com.learning.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 4: pure Java config — no XML, no @Component scanning, just
 * explicit @Bean methods.
 *
 * Why this style?
 *   - Construction is regular Java code: easy to debug, conditional logic,
 *     environment-specific overrides.
 *   - All beans visible in one file makes the wiring obvious.
 *   - Required for beans you can't annotate (third-party classes).
 *
 * Most production codebases mix Java config (for infrastructure beans
 * like DataSource, ObjectMapper) with @ComponentScan (for your own
 * @Service / @Repository / @Controller). Spring Boot does exactly that.
 */
public class Step4_JavaConfig {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(JavaConfig.class)) {

            UserService service = ctx.getBean(UserService.class);
            service.register("Grace");
            System.out.println("Users: " + service.listAll());
        }
    }
}
