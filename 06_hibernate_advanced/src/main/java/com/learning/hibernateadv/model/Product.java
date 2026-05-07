package com.learning.hibernateadv.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;

/*
 * Product is referenced from Order via @ManyToOne. From Product's
 * perspective the inverse side (the @OneToMany list of Orders) isn't
 * needed — leave it out unless your app actually needs to navigate
 * from a Product to its Orders. Smaller graph = simpler code.
 *
 * @Cacheable + @Cache : Step 5's 2nd-level cache demo.
 *   Products are read-mostly reference data — perfect cache fit.
 *   READ_WRITE strategy: safe under concurrent writes (uses soft locks).
 */
@Entity
@Table(name = "products")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    public Product() { }
    public Product(String name, BigDecimal price, Integer stock) {
        this.name = name; this.price = price; this.stock = stock;
    }

    public Long getId()              { return id; }
    public String getName()          { return name; }
    public BigDecimal getPrice()     { return price; }
    public Integer getStock()        { return stock; }
    public void setStock(Integer s)  { this.stock = s; }

    @Override public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + "}";
    }
}
