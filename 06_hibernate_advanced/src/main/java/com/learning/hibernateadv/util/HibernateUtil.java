package com.learning.hibernateadv.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = build();

    private HibernateUtil() { }

    private static SessionFactory build() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    public static SessionFactory getSessionFactory() { return SESSION_FACTORY; }
    public static void shutdown() { SESSION_FACTORY.close(); }
}
