package com.learning.springhib;

import com.learning.springhib.config.AppConfig;
import com.learning.springhib.model.User;
import com.learning.springhib.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 2: end-to-end CRUD through a @Transactional service.
 *
 * Compare with Phase 5's Step3_UpdateAndDelete:
 *   - No try (Session s = ...)
 *   - No tx.beginTransaction() / tx.commit()
 *   - No HibernateUtil.shutdown()
 *
 * The service method IS the unit of work. Spring opens a session,
 * runs the body, commits, and closes — all driven by @Transactional.
 *
 * This is the same shape you'll see in Spring Boot apps in Phase 11
 * — minus AppConfig, which Spring Boot generates for you.
 */
public class Step2_TransactionalService {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {

            UserService service = ctx.getBean(UserService.class);

            // Each service call is one tx
            User u = service.register("Karen Lee",
                "karen_" + System.currentTimeMillis() + "@example.com");
            System.out.println("Registered: " + u);

            service.rename(u.getId(), "Karen L.");
            System.out.println("After rename: " + service.listAll().stream()
                .filter(x -> x.getId().equals(u.getId()))
                .findFirst().orElseThrow());
        }
    }
}
