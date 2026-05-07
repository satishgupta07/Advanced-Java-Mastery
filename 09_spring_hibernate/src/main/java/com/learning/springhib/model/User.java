package com.learning.springhib.model;

import jakarta.persistence.*;

/*
 * Same JPA-annotated User as Phase 5. The mapping doesn't change just
 * because Spring is now in charge of the SessionFactory — that's the
 * point of standardising on JPA annotations.
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

    public User() { }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId()              { return id; }
    public String getName()          { return name; }
    public void setName(String n)    { this.name = n; }
    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }

    @Override public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
