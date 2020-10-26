package com.example.fptparkingproject.model;

public class ShareTemp {
    private String username;
    private String userborrowid;
    private String userborrowname;
    private Boolean status;
    private String token;
    private String sendtoken;

    public ShareTemp() {
    }

    public ShareTemp(String username, String userborrowid, String userborrowname, Boolean status, String token, String sendtoken) {
        this.username = username;
        this.userborrowid = userborrowid;
        this.userborrowname = userborrowname;
        this.status = status;
        this.token = token;
        this.sendtoken = sendtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserborrowid() {
        return userborrowid;
    }

    public void setUserborrowid(String userborrowid) {
        this.userborrowid = userborrowid;
    }

    public String getUserborrowname() {
        return userborrowname;
    }

    public void setUserborrowname(String userborrowname) {
        this.userborrowname = userborrowname;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSendtoken() {
        return sendtoken;
    }

    public void setSendtoken(String sendtoken) {
        this.sendtoken = sendtoken;
    }
}
