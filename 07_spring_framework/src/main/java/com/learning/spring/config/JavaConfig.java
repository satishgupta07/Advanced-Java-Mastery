package com.learning.spring.config;

import com.learning.spring.repository.UserRepository;
import com.learning.spring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/*
 * Java config — explicit @Bean methods inside an @Configuration class.
 *
 *   - The same beans we got from @Component scanning, declared by hand.
 *   - Useful when the class you want as a bean ISN'T yours to annotate
 *     (third-party class) or when construction needs custom logic.
 *
 * @EnableAspectJAutoProxy turns on @Aspect support — needed by Step 5.
 *
 * Spring Boot's auto-config is, under the hood, hundreds of @Bean
 * methods like these — you just don't have to write them.
 */
@Configuration
@EnableAspectJAutoProxy
public class JavaConfig {

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public UserService userService(UserRepository repo) {
        // Spring resolves the parameter from the bean of matching type.
        return new UserService(repo);
    }
}
