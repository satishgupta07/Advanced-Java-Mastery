package com.learning.hibernate;

import com.learning.hibernate.model.User;
import com.learning.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/*
 * HQL — Hibernate Query Language.
 *
 * It looks like SQL but operates on ENTITIES, not tables:
 *
 *     SQL:  SELECT * FROM users WHERE email = ?
 *     HQL:  FROM User WHERE email = :email
 *                ↑
 *                class name (not table name)
 *
 *  - SELECT clause is OPTIONAL when you want the whole entity.
 *  - Property names are FIELD names (case-sensitive), not column names.
 *  - Named parameters (`:email`) replace JDBC's positional `?`.
 *
 *  Hibernate translates HQL to dialect-specific SQL at runtime.
 *  Switch from MySQL to PostgreSQL → only the dialect changes; HQL stays.
 */
public class Step4_HQL {

    public static void main(String[] args) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            // ---- Find all ----
            List<User> all = s.createQuery("FROM User", User.class).list();
            System.out.println("All users: " + all.size());
            all.forEach(System.out::println);

            // ---- Where + named parameter ----
            Query<User> q = s.createQuery(
                "FROM User u WHERE u.email = :email", User.class);
            q.setParameter("email", "alice@example.com");
            User alice = q.uniqueResult();
            System.out.println("Alice: " + alice);

            // ---- Aggregate ----
            Long count = s.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                          .uniqueResult();
            System.out.println("Total users: " + count);

            // ---- Pagination ----
            List<User> page = s.createQuery("FROM User ORDER BY id", User.class)
                               .setFirstResult(0)
                               .setMaxResults(2)
                               .list();
            System.out.println("First 2: " + page);

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
