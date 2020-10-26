package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class Notification implements Comparable<Notification> {
    private String notificationId;
    private String notificationTitle;
    private String notificationImage;
    private String notificationShortContent;
    private String notificationContent;
    private Date notificationDateTime;
    Constant constant = new Constant();

    public Notification() {
    }

    public Notification(String notificationId) {
        this.notificationId = notificationId;
    }

    public Notification(String notificationId, String notificationTitle, String notificationImage, String notificationShortContent, String notificationContent, Date notificationDateTime) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationImage = notificationImage;
        this.notificationShortContent = notificationShortContent;
        this.notificationContent = notificationContent;
        this.notificationDateTime = notificationDateTime;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotificationShortContent() {
        return notificationShortContent;
    }

    public void setNotificationShortContent(String notificationShortContent) {
        this.notificationShortContent = notificationShortContent;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public Date getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(Date notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return notificationId.equals(that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId);
    }

    @Override
    public int compareTo(Notification o) {
        return this.getNotificationDateTime().compareTo(o.notificationDateTime);
    }

    //save list notifications to sharedpreference
    public void saveListNotification(SharedPreferences prefs, ArrayList<Notification> listArray) {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listArray);
        editor.putString(constant.KEY_NOTIFICATION, json);
        editor.commit();
    }

    //get list notifications sharedpreference
    public ArrayList<Notification> getListNotification(SharedPreferences prefs) {
        Gson gson = new Gson();
        String json = prefs.getString(constant.KEY_NOTIFICATION, null);
        Type listType = new TypeToken<ArrayList<Notification>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationTitle='" + notificationTitle + '\'' +
                ", notificationDateTime=" + notificationDateTime +
                '}';
    }
}
