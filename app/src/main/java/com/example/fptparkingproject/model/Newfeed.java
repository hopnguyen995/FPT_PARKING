package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Newfeed implements Comparable<Newfeed> {
    private String newfeedid;
    private String newfeedTitle;
    private String newfeedImage;
    private String newfeedShortContent;
    private String newfeedLongContent;
    private Date newfeedDateTime;
    Constant constant = new Constant();

    private boolean expanded;

    public Newfeed() {
    }

    public Newfeed(String newfeedid) {
        this.newfeedid = newfeedid;
    }

    public Newfeed(String newfeedid, String newfeedTitle, String newfeedImage, String newfeedShortContent, String newfeedLongContent, Date newfeedDateTime) {
        this.newfeedid = newfeedid;
        this.newfeedTitle = newfeedTitle;
        this.newfeedImage = newfeedImage;
        this.newfeedShortContent = newfeedShortContent;
        this.newfeedLongContent = newfeedLongContent;
        this.newfeedDateTime = newfeedDateTime;
    }

    public String getNewfeedid() {
        return newfeedid;
    }

    public void setNewfeedid(String newfeedid) {
        this.newfeedid = newfeedid;
    }

    public String getNewfeedTitle() {
        return newfeedTitle;
    }

    public void setNewfeedTitle(String newfeedTitle) {
        this.newfeedTitle = newfeedTitle;
    }

    public String getNewfeedImage() {
        return newfeedImage;
    }

    public void setNewfeedImage(String newfeedImage) {
        this.newfeedImage = newfeedImage;
    }

    public String getNewfeedShortContent() {
        return newfeedShortContent;
    }

    public void setNewfeedShortContent(String newfeedShortContent) {
        this.newfeedShortContent = newfeedShortContent;
    }

    public String getNewfeedLongContent() {
        return newfeedLongContent;
    }

    public void setNewfeedLongContent(String newfeedLongContent) {
        this.newfeedLongContent = newfeedLongContent;
    }

    public Date getNewfeedDateTime() {
        return newfeedDateTime;
    }

    public void setNewfeedDateTime(Date newfeedDateTime) {
        this.newfeedDateTime = newfeedDateTime;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public ArrayList<Newfeed> getListNewfeed(SharedPreferences prefs) {
        Gson gson = new Gson();
        String json = prefs.getString(constant.KEY_NEWFEED, null);
        Type listType = new TypeToken<ArrayList<Newfeed>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    public void saveListNewfeed(SharedPreferences prefs, ArrayList<Newfeed> listArray) {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listArray);
        editor.putString(constant.KEY_NEWFEED, json);
        editor.commit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Newfeed)) return false;
        Newfeed newfeed = (Newfeed) o;
        return getNewfeedid().equals(newfeed.getNewfeedid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNewfeedid());
    }

    @Override
    public String toString() {
        return "Newfeed{" +
                "newfeedTitle='" + newfeedTitle + '\'' +
                ", newfeedDateTime=" + newfeedDateTime +
                '}';
    }

    @Override
    public int compareTo(Newfeed o) {
        return this.getNewfeedDateTime().compareTo(o.getNewfeedDateTime());
    }
}
