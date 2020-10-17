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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Share;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.qrscan.QRScanActivity;
import com.example.fptparkingproject.qrshare.ShareActivity;
import com.example.fptparkingproject.signin.SignInWithGoogle;
import com.example.fptparkingproject.untils.Until;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignIn.getClient(getActivity(), gso).signOut();
                SharedPreferences prefRemember = getActivity().getSharedPreferences(constant.KEY_USER, Context.MODE_PRIVATE);
                prefRemember.edit().clear().commit();
                Toast.makeText(getContext(), R.string.signoutsuccess, Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(getContext(), SignInWithGoogle.class), constant.SIGNIN_REQUEST_CODE);
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
        final Button buttonHelp = root.findViewById(R.id.buttonMenuHelp);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber("02473081313");
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
            startActivityForResult(new Intent(getContext(), SignInWithGoogle.class), constant.SIGNIN_REQUEST_CODE);
        } else {
            user = new User().getUser(prefs);
            txtUsername.setText(user.getUsername());
            until.circleTransformAvatar(getContext(),imgAvatar, mAuth.getCurrentUser().getPhotoUrl().toString(),R.drawable.ic_baseline_account_circle_24);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == constant.SIGNIN_REQUEST_CODE && resultCode == constant.SIGNIN_RESPONSE_CODE) {
            until.circleTransformAvatar(getContext(),imgAvatar, mAuth.getCurrentUser().getPhotoUrl().toString(),R.drawable.ic_baseline_account_circle_24);
            user = new User().getUser(prefs);
            txtUsername.setText(user.getUsername());
        } else if (requestCode == constant.QRSCAN_REQUEST_CODE && resultCode == constant.QRSCAN_RESPONSE_CODE) {
            //get result qr scan
            String QRresult = data.getStringExtra(constant.INTENT_QRSCAN_RESULT);
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
                    new SendNotif().sendMessage("", user.getUsername(), sToken, user.getToken(), constant.KEY_CONFIRM_SHARE, until.dateTimeToString(new Date()));

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