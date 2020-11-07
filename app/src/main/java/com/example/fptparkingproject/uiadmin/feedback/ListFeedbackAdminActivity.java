package com.example.fptparkingproject.uiadmin.feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.ViewFeedbackUserAdapter;
import com.example.fptparkingproject.model.Feedback;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListFeedbackAdminActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private SharedPreferences prefs;
    RecyclerView recyclerView;
    ViewFeedbackUserAdapter feedbackUserAdapter;
    ArrayList<Feedback> feedbackArrayListDb = new ArrayList<>();
    User user;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_feedback_admin);
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
                        for (Feedback fb: feedbackArrayListDb) {
                            if(feedback.getFeedbackId().equals(fb.getFeedbackId())){
                                isDuplicate = true;
                                break;
                            }
                        }
                        if(!isDuplicate){
                            feedbackArrayListDb.add(feedback);
                        }
                    }
                    Collections.sort(feedbackArrayListDb, Collections.<Feedback>reverseOrder());
                    showListView();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showListView() {
        if (!feedbackArrayListDb.isEmpty()) {
            feedbackUserAdapter = new ViewFeedbackUserAdapter(getApplicationContext(), feedbackArrayListDb);
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