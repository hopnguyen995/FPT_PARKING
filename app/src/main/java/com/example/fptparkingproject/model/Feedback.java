package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Feedback implements Comparable<Feedback> {
    private String feedbackId;
    private String feedbackTitle;
    private String feedbackContent;
    private Date feedbackDateTime;
    private String userMail;
    private String feedbackStatus;
    private String feedback;
    Constant constant = new Constant();

    public Feedback() {
    }

    public Feedback(String feedbackId, String feedbackTitle, String feedbackContent, Date feedbackDateTime, String userMail, String feedbackStatus) {
        this.feedbackId = feedbackId;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
        this.feedbackDateTime = feedbackDateTime;
        this.userMail = userMail;
        this.feedbackStatus = feedbackStatus;
    }

    public Feedback(String feedbackId, String feedbackTitle, String feedbackContent, Date feedbackDateTime, String userMail, String feedbackStatus, String feedback, Constant constant) {
        this.feedbackId = feedbackId;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
        this.feedbackDateTime = feedbackDateTime;
        this.userMail = userMail;
        this.feedbackStatus = feedbackStatus;
        this.feedback = feedback;
        this.constant = constant;
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

    public Date getFeedbackDateTime() {
        return feedbackDateTime;
    }

    public void setFeedbackDateTime(Date feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;
        Feedback that = (Feedback) o;
        return feedbackId.equals(that.feedbackId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId);
    }

    @Override
    public int compareTo(Feedback o) {
        return this.getFeedbackDateTime().compareTo(o.feedbackDateTime);
    }
}
