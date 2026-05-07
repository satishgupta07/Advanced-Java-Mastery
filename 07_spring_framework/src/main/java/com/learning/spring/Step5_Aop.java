package com.learning.spring;

import com.learning.spring.config.AnnotationConfig;
import com.learning.spring.config.JavaConfig;
import com.learning.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 5: AOP in action.
 *
 * We boot Spring with BOTH JavaConfig (which has @EnableAspectJAutoProxy)
 * and AnnotationConfig (which @ComponentScans the aop package, picking up
 * TimingAspect). Calling any method on UserService now also prints a
 * "[AOP] ... took N ms" line — without UserService knowing anything
 * about timing.
 *
 * This is the entire mechanism behind:
 *   @Transactional — wraps the method in a TX begin/commit
 *   @PreAuthorize  — checks an ACL before the method runs
 *   @Cacheable     — returns a cached value, skipping the method
 *
 * They're all aspects matching pointcut expressions over your code.
 */
public class Step5_Aop {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(
                JavaConfig.class, AnnotationConfig.class)) {

            UserService service = ctx.getBean(UserService.class);

            // Each call below triggers TimingAspect.time(...)
            service.register("Hank");
            service.listAll();
        }
    }
}
