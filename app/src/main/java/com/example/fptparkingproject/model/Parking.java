package com.example.fptparkingproject.model;

import java.util.Date;
import java.util.Objects;

public class Parking implements Comparable<Parking>{
    private String parkingid;
    private String username;
    private String plate;
    private boolean type;
    private Date time;

    public Parking() {
    }

    public Parking(String parkingid, String username, String plate, boolean type, Date time) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int compareTo(Parking o) {
        return this.getTime().compareTo(o.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parking)) return false;
        Parking parking = (Parking) o;
        return parkingid.equals(parking.parkingid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingid);
    }
}
