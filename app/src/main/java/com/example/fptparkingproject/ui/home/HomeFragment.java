package com.example.fptparkingproject.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.qrscan.QRScanActivity;
import com.example.fptparkingproject.qrshare.ShareActivity;
import com.example.fptparkingproject.signin.SignInWithGoogle;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.auth.FirebaseAuth;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class HomeFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private TextView user;
    private String uid;
    private String username;
    private String token;
    Constant constant = new Constant();
    Until until = new Until();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final Button buttonQRScan = root.findViewById(R.id.buttonQR);
        buttonQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), QRScanActivity.class), 300);
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
        user = root.findViewById(R.id.user);
        mAuth = FirebaseAuth.getInstance();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivityForResult(new Intent(getContext(), SignInWithGoogle.class), 100);
        } else {
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("name", "");
            token = sharedPreferences.getString("token", "");
            user.setText(username);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == 400) {
            //get result qr scan
            String QRresult = data.getStringExtra("QRResult");
            //process
            if (constant.PARKING_IN.equals(QRresult)) {
                until.showAlertDialog(R.string.information, R.string.parkingin, getContext());
            } else if (constant.PARKING_OUT.equals(QRresult)) {
                until.showAlertDialog(R.string.information, R.string.parkingout, getContext());
            } else if (QRresult.contains(constant.SHARE_VEHICLE)) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Share shareVehicle = mapper.readValue(QRresult, Share.class);
                    String sToken = shareVehicle.getToken();
                    //Update database

                    //notification
                    new SendNotif().sendMessage("", username, sToken, token, constant.KEY_CONFIRM_SHARE,until.dateTimeToString(new Date()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), R.string.not_support, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 100 && resultCode == 200) {
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("name", "");
            token = sharedPreferences.getString("token", "");
            user.setText(username);
        }
    }
}