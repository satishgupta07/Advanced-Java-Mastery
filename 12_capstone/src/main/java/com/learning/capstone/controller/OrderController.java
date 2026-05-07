package com.learning.capstone.controller;

import com.learning.capstone.dto.OrderResponse;
import com.learning.capstone.dto.PlaceOrderRequest;
import com.learning.capstone.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) { this.service = service; }

    @GetMapping
    public List<OrderResponse> all() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<OrderResponse> place(@Valid @RequestBody PlaceOrderRequest req) {
        OrderResponse created = service.placeOrder(req);
        return ResponseEntity
            .created(URI.create("/api/orders/" + created.id()))
            .body(created);
    }
}
