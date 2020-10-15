package com.example.fptparkingproject.ui.menu;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.qrscan.QRScanActivity;
import com.example.fptparkingproject.qrshare.ShareActivity;
import com.example.fptparkingproject.signin.SignInWithGoogle;
import com.example.fptparkingproject.untils.Until;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class MenuFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private ImageView imgAvatar;
    private TextView txtUsername;
    private String userID;
    private String username;
    private String token;
    Constant constant = new Constant();
    Until until = new Until();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        mAuth = FirebaseAuth.getInstance();
        final Button buttonSignOut = root.findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove token in db
                new Until().connectDatabase().child("Users").child(userID).child("token").setValue("");
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignIn.getClient(getActivity(), gso).signOut();
                SharedPreferences prefRemember = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                prefRemember.edit().clear().commit();
                Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(getContext(), SignInWithGoogle.class), 100);
            }
        });
        final Button buttonQRScan = root.findViewById(R.id.buttonMenuQrCode);
        buttonQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), QRScanActivity.class), 300);
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
            startActivityForResult(new Intent(getContext(), SignInWithGoogle.class), 100);
        } else {
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("name", "");
            token = sharedPreferences.getString("token", "");
            txtUsername.setText(username);
            userID = sharedPreferences.getString("id", "");
            circleTransformAvatar(imgAvatar, mAuth.getCurrentUser().getPhotoUrl(), 124, 124);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            circleTransformAvatar(imgAvatar, mAuth.getCurrentUser().getPhotoUrl(), 124, 124);
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("token", "");
            txtUsername.setText(username);
            userID = sharedPreferences.getString("id", "");
        } else if (requestCode == 300 && resultCode == 400) {
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
                    new SendNotif().sendMessage("", username, sToken, token, constant.KEY_CONFIRM_SHARE, until.dateTimeToString(new Date()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), R.string.not_support, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void circleTransformAvatar(final ImageView img, Uri uri, int width, int height) {
        Picasso.with(getContext()).load(uri).resize(width, height).placeholder(R.drawable.ic_baseline_account_circle_24).into(img, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap imageBitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                imageDrawable.setCircular(true);
                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                img.setImageDrawable(imageDrawable);
            }

            @Override
            public void onError() {
                img.setImageResource(R.drawable.ic_baseline_account_circle_24);
            }

        });
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