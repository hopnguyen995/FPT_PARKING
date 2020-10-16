package com.example.fptparkingproject.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Share;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.qrscan.QRScanActivity;
import com.example.fptparkingproject.qrshare.ShareActivity;
import com.example.fptparkingproject.signin.SignInWithGoogle;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private TextView txtUsername;
    Constant constant = new Constant();
    Until until = new Until();
    private SharedPreferences prefs;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final Button buttonQRScan = root.findViewById(R.id.buttonQR);
        buttonQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), QRScanActivity.class), constant.QRSCAN_REQUEST_CODE);
            }
        });
        final Button buttonShare = root.findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShareActivity.class));
            }
        });
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        txtUsername = root.findViewById(R.id.user);
        mAuth = FirebaseAuth.getInstance();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivityForResult(new Intent(getContext(), SignInWithGoogle.class), constant.SIGNIN_REQUEST_CODE);
        } else {
            user = new User().getUser(prefs);
            txtUsername.setText(user.getUsername());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == constant.QRSCAN_REQUEST_CODE && resultCode == constant.QRSCAN_RESPONSE_CODE) {
            //get result qr scan
            String QRresult = data.getStringExtra(constant.INTENT_QRSCAN_RESULT);
            //process
            if (constant.PARKING_IN.equals(QRresult)) {
                until.showAlertDialog(R.string.information, R.string.parkingin, getContext());
            } else if (constant.PARKING_OUT.equals(QRresult)) {
                until.showAlertDialog(R.string.information, R.string.parkingout, getContext());
            } else if (QRresult.contains(constant.SHARE_VEHICLE)) {
                    Gson gson = new Gson();
                    Share shareVehicle = gson.fromJson(QRresult, Share.class);
                    String sToken = shareVehicle.getToken();
                    //Update database
                    if(!shareVehicle.getShare_vehicle().equals(mAuth.getUid())){
                        //notification
                        new SendNotif().sendMessage("", user.getUsername(), sToken, user.getToken(), constant.KEY_CONFIRM_SHARE,until.dateTimeToString(new Date()));
                    }else{
                        Toast.makeText(getContext(), R.string.cannotshare, Toast.LENGTH_LONG).show();
                    }
            } else {
                Toast.makeText(getContext(), R.string.not_support, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == constant.SIGNIN_REQUEST_CODE && resultCode == constant.SIGNIN_RESPONSE_CODE) {
            user = new User().getUser(prefs);
            txtUsername.setText(user.getUsername());
        }
    }
}