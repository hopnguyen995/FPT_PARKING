package com.example.fptparkingproject.ui.menu;

import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.ProfileActivity;
import com.example.fptparkingproject.model.Share;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.ui.qrscan.ParkingIn;
import com.example.fptparkingproject.ui.qrscan.ParkingOut;
import com.example.fptparkingproject.ui.qrscan.QRScanActivity;
import com.example.fptparkingproject.ui.qrshare.ShareActivity;
import com.example.fptparkingproject.ui.signin.SignInWithGoogle;
import com.example.fptparkingproject.ui.history.HistoryActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.auth.FirebaseAuth;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class MenuFragment extends Fragment {
    private FirebaseAuth mAuth;
    private ImageView imgAvatar;
    private TextView txtUsername;
    Constant constant = new Constant();
    Until until = new Until();
    private SharedPreferences prefs;
    User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        mAuth = FirebaseAuth.getInstance();
        final Button buttonSignOut = root.findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove token in db
                new Until().connectDatabase().child(constant.TABLE_USERS).child(user.getUserid()).child(constant.TABLE_USERS_CHILD_TOKEN).setValue("");
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SignInWithGoogle.class));
                Toast.makeText(getContext(), R.string.signoutsuccess, Toast.LENGTH_SHORT).show();
            }
        });
        final Button buttonQRScan = root.findViewById(R.id.buttonMenuQrCode);
        buttonQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), QRScanActivity.class), constant.QRSCAN_REQUEST_CODE);
            }
        });
        final Button buttonShare = root.findViewById(R.id.buttonMenuShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShareActivity.class));
            }
        });
        final Button buttonHistory = root.findViewById(R.id.buttonMenuHistory);
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HistoryActivity.class));
            }
        });
        final Button buttonHelp = root.findViewById(R.id.buttonMenuHelp);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber(constant.PHONENUMBER);
            }
        });

        final Button buttonProfile = root.findViewById(R.id.buttonProfile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getContext(), ShareActivity.class));
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        imgAvatar = root.findViewById(R.id.imgAvatar);
        txtUsername = root.findViewById(R.id.txtusername);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getContext(), SignInWithGoogle.class));
        } else {
            user = new User().getUser(prefs);
            txtUsername.setText(user.getUsername());
            until.circleTransformAvatar(getContext(), imgAvatar, mAuth.getCurrentUser().getPhotoUrl().toString(), R.drawable.ic_baseline_account_circle_24);
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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("vehicleid", Context.MODE_PRIVATE);
                String vehicleid = sharedPreferences.getString("vehicleid", "");
                new ParkingIn().parkingIn(user, vehicleid);
            } else if (constant.PARKING_OUT.equals(QRresult)) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("vehicleid", Context.MODE_PRIVATE);
                String vehicleid = sharedPreferences.getString("vehicleid", "");
                new ParkingOut().parkingOut(user, vehicleid);
            } else if (QRresult.contains(constant.SHARE_VEHICLE)) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Share shareVehicle = mapper.readValue(QRresult, Share.class);
                    String sToken = shareVehicle.getToken();
                    //Update database

                    //notification
                    new SendNotif().sendMessage("", user.getUsername(),"", sToken, user.getToken(), constant.KEY_CONFIRM_SHARE, until.dateTimeToString(new Date()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), R.string.not_support, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dialNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }
}