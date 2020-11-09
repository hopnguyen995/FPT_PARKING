package com.example.fptparkingproject.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.model.Vehicle;

public class EditProfileActivity extends AppCompatActivity {
    User user = new User();
    Vehicle vehicle = new Vehicle();
    Constant constant = new Constant();
    private TextView textViewUsername;
    private TextView textViewEmail;
    private EditText editTextPlateNumber;
    private EditText editTextLicense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user = (User) getIntent().getSerializableExtra(constant.INTENT_USER);
        vehicle = (Vehicle) getIntent().getSerializableExtra(constant.INTENT_VEHICLE);
        textViewUsername = findViewById(R.id.txt_userName);
        textViewEmail = findViewById(R.id.txt_email);
        editTextPlateNumber = findViewById(R.id.txt_plateNumber);
        editTextLicense = findViewById(R.id.txt_license);
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        editTextPlateNumber.setText(vehicle.getPlate());
        editTextLicense.setText(vehicle.getLicense());
        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}



