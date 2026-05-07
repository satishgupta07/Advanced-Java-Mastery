package com.learning.springhib.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/*
 * Wires: DataSource → SessionFactory → TransactionManager
 *
 * Why each bean is here:
 *
 *   DataSource              — connection pool. Hibernate uses it under the hood.
 *   LocalSessionFactoryBean — Spring's adapter that builds a SessionFactory
 *                             (replaces hibernate.cfg.xml + new Configuration().configure()).
 *   HibernateTransactionManager — bridges @Transactional to Session.beginTransaction().
 *                                 MUST use this one (not DataSourceTransactionManager)
 *                                 so currentSession() / tx binding works correctly.
 *
 * @EnableTransactionManagement turns on the AOP proxy for @Transactional.
 *
 * NOTE: spring-orm 6.x still ships the package as `hibernate5` for
 * historical reasons — it works with Hibernate 6.x just fine.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.learning.springhib")
public class AppConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:mysql://localhost:3306/learning_db");
        cfg.setUsername("learner");
        cfg.setPassword("learner_pw");
        cfg.setMaximumPoolSize(10);
        return new HikariDataSource(cfg);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(ds);
        sf.setPackagesToScan("com.learning.springhib.model");   // finds @Entity
        sf.setHibernateProperties(hibernateProperties());
        return sf;
    }

    @Bean
    public PlatformTransactionManager txManager(SessionFactory sf) {
        return new HibernateTransactionManager(sf);
    }

    private Properties hibernateProperties() {
        Properties p = new Properties();
        p.setProperty("hibernate.dialect",      "org.hibernate.dialect.MySQLDialect");
        p.setProperty("hibernate.show_sql",     "true");
        p.setProperty("hibernate.format_sql",   "true");
        p.setProperty("hibernate.hbm2ddl.auto", "validate");
        return p;
    }
}
