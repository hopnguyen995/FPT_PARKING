package com.example.fptparkingproject.ui.feedbacks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.HistoryViewAdapter;
import com.example.fptparkingproject.customadapter.ViewFeedbackUserAdapter;
import com.example.fptparkingproject.model.Feedback;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.profile.ProfileActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ViewFeedbackActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private SharedPreferences prefs;
    RecyclerView recyclerView;
    ViewFeedbackUserAdapter feedbackUserAdapter;
    ArrayList<Feedback> feedbackArrayListDb = new ArrayList<>();
    ArrayList<Feedback> feedbackArrayList = new ArrayList<>();
    User user;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.button_ViewFeedback);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        recyclerView = findViewById(R.id.recyclerViewFeedbackList);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ref = new Until().connectDatabase();
        user = new User().getUser(prefs);
        ref.child(constant.TABLE_FEEDBACKS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        boolean isDuplicate = false;
                        Feedback feedback = ds.getValue(Feedback.class);
                            feedbackArrayListDb.add(feedback);
                        }
                    Boolean isExist = false;
                    for (Feedback fb: feedbackArrayListDb) {
                        if(user.getEmail().equals(fb.getUserMail())){
                            isExist = true;
                            feedbackArrayList.add(fb);
                        }
                    }
                    if(isExist){
                        Collections.sort(feedbackArrayList, Collections.<Feedback>reverseOrder());
                        showListView();
                    }else {

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showListView() {
        if (!feedbackArrayList.isEmpty()) {
            feedbackUserAdapter = new ViewFeedbackUserAdapter(getApplicationContext(), feedbackArrayList);
            recyclerView.setAdapter(feedbackUserAdapter);
        }
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