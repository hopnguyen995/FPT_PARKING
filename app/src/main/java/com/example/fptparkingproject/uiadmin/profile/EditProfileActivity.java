package com.example.fptparkingproject.uiadmin.profile;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.model.Vehicle;
import com.example.fptparkingproject.textdetection.TextDetectionActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    User user = new User();
    Vehicle vehicle = new Vehicle();
    ArrayList<Vehicle> listVehicle = new ArrayList<>();
    Constant constant = new Constant();
    Until until = new Until();
    private TextView textViewUsername;
    private TextView textViewEmail;
    private EditText editTextPlateNumber;
    private EditText editTextLicense;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.txtLicense);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_edit_profile);
        user = (User) getIntent().getSerializableExtra(constant.INTENT_USER);
        vehicle = (Vehicle) getIntent().getSerializableExtra(constant.INTENT_VEHICLE);
        listVehicle = (ArrayList<Vehicle>) getIntent().getSerializableExtra(constant.INTENT_LISTVEHICLE);
        textViewUsername = findViewById(R.id.txt_userName);
        textViewEmail = findViewById(R.id.txt_email);
        editTextPlateNumber = findViewById(R.id.txt_plateNumber);
        editTextLicense = findViewById(R.id.txt_license);
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        if (vehicle != null) {
            editTextPlateNumber.setText(vehicle.getPlate());
            editTextLicense.setText(vehicle.getLicense());
        }
        ref = new Until().connectDatabase();
        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listVehicle != null) {
                    for (final Vehicle vehicle : listVehicle
                    ) {
                        if (vehicle.getStatus()) {
                            if (vehicle.getPlate().compareToIgnoreCase(editTextPlateNumber.getText().toString()) != 0) {
                                Vehicle newVehicle = new Vehicle();
                                newVehicle.setVehicleid(until.randomID());
                                newVehicle.setUserid(user.getUserid());
                                newVehicle.setLicense(editTextLicense.getText().toString().trim());
                                newVehicle.setPlate(editTextPlateNumber.getText().toString().trim().toUpperCase());
                                newVehicle.setStatus(true);
                                newVehicle.setUsername(user.getUsername());
                                ref.child(constant.TABLE_VEHICLES).child(user.getUserid()).child(newVehicle.getVehicleid()).setValue(newVehicle, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error != null) {
                                            until.showAlertDialog(R.string.title_warning, R.string.licenseerror, EditProfileActivity.this);
                                        } else {
                                            vehicle.setStatus(false);
                                            ref.getParent().child(vehicle.getVehicleid()).setValue(vehicle);
                                            until.showAlertDialog(R.string.title_notificaton, R.string.licensesuccess, EditProfileActivity.this);
                                        }
                                    }
                                });
                            } else {
                                until.showAlertDialog(R.string.title_warning, R.string.licenseerror, EditProfileActivity.this);
                            }
                        }
                    }
                } else {
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setVehicleid(new Until().randomID());
                    newVehicle.setUserid(user.getUserid());
                    newVehicle.setLicense(editTextLicense.getText().toString().trim());
                    newVehicle.setPlate(editTextPlateNumber.getText().toString().trim());
                    newVehicle.setStatus(true);
                    newVehicle.setUsername(user.getUsername());
                    ref.child(constant.TABLE_VEHICLES).child(user.getUserid()).child(newVehicle.getVehicleid()).setValue(newVehicle, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Toast.makeText(EditProfileActivity.this, R.string.licenseerror, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(EditProfileActivity.this, R.string.licenseerror, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        findViewById(R.id.buttonCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextDetectionActivity.class);
                intent.putExtra(constant.INTENT_CASE, true);
                startActivityForResult(intent, constant.CAMERA_REQUEST_CODE);
            }
        });
        findViewById(R.id.buttonCaptureLicense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextDetectionActivity.class);
                startActivityForResult(intent, constant.CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == constant.CAMERA_REQUEST_CODE && resultCode == constant.CAMERA_RESPONSE_CODE) {
                editTextPlateNumber.setText(data.getStringExtra(constant.INTENT_RESULT).replace("\n", "").toUpperCase().replaceAll("([^a-zA-Z0-9]*)", ""));
            } else {
                editTextLicense.setText(data.getStringExtra(constant.INTENT_RESULT));
            }
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



