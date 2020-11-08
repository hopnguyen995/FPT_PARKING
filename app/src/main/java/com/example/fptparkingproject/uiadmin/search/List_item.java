package com.example.fptparkingproject.uiadmin.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.SearchUserAdapter;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.profile.ProfileActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_item extends AppCompatActivity {
    private ArrayList<User> listUserDb;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Constant constant = new Constant();
    RecyclerView recyclerView;
    SearchUserAdapter searchUserAdapter;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_item);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        listUserDb = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        listUserDb = new User().getListUser(prefs);
        ref = new Until().connectDatabase();
        ref.child(constant.TABLE_USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean isExist = false;
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        listUserDb.add(user);
                    }
                }
                if(!isExist){

                }
                searchUserAdapter = new SearchUserAdapter(getApplication(), listUserDb);
                recyclerView.setAdapter(searchUserAdapter);
                new User().saveListUser(prefs,listUserDb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}