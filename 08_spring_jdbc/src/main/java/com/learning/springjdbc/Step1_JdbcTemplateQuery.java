package com.learning.springjdbc;

import com.learning.springjdbc.config.AppConfig;
import com.learning.springjdbc.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 1: query with a RowMapper.
 *
 *   jdbc.query(sql, rowMapper)              → List<T>
 *   jdbc.queryForObject(sql, rowMapper, args) → T  (exactly 1 row)
 *   jdbc.queryForList(sql, Long.class)       → List<Long> (single column)
 *
 * The repository hides JdbcTemplate from the caller, so application
 * code reads clean: repo.findAll(), repo.findById(7).
 */
public class Step1_JdbcTemplateQuery {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {

            UserRepository repo = ctx.getBean(UserRepository.class);

            System.out.println("All users:");
            repo.findAll().forEach(u -> System.out.println("  " + u));

            System.out.println("User #1: " + repo.findById(1L).orElse(null));
            System.out.println("User #999: " + repo.findById(999L).orElse(null));
        }
    }
}
