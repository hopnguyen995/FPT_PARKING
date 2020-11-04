package com.example.fptparkingproject.model;

import android.content.SharedPreferences;

import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.ui.history.HistoryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class History implements Comparable<History> {
    private String historyId;
    private String historyImage;
    private String historyContent;
    private Date historyDateTime;
    Constant constant = new Constant();

    public History() {
    }

    public History(String historyId) {
        this.historyId = historyId;
    }

    public History(String historyId, String historyImage, String historyContent, Date historyDateTime) {
        this.historyId = historyId;
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

    public Date getHistoryDateTime() {
        return historyDateTime;
    }

    public void setHistoryDateTime(Date historyDateTime) {
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


    @Override
    public String toString() {
        return historyContent;
    }

    @Override
    public int compareTo(History o) {
        return this.getHistoryDateTime().compareTo(o.getHistoryDateTime());
    }
}
