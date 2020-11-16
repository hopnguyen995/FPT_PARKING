package com.example.fptparkingproject.uiadmin.profile;

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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.VehicleAdminViewAdapter;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.model.Vehicle;
import com.example.fptparkingproject.untils.Until;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileAdminActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private SharedPreferences prefs;
    private TextView txtDanhsach;
    private RecyclerView recyclerView;
    private VehicleAdminViewAdapter vehicleAdminViewAdapter;
    private LinearLayout linearLayout;
    private ArrayList<Vehicle> listVehicles;
    private ArrayList<Vehicle> listAllVehicles;
    User user;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.txtProfile);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_profile_admin);
        FloatingActionButton fab = findViewById(R.id.fab_edit);
        TextView txtUsername = findViewById(R.id.txt_userName);
        TextView txtEmail = findViewById(R.id.txt_email);
        txtDanhsach = findViewById(R.id.txtDanhSach);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        fab.setVisibility(View.VISIBLE);
        user = (User) getIntent().getSerializableExtra(constant.INTENT_USER);

        txtDanhsach.setVisibility(View.INVISIBLE);
        linearLayout = findViewById(R.id.linearLayoutvehicle);
        linearLayout.setVisibility(View.INVISIBLE);
        txtUsername.setText(user.getUsername());
        txtEmail.setText(user.getEmail());
        listVehicles = new ArrayList<>();
        listAllVehicles = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewVehicleList);
        ref = new Until().connectDatabase();
        ref.child(constant.TABLE_VEHICLES).child(user.getUserid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtDanhsach.setVisibility(View.VISIBLE);
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    listVehicles.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Vehicle vehicle = ds.getValue(Vehicle.class);
                        int position = -1;
                        for (int i = 0; i < listVehicles.size(); i++) {
                            if (vehicle.getVehicleid().equals(listVehicles.get(i).getVehicleid())) {
                                position = i;
                            }
                        }
                        if (position != -1) {
                            listVehicles.set(position, vehicle);
                        } else {
                            listVehicles.add(vehicle);
                        }
                    }
                    showListVehicle();
                } else {
                    txtDanhsach.setText(R.string.txtNotLicense);
                    linearLayout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child(constant.TABLE_VEHICLES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        for (DataSnapshot dsc : ds.getChildren()) {
                            Vehicle vehicle = dsc.getValue(Vehicle.class);
                            listAllVehicles.add(vehicle);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra(constant.INTENT_USER, user);
                if (listAllVehicles != null) {
                    intent.putExtra(constant.INTENT_LISTALLVEHICLE, listAllVehicles);
                }
                if (listVehicles != null) {
                    for (Vehicle vehicle : listVehicles
                    ) {
                        if (vehicle.getStatus()) {
                            intent.putExtra(constant.INTENT_VEHICLE, vehicle);
                            intent.putExtra(constant.INTENT_LISTVEHICLE, listVehicles);
                        }
                    }
                }
                startActivity(intent);
            }
        });
    }

    private void showListVehicle() {
        if (!listVehicles.isEmpty()) {
            txtDanhsach.setText(R.string.txtlistVehicle);
            linearLayout.setVisibility(View.VISIBLE);
            vehicleAdminViewAdapter = new VehicleAdminViewAdapter(getApplicationContext(), listVehicles);
            recyclerView.setAdapter(vehicleAdminViewAdapter);
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