package com.example.fptparkingproject.model;

public class Feedback {
    private String feedbackId;
    private String feedbackTitle;
    private String feedbackContent;
    private String feedbackDateTime;
    private String userMail;



    public Feedback(String feedbackId, String feedbackTitle, String feedbackContent, String feedbackDateTime, String userMail) {
        this.feedbackId = feedbackId;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
        this.feedbackDateTime = feedbackDateTime;
        this.userMail = userMail;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackDateTime() {
        return feedbackDateTime;
    }

    public void setFeedbackDateTime(String feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
