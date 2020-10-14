package com.example.fptparkingproject.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class AlertDialogActivity extends Activity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String title;
    private String message;
    private String token;
    private String sendtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        } else {
            showAlertDialogConfimShareVehicle();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showAlertDialogConfimShareVehicle();
    }

    private void showAlertDialogConfimShareVehicle() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        message = intent.getStringExtra("message");
        token = intent.getStringExtra("token");
        sendtoken = intent.getStringExtra("sendtoken");
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_message);
        builder.setPositiveButton(getResources().getString(R.string.button_Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SendNotif().sendMessage("", "", sendtoken, token, new Constant().KEY_CONFIRM_SHARE_SUCCESS, new Until().dateTimeToString(new Date()));
                //update database: sharing
                dialog.cancel();
                finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.button_Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SendNotif().sendMessage("", mAuth.getCurrentUser().getDisplayName(), sendtoken, token, new Constant().KEY_CONFIRM_SHARE_FAILED, new Until().dateTimeToString(new Date()));
                //update database: not sharing
                dialog.cancel();
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        alert.show();
    }
}