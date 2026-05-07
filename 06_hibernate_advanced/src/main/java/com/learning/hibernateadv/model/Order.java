package com.learning.hibernateadv.model;

import jakarta.persistence.*;

import java.time.Instant;

/*
 * Order is the OWNING side of two many-to-one relationships:
 *   - one User
 *   - one Product
 *
 * @ManyToOne(fetch = LAZY) — DEFAULT TO LAZY, always.
 *
 *   Eager fetching of associations is the #1 cause of accidental
 *   over-fetching: every time you load an Order, Hibernate fires a
 *   second query for the User even if you didn't ask. With LAZY,
 *   the User is fetched only when you call order.getUser().getName().
 *
 * @JoinColumn(name = "user_id") — names the FK column. Without it,
 * Hibernate would default to "user_id" anyway (class + "_id"), but
 * it's good practice to be explicit.
 */
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
    public Order(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId()             { return id; }
    public User getUser()           { return user; }
    public void setUser(User u)     { this.user = u; }
    public Product getProduct()     { return product; }
    public Integer getQuantity()    { return quantity; }
    public Instant getOrderedAt()   { return orderedAt; }

    @Override public String toString() {
        return "Order{id=" + id + ", quantity=" + quantity + "}";
    }
}
