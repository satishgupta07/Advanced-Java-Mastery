package com.learning.springjdbc.model;

/*
 * Plain POJO. Spring JDBC doesn't care about annotations — it builds
 * Users from ResultSet rows via the RowMapper YOU supply, not via
 * reflection over a JPA-style @Entity.
 */
public class User {

    private Long id;
    private String name;
    private String email;

    public User() { }
    public User(Long id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }
    public User(String name, String email) {
        this.name = name; this.email = email;
    }

    public Long getId()           { return id; }
    public void setId(Long id)    { this.id = id; }
    public String getName()       { return name; }
    public void setName(String n) { this.name = n; }
    public String getEmail()      { return email; }
    public void setEmail(String e){ this.email = e; }

    @Override public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
