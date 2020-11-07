package com.example.fptparkingproject.ui.history;

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
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private SharedPreferences prefs;
    RecyclerView recyclerView;
    HistoryViewAdapter historyViewAdapter;
    ArrayList<Parking> listParkingHistoryDb = new ArrayList<>();
    User user;
    Constant constant = new Constant();
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.button_history);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_history);
        constraintLayout = findViewById(R.id.layoutNoVehicle);
        recyclerView = findViewById(R.id.recyclerViewHistoryList);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ref = new Until().connectDatabase();
        user = new User().getUser(prefs);
        ref.child(constant.TABLE_PARKINGS).child(user.getUserid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        boolean isDuplicate = false;
                        Parking parking = ds.getValue(Parking.class);
                        for (Parking p: listParkingHistoryDb
                             ) {
                            if(parking.getParkingid().equals(p.getParkingid())){
                                isDuplicate = true;
                                break;
                            }
                        }
                        if(!isDuplicate){
                            listParkingHistoryDb.add(parking);
                        }
                    }
                    Collections.sort(listParkingHistoryDb, Collections.<Parking>reverseOrder());
                    showListView();
                }
                else{
                    constraintLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showListView() {
        if (!listParkingHistoryDb.isEmpty()) {
            constraintLayout.setVisibility(View.INVISIBLE);
            historyViewAdapter = new HistoryViewAdapter(getApplicationContext(), listParkingHistoryDb);
            recyclerView.setAdapter(historyViewAdapter);
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