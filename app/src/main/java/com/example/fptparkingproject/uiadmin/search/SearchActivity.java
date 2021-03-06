package com.example.fptparkingproject.uiadmin.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.profile.ProfileActivity;
import com.example.fptparkingproject.uiadmin.profile.ProfileAdminActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private EditText inputSearch;
    private Button buttonSearch;
    private ArrayList<User> listUserDb;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.button_CheckInfo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_search);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        listUserDb = new ArrayList<>();
        inputSearch = findViewById(R.id.inputSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        ref = new Until().connectDatabase();
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(constant.TABLE_USERS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean isExist = false;
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                listUserDb.add(user);
                            }
                            for (User uDb : listUserDb
                            ) {
                                if (inputSearch.getText().toString().equals(uDb.getEmail())) {
                                    isExist = true;
                                    Intent intent = new Intent(getApplicationContext(), ProfileAdminActivity.class);
                                    intent.putExtra(constant.INTENT_USER, uDb);
                                    startActivity(intent);
                                    break;
                                }
                            }

                        }
                        if (!isExist) {
                            new Until().showAlertDialog(R.string.title_notificaton,R.string.emailnotexist, SearchActivity.this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });
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