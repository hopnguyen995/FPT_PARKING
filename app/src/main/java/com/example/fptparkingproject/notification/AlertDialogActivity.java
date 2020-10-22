package com.example.fptparkingproject.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.firebasedatabase.ShareVehicleSynchoronously;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class AlertDialogActivity extends Activity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String title;
    private String message;
    private String username;
    private String userborrowname;
    private String userid;
    private String userborrowid;
    private String token;
    private String sendtoken;
    Constant constant = new Constant();

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
        title = intent.getStringExtra(constant.INTENT_ALERTDIALOG_TITLE);
        message = intent.getStringExtra(constant.INTENT_ALERTDIALOG_MESSAGE);
        username = intent.getStringExtra(constant.INTENT_ALERTDIALOG_USERNAME);
        userborrowname = intent.getStringExtra(constant.INTENT_ALERTDIALOG_USERBORROWNAME);
        userid = intent.getStringExtra(constant.INTENT_ALERTDIALOG_USERID);
        userborrowid = intent.getStringExtra(constant.INTENT_ALERTDIALOG_USERBORROWID);
        token = intent.getStringExtra(constant.INTENT_ALERTDIALOG_TOKEN);
        sendtoken = intent.getStringExtra(constant.INTENT_ALERTDIALOG_SENDTOKEN);
        SharedPreferences sharedPreferences = getSharedPreferences(constant.KEY_VEHICLEPLATE, Context.MODE_PRIVATE);
        final String plate = sharedPreferences.getString(constant.KEY_VEHICLEPLATE, "");
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_message);
        builder.setPositiveButton(getResources().getString(R.string.button_Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SendNotif().sendMessage("", "", "", sendtoken, token, new Constant().KEY_CONFIRM_SHARE_SUCCESS, new Until().dateTimeToString(new Date()));
                //update database: sharing
                new ShareVehicleSynchoronously().shareVehicle(username, userborrowname, userid, userborrowid, true, plate, token, sendtoken);
                dialog.cancel();
                finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.button_Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SendNotif().sendMessage("", mAuth.getCurrentUser().getDisplayName(), "", sendtoken, token, new Constant().KEY_CONFIRM_SHARE_FAILED, new Until().dateTimeToString(new Date()));
                //update database: not sharing
                new ShareVehicleSynchoronously().shareVehicle(username, userborrowname, userid, userborrowid, false, plate, token, sendtoken);
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