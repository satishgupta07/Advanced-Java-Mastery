package com.learning.springjdbc;

import com.learning.springjdbc.config.AppConfig;
import com.learning.springjdbc.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Step 4: @Transactional in action.
 *
 * Two scenarios:
 *
 *   1. placeOrder(1, 1, 1) — happy path. Stock decrement + order insert
 *      both committed in ONE transaction.
 *
 *   2. placeOrder(1, 1, 99999) — insufficient stock. The method throws,
 *      the tx rolls back, the (already-flushed) UPDATE is undone too.
 *
 * Inspect the orders table after each scenario to confirm.
 */
public class Step4_TransactionalService {

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {

            OrderService orders = ctx.getBean(OrderService.class);

            // ---- Happy path ----
            try {
                orders.placeOrder(1L, 1L, 1);
                System.out.println("Order placed.");
            } catch (RuntimeException e) {
                System.out.println("Order failed: " + e.getMessage());
            }

            // ---- Rollback path ----
            try {
                orders.placeOrder(1L, 1L, 99999);
                System.out.println("This should not print.");
            } catch (RuntimeException e) {
                System.out.println("Rolled back as expected: " + e.getMessage());
            }
        }
    }
}
