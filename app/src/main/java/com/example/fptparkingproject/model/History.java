package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class History {
    private String historyId;
    private String historyTitle;
    private String historyImage;
    private String historyContent;
    private String historyDateTime;
    Constant constant = new Constant();

    public History() {
    }

    public History(String historyId) {
        this.historyId = historyId;
    }

    public History(String historyId, String historyTitle, String historyImage, String historyContent, String historyDateTime) {
        this.historyId = historyId;
        this.historyTitle = historyTitle;
        this.historyImage = historyImage;
        this.historyContent = historyContent;
        this.historyDateTime = historyDateTime;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getHistoryTitle() {
        return historyTitle;
    }

    public void setHistoryTitle(String historyTitle) {
        this.historyTitle = historyTitle;
    }

    public String getHistoryImage() {
        return historyImage;
    }

    public void setHistoryImage(String historyImage) {
        this.historyImage = historyImage;
    }

    public String getHistoryContent() {
        return historyContent;
    }

    public void setHistoryContent(String historyContent) {
        this.historyContent = historyContent;
    }

    public String getHistoryDateTime() {
        return historyDateTime;
    }

    public void setHistoryDateTime(String historyDateTime) {
        this.historyDateTime = historyDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof History)) return false;
        History that = (History) o;
        return historyId.equals(that.historyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyId);
    }
}
