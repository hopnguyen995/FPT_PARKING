package com.example.fptparkingproject.model;

import java.io.Serializable;
import java.util.Objects;

public class Vehicle implements Serializable {
    private String vehicleid;
    private String userid;
    private String username;
    private String license;
    private String plate;
    private Boolean status;

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleid='" + vehicleid + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", license='" + license + '\'' +
                ", plate='" + plate + '\'' +
                ", status=" + status +
                '}';
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleid.equals(vehicle.vehicleid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleid);
    }
}
