package com.example.fptparkingproject.qrscan;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.firebasedatabase.ParkingInSynchronously;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.home.HomeFragment;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParkingIn extends AppCompatActivity {
    Constant constant = new Constant();
    Until until = new Until();
    ParkingInSynchronously parkingInSynchronously = new ParkingInSynchronously();

    //save parking in information to database
    public void parkingIn(User user) {
        parkingInSynchronously.addDataParkingSynchronous(user);
        CountDownTimer timer = new CountDownTimer(constant.TIMEOUT_PARKING, constant.COUNTDOWN) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if (parkingInSynchronously.getResult()) {
                    until.showAlertDialog(R.string.information, R.string.parkinginsuccess, HomeFragment.getAppContext());
                } else {
                    until.showAlertDialog(R.string.title_warning, R.string.parkinginfailed, HomeFragment.getAppContext());
                }
            }
        };
        timer.start();
    }
}
