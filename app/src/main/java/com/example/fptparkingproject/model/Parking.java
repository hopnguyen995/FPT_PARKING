package com.example.fptparkingproject.model;

public class Parking {
    private String parkingid;
    private String username;
    private String plate;
    private boolean type;
    private String time;

    public Parking() {
    }

    public Parking(String parkingid, String username, String plate, boolean type, String time) {
        this.parkingid = parkingid;
        this.username = username;
        this.plate = plate;
        this.type = type;
        this.time = time;
    }

    public String getParkingid() {
        return parkingid;
    }

    public void setParkingid(String parkingid) {
        this.parkingid = parkingid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
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
