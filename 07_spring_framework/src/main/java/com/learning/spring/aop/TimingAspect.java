package com.learning.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
 * A tiny aspect that times every public method on @Service beans.
 *
 *   @Aspect       - "this class contains advice (cross-cutting code)"
 *   @Component    - register the aspect itself as a Spring bean
 *   @Around(...)  - run BEFORE and AFTER each matched method call
 *
 *   pjp.proceed() - call the original method (and capture its result)
 *
 * The expression `execution(* com.learning.spring.service..*(..))` is
 * a POINTCUT — a pattern matching method signatures:
 *
 *      execution( * com.learning.spring.service..*(..) )
 *                 ↑               ↑                 ↑
 *               any return type   any class/method  any args
 *
 * One aspect, applied automatically to every service method without
 * touching their code. This is the model behind @Transactional,
 * @PreAuthorize, @Cacheable — they're all Spring AOP advice.
 */
@Aspect
@Component
public class TimingAspect {

    @Around("execution(* com.learning.spring.service..*(..))")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();           // run the actual method
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            System.out.printf("[AOP] %s took %d ms%n",
                pjp.getSignature().toShortString(), ms);
        }
    }
}
