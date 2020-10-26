package com.example.fptparkingproject.model;

public class ShareVehicle {
    private String shareid;
    private String username;
    private String userborrowname;
    private String userid;
    private String userborrowid;
    private Boolean status;
    private String plate;
    private int count;

    public ShareVehicle() {
    }

    public ShareVehicle(String shareid, String username, String userborrowname, String userid, String userborrowid, Boolean status, String plate, int count) {
        this.shareid = shareid;
        this.username = username;
        this.userborrowname = userborrowname;
        this.userid = userid;
        this.userborrowid = userborrowid;
        this.status = status;
        this.plate = plate;
        this.count = count;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getShareid() {
        return shareid;
    }

    public void setShareid(String shareid) {
        this.shareid = shareid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserborrowname() {
        return userborrowname;
    }

    public void setUserborrowname(String userborrowname) {
        this.userborrowname = userborrowname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserborrowid() {
        return userborrowid;
    }

    public void setUserborrowid(String userborrowid) {
        this.userborrowid = userborrowid;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
