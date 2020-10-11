package com.example.fptparckingproject.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userid;
    private String username;
    private String email;
    private String token;

    public User(String userid, String username, String email, String token) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
