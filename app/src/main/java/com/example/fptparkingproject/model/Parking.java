package com.example.fptparkingproject.model;

public class Parking {
    private String parkingid;
    private String userid;
    private String vehicleid;
    private boolean status;
    private String time;

    public Parking() {
    }

    public Parking(String parkingid, String userid, String vehicleid, boolean status, String time) {
        this.parkingid = parkingid;
        this.userid = userid;
        this.vehicleid = vehicleid;
        this.status = status;
        this.time = time;
    }

    public String getParkingid() {
        return parkingid;
    }

    public void setParkingid(String parkingid) {
        this.parkingid = parkingid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
