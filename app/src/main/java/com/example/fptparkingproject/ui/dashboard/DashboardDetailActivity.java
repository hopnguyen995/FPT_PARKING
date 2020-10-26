package com.example.fptparkingproject.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Newfeed;
import com.example.fptparkingproject.model.Notification;
import com.example.fptparkingproject.untils.Until;
import com.google.gson.Gson;

public class DashboardDetailActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewDateTime;
    private TextView textViewContent;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("News");
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_dashboard_detail);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        textViewContent = findViewById(R.id.textViewContent);
        Gson gson = new Gson();
        Intent intent = getIntent();
        Newfeed newfeed = gson.fromJson(intent.getStringExtra(constant.INTENT_NEWFEED_DETAIL_NEWFEED), Newfeed.class);
        textViewTitle.setText(newfeed.getNewfeedTitle());
        textViewDateTime.setText(newfeed.getNewfeedDateTime());
        textViewContent.setText(newfeed.getNewfeedLongContent());
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}