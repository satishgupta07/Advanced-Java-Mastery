package com.learning.springjdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * The Phase 2 transaction example, rewritten with @Transactional.
 *
 * Phase 2 needed:
 *   connection.setAutoCommit(false);
 *   ... two updates ...
 *   if (success) connection.commit();
 *   else         connection.rollback();
 *
 * @Transactional collapses all of that into ONE annotation. Spring's
 * AOP proxy:
 *   - opens a tx before placeOrder() runs
 *   - commits if the method returns normally
 *   - rolls back if it throws an unchecked exception
 *
 * IMPORTANT: by default, ONLY runtime exceptions trigger rollback.
 * For checked exceptions, use @Transactional(rollbackFor = SomeChecked.class).
 */
@Service
public class OrderService {

    private final JdbcTemplate jdbc;

    @Autowired
    public OrderService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    public void placeOrder(long userId, long productId, int qty) {
        int updated = jdbc.update(
            "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?",
            qty, productId, qty);

        if (updated == 0) {
            // Throwing aborts the tx — both statements are rolled back.
            throw new IllegalStateException("Insufficient stock for product " + productId);
        }

        jdbc.update(
            "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?)",
            userId, productId, qty);
    }
}
