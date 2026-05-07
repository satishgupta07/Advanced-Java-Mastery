package com.learning.hibernateadv;

import com.learning.hibernateadv.model.Order;
import com.learning.hibernateadv.model.Product;
import com.learning.hibernateadv.model.User;
import com.learning.hibernateadv.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/*
 * Step 1: @OneToMany — load a User, walk through their Orders.
 *
 * Because User.orders is mapped with cascade=ALL, we could ALSO add
 * brand-new Orders to user.getOrders() and just call session.persist(user)
 * — Hibernate would cascade the persist down to the new Orders. We're
 * just READING here; cascading is shown in the comments.
 */
public class Step1_OneToMany {

    public static void main(String[] args) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();

            User u = s.get(User.class, 1L);
            System.out.println("User: " + u.getName());

            // Touching getOrders() triggers the lazy collection load
            // (a SELECT against orders WHERE user_id = ?)
            for (Order o : u.getOrders()) {
                // Touching o.getProduct().getName() triggers ANOTHER lazy load
                // — see Step 3 for the N+1 problem this creates at scale.
                System.out.printf("  - %d × %s%n",
                    o.getQuantity(),
                    o.getProduct().getName());
            }

            tx.commit();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
