package com.example.fptparkingproject.ui.qrscan;

import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.firebasedatabase.ParkingInSynchronously;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.home.HomeFragment;
import com.example.fptparkingproject.untils.Until;

public class ParkingIn extends AppCompatActivity {
    Constant constant = new Constant();
    Until until = new Until();
    ParkingInSynchronously parkingInSynchronously = new ParkingInSynchronously();
    CountDownTimer timer;

    //save parking in information to database
    public void parkingIn(User user, String plate) {
            parkingInSynchronously.checkShareVehicle(user, plate);
            timer = new CountDownTimer(constant.TIMEOUT_PARKING, constant.COUNTDOWN) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (parkingInSynchronously.getResult()) {
                        showAlertDialog(parkingInSynchronously.getResult());
                        timer.cancel();
                    }
                }

                @Override
                public void onFinish() {
                    showAlertDialog(parkingInSynchronously.getResult());
                }
            };
            timer.start();
    }

    private void showAlertDialog(Boolean isSuccess) {
        if (isSuccess) {
            until.showAlertDialog(R.string.information, R.string.parkinginsuccess, HomeFragment.getAppContext());
        } else {
            until.showAlertDialog(R.string.title_warning, R.string.parkinginfailed, HomeFragment.getAppContext());
        }
    }
}
