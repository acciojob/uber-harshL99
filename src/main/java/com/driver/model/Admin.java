package com.driver.model;

import javax.persistence.*;

@Entity
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true,nullable = false,updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public Admin(){

    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}