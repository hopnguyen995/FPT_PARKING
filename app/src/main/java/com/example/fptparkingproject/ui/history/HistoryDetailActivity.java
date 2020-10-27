package com.example.fptparkingproject.ui.history;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.google.gson.Gson;

public class HistoryDetailActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewDateTime;
    private TextView textViewContent;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_toolbar_notification);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_notification_detail);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        textViewContent = findViewById(R.id.textViewContent);
        Gson gson = new Gson();
        Intent intent = getIntent();
        History history = gson.fromJson(intent.getStringExtra(constant.INTENT_HISTORY_DETAIL_HISTORY),History.class);
        textViewTitle.setText(history.getHistoryTitle());
        textViewDateTime.setText(history.getHistoryDateTime());
        textViewContent.setText(history.getHistoryContent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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