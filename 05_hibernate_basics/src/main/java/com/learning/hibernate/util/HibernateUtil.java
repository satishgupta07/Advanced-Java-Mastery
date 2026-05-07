package com.learning.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
 * SessionFactory is heavyweight (it parses config, builds metadata,
 * sets up a connection pool). You build it ONCE per application and
 * reuse it for every request.
 *
 * Sessions, by contrast, are short-lived and per-unit-of-work — open,
 * use, close.
 *
 * The classic pattern: a single static SessionFactory, lazily built.
 * Spring/Spring Boot give you the factory as a managed bean instead,
 * but the underlying object is the same.
 */
public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = build();

    private HibernateUtil() { }

    private static SessionFactory build() {
        try {
            // Reads hibernate.cfg.xml from the classpath
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable t) {
            // If this fails, every later step will fail too — fail fast and loud.
            throw new ExceptionInInitializerError(t);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}
