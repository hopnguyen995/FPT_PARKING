package com.example.fptparckingproject.notification;


import okhttp3.MediaType;

public class API_Service {
    private final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String SERVER_KEY = "key=AAAAhR_HLL0:APA91bEzrxBEQhT-0B2SpzbSsWOs6vFiOAoQNAUCWssCOussGWIWoUP0yxHwHZvF15swvY5dC4BGPqnpTYOLOi2rY6WYRZ4PW9Rj2YIW066ABnayeENSvEbOJs_5n8znP5_Xcf9jsIKE";

    public String getFCM_MESSAGE_URL() {
        return FCM_MESSAGE_URL;
    }

    public MediaType getJSON() {
        return JSON;
    }

    public String getSERVER_KEY() {
        return SERVER_KEY;
    }
}
