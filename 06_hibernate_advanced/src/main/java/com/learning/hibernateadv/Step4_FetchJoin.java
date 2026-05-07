package com.learning.hibernateadv;

import com.learning.hibernateadv.model.User;
import com.learning.hibernateadv.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/*
 * Step 4: fix the N+1 with JOIN FETCH.
 *
 *     SELECT DISTINCT u
 *     FROM User u
 *     LEFT JOIN FETCH u.orders o
 *     LEFT JOIN FETCH o.product
 *
 * One query. One round-trip. Hibernate emits a single SELECT with
 * joins to users, orders, and products. The graph comes back fully
 * populated, so iterating user.getOrders() in app code fires NO
 * additional queries.
 *
 * Compare the SQL log with Step 3 — that's the N+1 fix in action.
 *
 * Why DISTINCT? With a fetch-join, each row in the result set
 * represents a User × Order pair, so a User with 3 orders appears
 * 3 times. DISTINCT collapses the duplicates at the entity level.
 */
public class Step4_FetchJoin {

    public static void main(String[] args) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            String hql =
                "SELECT DISTINCT u FROM User u " +
                "LEFT JOIN FETCH u.orders o " +
                "LEFT JOIN FETCH o.product";

            List<User> users = s.createQuery(hql, User.class).list();

            for (User u : users) {
                System.out.printf("%-15s%n", u.getName());
                u.getOrders().forEach(o ->
                    System.out.printf("    %d × %s%n",
                        o.getQuantity(),
                        o.getProduct().getName()));
            }

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
