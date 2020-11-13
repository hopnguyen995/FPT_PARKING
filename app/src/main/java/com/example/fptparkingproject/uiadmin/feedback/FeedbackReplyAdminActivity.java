package com.example.fptparkingproject.uiadmin.feedback;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Feedback;
import com.example.fptparkingproject.model.Notification;
import com.example.fptparkingproject.ui.feedbacks.SendFeedbackActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.Date;

public class FeedbackReplyAdminActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewDateTime;
    private EditText textViewContent;
    private TextView textViewUserMail;
    private EditText txtReply;
    private Button btnApprove;
    Constant constant = new Constant();
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_reply_admin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_toolbar_feedback_reply_admin);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        textViewTitle = findViewById(R.id.txtTitleAdmin);
        textViewDateTime = findViewById(R.id.txtDateTimeAdmin);
        textViewContent = findViewById(R.id.etContentAdmin);
        textViewUserMail = findViewById(R.id.txtUserMailAdmin);
        txtReply = findViewById(R.id.etReply);
        btnApprove = findViewById(R.id.btnApprove);
        Gson gson = new Gson();
        Intent intent = getIntent();
        final Feedback feedback = gson.fromJson(intent.getStringExtra(constant.INTENT_DETAIL_FEEDBACK), Feedback.class);
        ref = new Until().connectDatabase();
        textViewTitle.setText(feedback.getFeedbackTitle());
        textViewDateTime.setText(new Until().nomalizeDateTime(feedback.getFeedbackDateTime()));
        textViewContent.setText(feedback.getFeedbackContent());
        textViewUserMail.setText(feedback.getUserMail());
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtReply.getText().toString().isEmpty()) {
                    feedback.setFeedback(txtReply.getText().toString());
                    feedback.setFeedbackStatus("2");
                    ref.child(constant.TABLE_FEEDBACKS).child(feedback.getFeedbackId()).setValue(feedback);
                    Toast.makeText(FeedbackReplyAdminActivity.this, R.string.feedback_approve_success, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
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