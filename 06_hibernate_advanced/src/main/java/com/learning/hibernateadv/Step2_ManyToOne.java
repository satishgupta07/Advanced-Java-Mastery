package com.learning.hibernateadv;

import com.learning.hibernateadv.model.Order;
import com.learning.hibernateadv.model.Product;
import com.learning.hibernateadv.model.User;
import com.learning.hibernateadv.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/*
 * Step 2: creating an Order — the owning @ManyToOne side.
 *
 * Note the bidirectional helper user.addOrder(o):
 *
 *   public void addOrder(Order o) {
 *       orders.add(o);     // update inverse side
 *       o.setUser(this);   // update owning side
 *   }
 *
 * Forgetting to update BOTH sides is a common source of subtle bugs:
 *   - First-level cache shows different state than the DB.
 *   - Second SELECT in the same Session returns the new object
 *     missing from the parent's collection.
 *
 * Rule of thumb: ALWAYS set both ends of a bidirectional association.
 */
public class Step2_ManyToOne {

    public static void main(String[] args) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();

            User user      = s.get(User.class, 1L);
            Product product = s.get(Product.class, 1L);

            Order order = new Order(product, 3);
            user.addOrder(order);          // sets BOTH sides

            // Because User.orders has cascade=ALL, persisting the user
            // would persist the new Order too. Calling persist on the
            // Order directly is also fine and a bit more explicit:
            s.persist(order);

            tx.commit();
            System.out.println("Created order " + order.getId() +
                " for user " + user.getName() +
                " (product = " + product.getName() + ")");

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
