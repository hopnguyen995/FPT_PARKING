package com.example.fptparkingproject.uiadmin.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.untils.Until;

import java.util.Date;

public class SendNotificationActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextImage;
    private EditText editTextContent;
    private EditText editTextShortContent;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        editTextTitle = findViewById(R.id.textViewTitle);
        editTextShortContent = findViewById(R.id.textViewShortContent);
        editTextContent = findViewById(R.id.textViewContent);
        editTextImage = findViewById(R.id.textViewImage);
        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextTitle.getText().toString().isEmpty() && !editTextShortContent.getText().toString().isEmpty() && !editTextContent.getText().toString().isEmpty()) {
                    new SendNotif().sendMessage(editTextTitle.getText().toString(), editTextShortContent.getText().toString(), editTextImage.getText().toString(), "/topics/all", "", "", new Until().nomalizeDateTime(new Date()));
                    Toast.makeText(SendNotificationActivity.this, "Send success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}