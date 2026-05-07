package com.learning.capstone.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "ordered_at", insertable = false, updatable = false)
    private Instant orderedAt;

    public Order() { }
    public Order(User user, Product product, Integer quantity) {
        this.user = user; this.product = product; this.quantity = quantity;
    }

    public Long getId()             { return id; }
    public User getUser()           { return user; }
    public Product getProduct()     { return product; }
    public Integer getQuantity()    { return quantity; }
    public Instant getOrderedAt()   { return orderedAt; }
}
