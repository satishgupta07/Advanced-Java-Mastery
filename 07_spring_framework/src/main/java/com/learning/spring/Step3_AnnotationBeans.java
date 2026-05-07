package com.learning.spring;

import com.learning.spring.config.AnnotationConfig;
import com.learning.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 3: discover beans via @ComponentScan, wire them via @Autowired.
 *
 *   - No XML.
 *   - No <bean> declarations.
 *   - Spring scans `com.learning.spring`, finds @Component-annotated
 *     classes (UserRepository, UserService), instantiates them, and
 *     wires constructor-injected dependencies automatically.
 *
 * This is the most common Spring style outside Spring Boot.
 */
public class Step3_AnnotationBeans {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AnnotationConfig.class)) {

            UserService service = ctx.getBean(UserService.class);
            service.register("Frank");
            System.out.println("Users: " + service.listAll());
        }
    }
}
