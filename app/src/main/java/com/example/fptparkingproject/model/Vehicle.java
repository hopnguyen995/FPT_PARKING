package com.example.fptparkingproject.model;

public class Vehicle {
    private String vehicleid;
    private String userid;
    private String username;
    private String license;
    private String plate;
    private Boolean status;

    public Vehicle() {
    }

    public Vehicle(String vehicleid, String userid, String username, String license, String plate, Boolean status) {
        this.vehicleid = vehicleid;
        this.userid = userid;
        this.username = username;
        this.license = license;
        this.plate = plate;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
