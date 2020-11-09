package com.example.fptparkingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.signin.SignInWithGoogle;
import com.example.fptparkingproject.uiadmin.newfeed.NewfeedAdminActivity;
import com.example.fptparkingproject.uiadmin.notification.SendNotificationActivity;
import com.example.fptparkingproject.uiadmin.search.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    Constant constant = new Constant();
    private FirebaseAuth mAuth;
    private SharedPreferences prefs;
    User user;
    private Button buttonSignOut;
    private Button buttonNotif;
    private Button buttonNewFeed;
    private Button buttonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_admin);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        buttonSignOut = findViewById(R.id.buttonAdminSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                user = new User();
                user.saveUser(prefs,user);
                Toast.makeText(getApplicationContext(), R.string.signoutsuccess, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SignInWithGoogle.class));
            }
        });
        buttonNotif = findViewById(R.id.buttonNotif);
        buttonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), SendNotificationActivity.class),1);
            }
        });

        buttonNewFeed = findViewById(R.id.buttonNewFeed);
        buttonNewFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewfeedAdminActivity.class));
            }
        });

        buttonInfo = findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), SignInWithGoogle.class));
        } else {
            user = new User().getUser(prefs);
        }
    }
}