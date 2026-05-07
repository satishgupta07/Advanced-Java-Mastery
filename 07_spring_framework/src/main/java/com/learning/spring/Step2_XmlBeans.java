package com.learning.spring;

import com.learning.spring.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * Step 2: XML-based wiring (legacy, still works).
 *
 *   1. Spring reads applicationContext.xml from the classpath.
 *   2. For each <bean>, it instantiates the class and stores the
 *      instance in the ApplicationContext (a Map-like registry).
 *   3. <constructor-arg ref="..."> tells Spring to inject one bean
 *      into another.
 *
 * Compare with Step 1: main() no longer knows about UserRepository.
 * It only asks for UserService, and Spring assembles the graph
 * behind the scenes.
 */
public class Step2_XmlBeans {

    public static void main(String[] args) {
        // The ApplicationContext IS the IoC container.
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserService service = ctx.getBean(UserService.class);
        service.register("Eve");
        System.out.println("Users: " + service.listAll());

        // ((ConfigurableApplicationContext) ctx).close();   // good hygiene in real apps
    }
}
