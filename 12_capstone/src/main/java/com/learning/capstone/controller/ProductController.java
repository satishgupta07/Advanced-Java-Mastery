package com.learning.capstone.controller;

import com.learning.capstone.model.Product;
import com.learning.capstone.repository.ProductRepository;
import com.learning.capstone.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Product> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Product one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(
            () -> new OrderService.NotFoundException("Product " + id + " not found"));
    }
}
