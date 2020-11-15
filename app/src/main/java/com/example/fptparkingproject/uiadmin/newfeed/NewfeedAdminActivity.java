package com.example.fptparkingproject.uiadmin.newfeed;

import androidx.annotation.NonNull;
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
import com.example.fptparkingproject.model.Newfeed;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class NewfeedAdminActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextImage;
    private EditText editTextLongContent;
    private Button buttonSend;
    private DatabaseReference ref;
    Until until = new Until();
    Constant constant = new Constant();
    String content = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_dashboard);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_newfeed_admin);
        ref = new Until().connectDatabase();
        editTextTitle = findViewById(R.id.textViewTitle);
        editTextLongContent = findViewById(R.id.textViewLongContent);
        editTextImage = findViewById(R.id.textViewImage);
        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextTitle.getText().toString().isEmpty() && !editTextLongContent.getText().toString().isEmpty()) {
                    Newfeed newfeed = new Newfeed(until.randomID(),editTextTitle.getText().toString(), editTextImage.getText().toString(),editTextLongContent.getText().toString(),new Date());
                    if(newfeed.getNewfeedLongContent().length() > constant.CHARACTER_LIMIT){
                        content = newfeed.getNewfeedLongContent().substring(0, constant.CHARACTER_LIMIT);
                        newfeed.setNewfeedShortContent(content);
                    }
                    ref.child(constant.TABLE_NEWFEEDS).child(newfeed.getNewfeedid()).setValue(newfeed);
                    Toast.makeText(NewfeedAdminActivity.this, "Insert success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
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