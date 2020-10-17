package com.example.fptparkingproject.model;

public class Parking {
    private String parkingid;
    private String userid;
    private String vehicleid;
    private boolean type;
    private String time;

    public Parking() {
    }

    public Parking(String parkingid, String userid, String vehicleid, boolean type, String time) {
        this.parkingid = parkingid;
        this.userid = userid;
        this.vehicleid = vehicleid;
        this.type = type;
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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
