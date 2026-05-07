package com.learning.capstone.service;

import com.learning.capstone.dto.OrderResponse;
import com.learning.capstone.dto.PlaceOrderRequest;
import com.learning.capstone.model.Order;
import com.learning.capstone.model.Product;
import com.learning.capstone.model.User;
import com.learning.capstone.repository.OrderRepository;
import com.learning.capstone.repository.ProductRepository;
import com.learning.capstone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * The heart of the capstone.
 *
 * placeOrder() is the SAME problem from Phase 2 (raw JDBC with
 * setAutoCommit(false)) and Phase 8 (@Transactional + JdbcTemplate),
 * here in its final, clean form:
 *
 *   1. Fetch user (404 if missing).
 *   2. Fetch product WITH PESSIMISTIC LOCK (no oversell race).
 *   3. Validate stock.
 *   4. Decrement stock — dirty checking turns this into UPDATE on commit.
 *   5. Insert the new Order.
 *
 * Throwing IllegalStateException aborts the @Transactional block,
 * rolling back any in-flight changes. The GlobalExceptionHandler
 * turns the exception into a 409 response.
 */
@Service
public class OrderService {

    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public OrderService(UserRepository u, ProductRepository p, OrderRepository o) {
        this.userRepo = u; this.productRepo = p; this.orderRepo = o;
    }

    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest req) {
        User user = userRepo.findById(req.userId())
            .orElseThrow(() -> new NotFoundException("User " + req.userId() + " not found"));

        Product product = productRepo.findById(req.productId())
            .orElseThrow(() -> new NotFoundException("Product " + req.productId() + " not found"));

        if (product.getStock() < req.quantity()) {
            throw new IllegalStateException(
                "Insufficient stock for product " + product.getId() +
                " (have " + product.getStock() + ", need " + req.quantity() + ")");
        }

        product.setStock(product.getStock() - req.quantity());   // dirty-checked UPDATE on commit
        Order order = orderRepo.save(new Order(user, product, req.quantity()));

        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepo.findAllWithUserAndProduct().stream()
            .map(OrderResponse::from)
            .toList();
    }

    // Specific domain exception so the global handler can map it to 404.
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String msg) { super(msg); }
    }
}
