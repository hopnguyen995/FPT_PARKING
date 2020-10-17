package com.example.fptparkingproject.qrscan;

import android.os.CountDownTimer;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.firebasedatabase.ParkingInSynchronously;
import com.example.fptparkingproject.firebasedatabase.ParkingOutSynchronously;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.home.HomeFragment;
import com.example.fptparkingproject.untils.Until;

public class ParkingOut {
    Constant constant = new Constant();
    Until until = new Until();
    ParkingOutSynchronously parkingOutSynchronously = new ParkingOutSynchronously();

    //save parking in information to database
    public void parkingOut(User user, String vehicleid) {
        parkingOutSynchronously.addDataParkingSynchronous(user,vehicleid);
        CountDownTimer timer = new CountDownTimer(constant.TIMEOUT_PARKING, constant.COUNTDOWN) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (parkingOutSynchronously.getResult()) {
                    until.showAlertDialog(R.string.information, R.string.parkingoutsuccess, HomeFragment.getAppContext());
                } else {
                    until.showAlertDialog(R.string.title_warning, R.string.parkingoutfailed, HomeFragment.getAppContext());
                }
            }
        };
        timer.start();
    }
}
