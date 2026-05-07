package com.learning.jdbcadv.model;

/*
 * A POJO — Plain Old Java Object — representing one row from the users table.
 *
 * In raw JDBC we map by hand: read columns from a ResultSet, set fields here.
 * In Hibernate (Phase 5+) we will annotate this same shape and the framework
 * will do the mapping for us. Same target — different amount of plumbing.
 */
public class User {

    private Long id;
    private String name;
    private String email;

    public User() { }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId()              { return id; }
    public void setId(Long id)       { this.id = id; }
    public String getName()          { return name; }
    public void setName(String n)    { this.name = n; }
    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
