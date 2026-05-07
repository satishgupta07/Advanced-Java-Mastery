package com.learning.hibernateadv.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
 * User now has a list of Orders — the "one" side of one-to-many.
 *
 *   @OneToMany(mappedBy = "user", ...)
 *               ↑
 *               name of the field on the OWNING side (Order.user)
 *
 * mappedBy says: "I'm the inverse side. The FK lives on the Order
 * table; don't create a join table for me." Without it, Hibernate
 * would invent a `user_orders` join table and ignore the `user_id`
 * column we already have.
 *
 * cascade = ALL : operations on User propagate to its Orders.
 *                 Save a User → its new Orders are saved too.
 *                 Delete a User → its Orders are deleted too.
 *
 * orphanRemoval = true : remove an Order from the list → it's deleted.
 *                        Without this, the Order just gets its FK nulled.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public User() { }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId()           { return id; }
    public String getName()       { return name; }
    public void setName(String n) { this.name = n; }
    public String getEmail()      { return email; }
    public void setEmail(String e){ this.email = e; }
    public List<Order> getOrders(){ return orders; }

    // Bidirectional helper — keep BOTH sides in sync. Always.
    public void addOrder(Order o) {
        orders.add(o);
        o.setUser(this);
    }

    @Override public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}
