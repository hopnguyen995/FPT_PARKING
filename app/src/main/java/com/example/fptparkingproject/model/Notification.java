package com.example.fptparkingproject.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Notification implements Serializable {
    private String notificationId;
    private String notificationTitle;
    private String notificationImage;
    private String notificationShortContent;
    private String notificationContent;
    private String notificationDateTime;

    public Notification() {
    }

    public Notification(String notificationId) {
        this.notificationId = notificationId;
    }

    public Notification(String notificationId, String notificationTitle, String notificationImage, String notificationShortContent, String notificationContent, String notificationDateTime) {
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

    public String getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(String notificationDateTime) {
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
}
