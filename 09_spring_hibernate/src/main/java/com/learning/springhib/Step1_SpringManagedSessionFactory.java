package com.learning.springhib;

import com.learning.springhib.config.AppConfig;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 1: prove that Spring built the SessionFactory and we can fetch
 * it from the container. No HibernateUtil. No hibernate.cfg.xml.
 */
public class Step1_SpringManagedSessionFactory {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {

            SessionFactory sf = ctx.getBean(SessionFactory.class);
            System.out.println("SessionFactory bean: " + sf);
            System.out.println("Open sessions: " + sf.getStatistics().getSessionOpenCount());
        }
    }
}
