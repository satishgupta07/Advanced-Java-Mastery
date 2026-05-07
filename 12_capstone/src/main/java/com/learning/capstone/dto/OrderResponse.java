package com.learning.capstone.dto;

import com.learning.capstone.model.Order;

import java.time.Instant;

/*
 * The shape returned to API callers — flat, predictable, decoupled
 * from the JPA entity graph. No lazy-loading exceptions, no accidental
 * field leaks if the entity grows.
 */
public record OrderResponse(
    Long id,
    Long userId,
    String userName,
    Long productId,
    String productName,
    Integer quantity,
    Instant orderedAt
) {
    public static OrderResponse from(Order o) {
        return new OrderResponse(
            o.getId(),
            o.getUser().getId(),
            o.getUser().getName(),
            o.getProduct().getId(),
            o.getProduct().getName(),
            o.getQuantity(),
            o.getOrderedAt()
        );
    }
}
