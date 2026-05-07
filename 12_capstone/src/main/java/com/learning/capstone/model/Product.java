package com.learning.capstone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @NotNull @Min(0)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull @Min(0)
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
}
