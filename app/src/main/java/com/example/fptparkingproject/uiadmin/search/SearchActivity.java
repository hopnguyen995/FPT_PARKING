package com.example.fptparkingproject.uiadmin.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.NewfeedAdapter;
import com.example.fptparkingproject.customadapter.SearchAdapter;
import com.example.fptparkingproject.model.Newfeed;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity {
    private EditText inputSearch;
    private ListView list_view;
    private ArrayAdapter<String> adapter;

    ArrayList<User> listUser;
    SearchAdapter searchAdapter;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final ArrayList<User> listUserDb = new ArrayList<>();
        listUser = new User().getListUser(prefs);
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        searchAdapter = new SearchAdapter(getApplicationContext(), listUser);
        list_view.setAdapter((ListAdapter) searchAdapter);
        ref = new Until().connectDatabase();
        ref.child(constant.TABLE_USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        listUserDb.add(user);
                    }
                    for (User uDb : listUserDb
                    ) {
                        boolean isExist = false;
                        for (User u : listUser
                        ) {
                            if (u.equals(uDb)) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            listUser.add(uDb);
                        }
                    }

                }
                searchAdapter = new SearchAdapter(getApplicationContext(), listUser);
                list_view.setAdapter((ListAdapter) searchAdapter);
                new User().saveListUser(prefs, listUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        searchAdapter = new SearchAdapter(getApplicationContext(), listUser);
        list_view.setAdapter((ListAdapter) searchAdapter);
    }
}