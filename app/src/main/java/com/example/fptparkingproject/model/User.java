package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class User implements Serializable {
    private String userid;
    private String username;
    private String email;
    private String token;
    private Boolean role;

    public User(String userid, String username, String email, String token, Boolean role, Constant constant) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
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

    //save user to sharedpreference
    public void saveUser(SharedPreferences prefs, User user) {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(new Constant().KEY_USER, json);
        editor.commit();
    }

    //get user from sharedpreference
    public User getUser(SharedPreferences prefs) {
        Gson gson = new Gson();
        String json = prefs.getString(new Constant().KEY_USER, null);
        return gson.fromJson(json, User.class);
    }




    public ArrayList<User> getListUser(SharedPreferences prefs) {
        Gson gson = new Gson();
        String json = prefs.getString(new Constant().KEY_USER, null);
        Type listType = new TypeToken<ArrayList<Newfeed>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public void saveListUser(SharedPreferences prefs, ArrayList<User> listArray) {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listArray);
        editor.putString(new Constant().KEY_USER, json);
        editor.commit();
    }

}
