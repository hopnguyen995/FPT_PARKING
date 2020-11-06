package com.example.fptparkingproject.ui.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.HistoryViewAdapter;
import com.example.fptparkingproject.model.History;
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
    ArrayList<History> listHistoryDb = new ArrayList<>();
    User user;
    Constant constant = new Constant();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.button_history);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerViewHistoryList);
        Gson gson = new Gson();
        Intent intent = getIntent();
        History history = gson.fromJson(intent.getStringExtra(constant.INTENT_HISTORY_DETAIL_HISTORY),History.class);
        if(history == null) {
            history = new History();
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ref = new Until().connectDatabase();
        user = new User().getUser(prefs);
        ref.child(constant.TABLE_PARKINGS).child(user.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Parking parking = ds.getValue(Parking.class);
                        History history = new History();
                        history.setHistoryId(parking.getParkingid());
                        history.setHistoryDateTime(parking.getTime());
                        history.setHistoryContent(parking.getUsername());
                        history.setHistoryImage(parking.isType() ? constant.PARKING_IN : constant.PARKING_OUT);
                        listHistoryDb.add(history);
                    }
                    Collections.sort(listHistoryDb, Collections.<History>reverseOrder());
                    showListView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showListView(){
        if(!listHistoryDb.isEmpty()){
            historyViewAdapter = new HistoryViewAdapter(getApplicationContext(),listHistoryDb);
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