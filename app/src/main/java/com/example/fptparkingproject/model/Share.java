package com.example.fptparkingproject.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Share {
    private String share_vehicle = null;
    private String token = null;

    public String getShare_vehicle() {
        return share_vehicle;
    }

    public void setShare_vehicle(String share_vehicle) {
        this.share_vehicle = share_vehicle;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
