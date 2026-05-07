package com.learning.hibernate;

import com.learning.hibernate.model.User;
import com.learning.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/*
 * Update via dirty checking, plus delete.
 *
 *   1. session.get(...) returns a MANAGED entity.
 *   2. Mutating its fields does NOT immediately fire SQL.
 *   3. On tx.commit(), Hibernate diffs current vs. snapshot,
 *      and emits an UPDATE for any changed columns.
 *
 * No session.update() call needed. The entity is "watched" for the
 * lifetime of the Session.
 *
 * For delete, session.remove(entity) marks it for DELETE, flushed at commit.
 *
 * This script is idempotent: it inserts a fresh user, updates them,
 * then deletes them — leaving the DB the way it found it.
 */
public class Step3_UpdateAndDelete {

    public static void main(String[] args) {
        Long id;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            // ---- Insert a temporary user ----
            Transaction tx = s.beginTransaction();
            User u = new User("Temp User", "temp_" + System.currentTimeMillis() + "@example.com");
            s.persist(u);
            tx.commit();
            id = u.getId();
            System.out.println("Inserted: " + u);
        }

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            // ---- Dirty-checked update ----
            Transaction tx = s.beginTransaction();
            User u = s.get(User.class, id);
            u.setName("Temp User (renamed)");                  // no save() needed
            tx.commit();                                       // UPDATE fires here
            System.out.println("Updated: " + u);
        }

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            // ---- Delete ----
            Transaction tx = s.beginTransaction();
            User u = s.get(User.class, id);
            s.remove(u);                                       // marks for DELETE
            tx.commit();
            System.out.println("Deleted user " + id);
        }

        HibernateUtil.shutdown();
    }
}
