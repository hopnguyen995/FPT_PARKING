package com.example.fptparkingproject.ui.feedbacks;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Feedback;
import com.example.fptparkingproject.model.Notification;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.uiadmin.notification.SendNotificationActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class SendFeedbackActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSend;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Until until = new Until();
    Constant constant = new Constant();
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.button_ViewFeedback);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        ref = new Until().connectDatabase();
        user = new User().getUser(prefs);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editTextTitle = findViewById(R.id.etTitle);
        editTextContent = findViewById(R.id.etContent);
        buttonSend = findViewById(R.id.btnSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextTitle.getText().toString().isEmpty() &&  !editTextContent.getText().toString().isEmpty()) {
                    Feedback feedback = new Feedback(until.randomID(), editTextTitle.getText().toString(), editTextContent.getText().toString(), new Date(), user.getEmail() , "1");
                    ref.child(constant.TABLE_FEEDBACKS).child(feedback.getFeedbackId()).setValue(feedback);
                    Toast.makeText(SendFeedbackActivity.this, "Send success", Toast.LENGTH_SHORT).show();
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