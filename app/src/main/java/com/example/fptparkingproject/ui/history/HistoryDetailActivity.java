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

import java.util.ArrayList;

public class HistoryDetailActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewDateTime;
    private TextView textViewContent;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    private ListView listView;
    ArrayAdapter<History> historyArrayAdapter;
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
                        history.setHistoryContent(parking.getUsername() + (parking.isType() ? "đã cho xe vào" : "đã lấy xe ra"));
                        history.setHistoryImage(parking.isType() ? "InCome" : "GetOut");
                        listHistoryDb.add(history);
                    }
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
            historyArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listHistoryDb);
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