package com.example.fptparkingproject.notification;


import okhttp3.MediaType;

public interface APIService {
     final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
     final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
     final String SERVER_KEY = "key=AAAAhR_HLL0:APA91bEzrxBEQhT-0B2SpzbSsWOs6vFiOAoQNAUCWssCOussGWIWoUP0yxHwHZvF15swvY5dC4BGPqnpTYOLOi2rY6WYRZ4PW9Rj2YIW066ABnayeENSvEbOJs_5n8znP5_Xcf9jsIKE";
}
