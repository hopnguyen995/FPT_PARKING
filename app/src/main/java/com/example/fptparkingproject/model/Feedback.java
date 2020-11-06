package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class Feedback implements Comparable<Feedback> {
    private String feedbackId;
    private String feedbackTitle;
    private String feedbackContent;
    private String feedbackDateTime;
    private String userMail;
    private String feedbackStatus;
    Constant constant = new Constant();

    public Feedback() {
    }

    public Feedback(String feedbackId, String feedbackTitle, String feedbackContent, String feedbackDateTime, String userMail, String feedbackStatus) {
        this.feedbackId = feedbackId;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
        this.feedbackDateTime = feedbackDateTime;
        this.userMail = userMail;
        this.feedbackStatus = feedbackStatus;
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

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
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

    //save list feedback to sharedpreference
    public void saveListFeedBack(SharedPreferences prefs, ArrayList<Feedback> listArray) {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listArray);
        editor.putString(constant.KEY_FEEDBACK, json);
        editor.commit();
    }

    //get list feedback sharedpreference
    public ArrayList<Feedback> getListFeedBack(SharedPreferences prefs) {
        Gson gson = new Gson();
        String json = prefs.getString(constant.KEY_NOTIFICATION, null);
        Type listType = new TypeToken<ArrayList<Feedback>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

}
