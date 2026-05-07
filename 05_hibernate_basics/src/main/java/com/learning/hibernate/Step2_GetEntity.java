package com.learning.hibernate;

import com.learning.hibernate.model.User;
import com.learning.hibernate.util.HibernateUtil;
import org.hibernate.Session;

/*
 * Primary-key fetch.
 *
 *   session.get(User.class, 1L)        — eager: hits the DB right now,
 *                                        returns null if not found.
 *
 *   session.getReference(User.class, 1L) — lazy: returns a proxy, NO SQL yet.
 *                                        SQL fires only when you touch a field.
 *                                        Throws ObjectNotFoundException if missing.
 *
 * Use get() unless you specifically need the proxy.
 */
public class Step2_GetEntity {

    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            User u = session.get(User.class, 1L);   // SELECT * FROM users WHERE id = 1
            if (u == null) {
                System.out.println("No user with id = 1");
            } else {
                System.out.println("Found: " + u);
            }

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
