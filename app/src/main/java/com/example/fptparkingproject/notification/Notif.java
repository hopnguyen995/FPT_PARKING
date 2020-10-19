package com.example.fptparkingproject.notification;

import okhttp3.MediaType;

public class Notif implements APIService {
    public String getFCM_MESSAGE_URL() {
        return FCM_MESSAGE_URL;
    }

    public String getHEADER() {
        return HEADER;
    }

    public MediaType getJSON() {
        return JSON;
    }

    public String getSERVER_KEY() {
        return SERVER_KEY;
    }
}
