package com.learning.hibernate;

import com.learning.hibernate.model.User;
import com.learning.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/*
 * Phase 2 equivalent:
 *
 *     ps.setString(1, "Frank");
 *     ps.setString(2, "frank@example.com");
 *     ps.executeUpdate();
 *
 * Hibernate equivalent:
 *
 *     session.persist(user);     // Hibernate generates the INSERT
 *
 * The auto-generated id is written back into user.id after commit
 * — same effect as Statement.RETURN_GENERATED_KEYS in Phase 2,
 * but you didn't have to ask.
 */
public class Step1_SaveEntity {

    public static void main(String[] args) {
        User u = new User("Frank Carter", "frank@example.com");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(u);          // queues an INSERT
            // No id yet at THIS point necessarily — Hibernate may delay
            // the actual SQL until flush/commit. For IDENTITY strategy
            // the INSERT runs immediately so id IS populated.

            tx.commit();                 // SQL is flushed here at the latest

            System.out.println("Saved: " + u);  // id is now set
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
