package com.example.fptparkingproject.ui.feedbacks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fptparkingproject.R;

public class FeedbackActivity extends AppCompatActivity {
    Button sendFeedBack;
    Button viewFeedBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        sendFeedBack = findViewById(R.id.btnSendFeedback);
        viewFeedBack = findViewById(R.id.btnViewFeedback);

        sendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SendFeedbackActivity.class);
                startActivity(intent);
            }
        });

        viewFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewFeedbackActivity.class);
                startActivity(intent);
            }
        });
    }
}