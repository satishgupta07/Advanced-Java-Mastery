package com.learning.hibernateadv;

import com.learning.hibernateadv.model.Order;
import com.learning.hibernateadv.model.User;
import com.learning.hibernateadv.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/*
 * Step 3: the N+1 problem — demonstrated.
 *
 * Run this and watch the SQL log. You will see:
 *
 *     1   x   SELECT ... FROM users
 *     N   x   SELECT ... FROM orders WHERE user_id = ?
 *     N×M x   SELECT ... FROM products WHERE id = ?
 *
 * The first query gets all users. The second is fired by the call to
 * u.getOrders() inside the loop — once per user. The third is fired by
 * o.getProduct().getName() — once per order.
 *
 * For 100 users with 5 orders each that touches the product name,
 * you've made 1 + 100 + 500 = 601 round-trips for what should be ONE
 * join. Step 4 fixes this.
 *
 * The SHAPE of the bug is: lazy fetch + iteration in app code.
 * Lazy is not bad — using it inside a loop is.
 */
public class Step3_LazyVsEager {

    public static void main(String[] args) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            // 1 query — fetches the user rows only
            List<User> users = s.createQuery("FROM User", User.class).list();

            for (User u : users) {
                // Each iteration: 1 query for orders + 1 query per product touched
                int count = u.getOrders().size();
                System.out.printf("%-15s — %d order(s)%n", u.getName(), count);
            }

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
