package com.learning.capstone.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/*
 * The shape the API accepts. NOT the entity — the entity's shape
 * (FK objects, @ManyToOne, generated id) is not what callers want
 * to send.
 */
public record PlaceOrderRequest(
    @NotNull Long userId,
    @NotNull Long productId,
    @NotNull @Min(1) Integer quantity
) { }
