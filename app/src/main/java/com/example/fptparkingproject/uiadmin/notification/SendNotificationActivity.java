package com.example.fptparkingproject.uiadmin.notification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Notification;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class SendNotificationActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextImage;
    private EditText editTextContent;
    private EditText editTextShortContent;
    private Button buttonSend;
    private DatabaseReference ref;
    Until until = new Until();
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_toolbar_notification);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_send_notification);
        ref = new Until().connectDatabase();
        editTextTitle = findViewById(R.id.textViewTitle);
        editTextShortContent = findViewById(R.id.textViewLongContent);
        editTextContent = findViewById(R.id.textViewLongContent);
        editTextImage = findViewById(R.id.textViewImage);
        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextTitle.getText().toString().isEmpty() && !editTextShortContent.getText().toString().isEmpty() && !editTextContent.getText().toString().isEmpty()) {
                    Notification notification = new Notification(until.randomID(),editTextTitle.getText().toString(), editTextImage.getText().toString(),editTextShortContent.getText().toString(),editTextContent.getText().toString(),new Date());
                    new SendNotif().sendMessage(notification.getNotificationTitle(), notification.getNotificationShortContent(), notification.getNotificationImage(), "/topics/all", "", "", until.nomalizeDateTime(notification.getNotificationDateTime()));
                    ref.child(constant.TABLE_NOTIFICATIONS).child(notification.getNotificationId()).setValue(notification);
                    Toast.makeText(SendNotificationActivity.this, "Send success", Toast.LENGTH_SHORT).show();
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