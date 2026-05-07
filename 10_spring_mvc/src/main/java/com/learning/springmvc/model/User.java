package com.learning.springmvc.model;

/*
 * A POJO. For the JSON endpoint, Jackson serializes via the getters.
 * For the JSP view, EL ${u.name} calls the same getters.
 * One class, two output formats.
 */
public class User {

    private Long id;
    private String name;
    private String email;

    public User() { }
    public User(Long id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }

    public Long getId()              { return id; }
    public void setId(Long id)       { this.id = id; }
    public String getName()          { return name; }
    public void setName(String n)    { this.name = n; }
    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }
}
