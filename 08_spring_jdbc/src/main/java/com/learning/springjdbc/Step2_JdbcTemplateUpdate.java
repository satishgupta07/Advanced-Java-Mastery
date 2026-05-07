package com.learning.springjdbc;

import com.learning.springjdbc.config.AppConfig;
import com.learning.springjdbc.model.User;
import com.learning.springjdbc.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 2: INSERT / UPDATE / DELETE via jdbc.update(sql, args...).
 *
 * Returns the number of rows affected — same return value as
 * PreparedStatement.executeUpdate() in Phase 1, just with the
 * ceremony stripped away.
 *
 * Idempotent script: insert → update → delete → leaves DB unchanged.
 */
public class Step2_JdbcTemplateUpdate {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UserRepository repo = ctx.getBean(UserRepository.class);

            String email = "ivy_" + System.currentTimeMillis() + "@example.com";

            repo.save(new User("Ivy Patel", email));
            User saved = repo.findAll().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst().orElseThrow();
            System.out.println("Inserted: " + saved);

            saved.setName("Ivy P.");
            repo.update(saved);
            System.out.println("Updated : " + repo.findById(saved.getId()).orElseThrow());

            int deleted = repo.deleteById(saved.getId());
            System.out.println("Deleted : " + deleted + " row(s)");
        }
    }
}
