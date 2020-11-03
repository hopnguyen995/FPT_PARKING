package com.example.fptparkingproject.ui.history;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.HistoryAdapter;
import com.example.fptparkingproject.model.History;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HistoryDetailActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewDateTime;
    private TextView textViewContent;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    private ListView listView;
    HistoryAdapter historyArrayAdapter;
    ArrayList<History> listHistoryDb = new ArrayList<>();
    User user;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("History");
        //Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_history_detail);
        //textViewTitle = findViewById(R.id.textViewTitle);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        textViewContent = findViewById(R.id.textViewContent);
        listView = findViewById(R.id.listView);
        Gson gson = new Gson();
        Intent intent = getIntent();
        History history = gson.fromJson(intent.getStringExtra(constant.INTENT_HISTORY_DETAIL_HISTORY),History.class);
        if(history == null) {
            history = new History();
        }

       // textViewDateTime.setText(history.getHistoryDateTime());
       // textViewContent.setText(history.getHistoryContent());


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ref = new Until().connectDatabase();
        user = new User().getUser(prefs);
        ref.child(constant.TABLE_PARKINGS).child("XmkLAcmx9UgGMMUr7lRacCaByTT2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Parking parking = ds.getValue(Parking.class);
                        History history = new History();
                        history.setHistoryId(parking.getParkingid());
                        history.setHistoryDateTime(parking.getTime());
                        history.setHistoryContent(parking.getUsername());
                        history.setHistoryImage(parking.isType() ? "InCome" : "GetOut");
                        listHistoryDb.add(history);
                    }
                    Collections.sort(listHistoryDb, new Comparator<History>() {
                        @Override
                        public int compare(History o1, History o2) {
                            SimpleDateFormat  formatter1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                            Date date1 = null;
                            Date date2 = null;
                            try {
                                date1 = formatter1.parse(o1.getHistoryDateTime());
                                date2 = formatter1.parse(o2.getHistoryDateTime());
                                return date2.compareTo(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
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
            historyArrayAdapter = new HistoryAdapter(this, R.layout.history_layout, listHistoryDb);
            listView.setAdapter(historyArrayAdapter);
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