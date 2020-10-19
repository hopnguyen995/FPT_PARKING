package com.example.fptparkingproject.model;

import java.util.Date;

public class Newfeed {
    private String newId;
    private String newTitle;
    private String newImage;
    private String newShortContent;
    private String newLongContent;
    private Date newDateTime;

    public Newfeed() {
    }

    public Newfeed(String newId, String newTitle, String newImage, String newShortContent, String newLongContent, Date newDateTime) {
        this.newId = newId;
        this.newTitle = newTitle;
        this.newImage = newImage;
        this.newShortContent = newShortContent;
        this.newLongContent = newLongContent;
        this.newDateTime = newDateTime;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewImage() {
        return newImage;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }

    public String getNewShortContent() {
        return newShortContent;
    }

    public void setNewShortContent(String newShortContent) {
        this.newShortContent = newShortContent;
    }

    public String getNewLongContent() {
        return newLongContent;
    }

    public void setNewLongContent(String newLongContent) {
        this.newLongContent = newLongContent;
    }

    public Date getNewDateTime() {
        return newDateTime;
    }

    public void setNewDateTime(Date newDateTime) {
        this.newDateTime = newDateTime;
    }
}
