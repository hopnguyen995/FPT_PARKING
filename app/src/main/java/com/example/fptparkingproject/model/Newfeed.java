package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class Newfeed {
    private String newfeedid;
    private String newfeedTitle;
    private String newfeedImage;
    private String newfeedShortContent;
    private String newfeedLongContent;
    private String newfeedDateTime;
    Constant constant = new Constant();

    public Newfeed() {
    }

    public Newfeed(String newfeedid) {
        this.newfeedid = newfeedid;
    }

    public Newfeed(String newfeedid, String newfeedTitle, String newfeedImage, String newfeedShortContent, String newfeedLongContent, String newfeedDateTime) {
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

    public void setNewShortContent(String newfeedShortContent) {
        this.newfeedShortContent = newfeedShortContent;
    }

    public String getNewfeedLongContent() {
        return newfeedLongContent;
    }

    public void setNewfeedLongContent(String newfeedLongContent) {
        this.newfeedLongContent = newfeedLongContent;
    }

    public String getNewfeedDateTime() {
        return newfeedDateTime;
    }

    public void setNewfeedDateTime(String newfeedDateTime) {
        this.newfeedDateTime = newfeedDateTime;
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
}
