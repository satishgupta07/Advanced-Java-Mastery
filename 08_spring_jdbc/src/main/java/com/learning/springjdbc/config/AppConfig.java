package com.learning.springjdbc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/*
 * Three infrastructure beans that the rest of the project needs:
 *
 *   DataSource                — pooled JDBC connection factory (Hikari)
 *   JdbcTemplate              — the workhorse query helper
 *   PlatformTransactionManager — what @Transactional uses to begin/commit/rollback
 *
 * @EnableTransactionManagement turns on the AOP proxy that intercepts
 * @Transactional methods. Without it, @Transactional is silently
 * ignored — a famously confusing source of "my changes weren't
 * persisted!" bugs.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.learning.springjdbc")
public class AppConfig {

    @Bean(destroyMethod = "close")          // Spring calls close() on shutdown
    public DataSource dataSource() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:mysql://localhost:3306/learning_db");
        cfg.setUsername("learner");
        cfg.setPassword("learner_pw");
        cfg.setMaximumPoolSize(10);
        return new HikariDataSource(cfg);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean
    public NamedParameterJdbcTemplate namedJdbc(DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
