package com.example.fptparkingproject.notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import androidx.core.app.NotificationCompat;

import com.example.fptparkingproject.MainActivity;
import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.ui.signin.SignInWithGoogle;
import com.example.fptparkingproject.untils.Until;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Constant constant = new Constant();
    Until until = new Until();
    private SharedPreferences prefs;
    User user;

    public FirebaseService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Until().connectDatabase().push();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // handle a notification payload.
        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            sendNotification(notification.getBody(), notification.getTitle(), notification.getImageUrl().toString(), notification.getTag(), "", "", "");
        } else if (remoteMessage.getData() != null) {
            sendNotification(remoteMessage.getData().get(constant.JSON_KEY_BODY), remoteMessage.getData().get(constant.JSON_KEY_TITLE), remoteMessage.getData().get(constant.JSON_KEY_IMAGE), remoteMessage.getData().get(constant.JSON_KEY_SENDTOKEN), remoteMessage.getData().get(constant.JSON_KEY_TOKEN), remoteMessage.getData().get(constant.JSON_KEY_CODE), remoteMessage.getData().get(constant.JSON_KEY_TIME));
        }
    }

    @Override
    public void onNewToken(String token) {
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        //FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    private void sendNotification(String messageBody, String messageTitle, String imageUri, String sendToken, String token, String code, String time) {
        //Check account signed in
        try {
            user = new User().getUser(prefs);
            if (mAuth.getCurrentUser() != null) {
                if (!user.getRole()) {
                    Date dateTimeReceive = until.stringToDateTime(time);
                    long subtime = until.subDateTime(dateTimeReceive.toInstant(), Instant.now());
                    String nomalizeTime = until.nomalizeDateTime(dateTimeReceive);
                    if (constant.KEY_SIGNOUT.equals(code)) {//sign out account in old device
                        if (mAuth.getCurrentUser() != null) {
                            mAuth.signOut();
                            FirebaseAuth.getInstance().signOut();
                            user.saveUser(prefs, new User());
                            if (subtime < constant.TIMEOUT_SIGNIN) {
                                new SendNotif().sendMessage("", "", "", sendToken, sendToken, constant.KEY_SUCCESS, until.dateTimeToString(new Date()));
                            }
                            if (isRuning()) {
                                Intent dialogIntent = new Intent(getApplicationContext(), SignInWithGoogle.class);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(dialogIntent);
                            }
                            messageTitle = getResources().getString(R.string.title_warning);
                            messageBody = getResources().getString(R.string.message_signin_warning) + nomalizeTime;
                        }
                    } else if (constant.KEY_SUCCESS.equals(code)) {//update token account in new device
                        messageTitle = getResources().getString(R.string.title_notificaton);
                        messageBody = messageBody + getResources().getString(R.string.message_signin_success);
                        until.connectDatabase().child("Users").child(mAuth.getUid()).child("token").setValue(sendToken);
                    } else if (constant.KEY_CONFIRM_SHARE.equals(code)) {
                        //confirm share vehicle
                        if (isRuning() && subtime < constant.TIMEOUT_SIGNIN) {
                            String userborrowid = messageTitle;
                            String userborrowname = messageBody;
                            messageTitle = getResources().getString(R.string.title_confirm);
                            messageBody = messageBody + getResources().getString(R.string.message_borrow_vehicle);
                            Intent dialogIntent = new Intent(getApplicationContext(), AlertDialogActivity.class);
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_TITLE, messageTitle);
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_MESSAGE, messageBody);
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_USERNAME, user.getUsername());
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_USERBORROWNAME, userborrowname);
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_USERID, user.getUserid());
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_USERBORROWID, userborrowid);
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_TOKEN, token);
                            dialogIntent.putExtra(constant.INTENT_ALERTDIALOG_SENDTOKEN, sendToken);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(dialogIntent);
                        } else {
                            //failed
                            new SendNotif().sendMessage("", "", "", sendToken, token, constant.KEY_SHARE_FAILED, until.dateTimeToString(new Date()));
                            //update database: not sharing

                        }
                    } else if (constant.KEY_CONFIRM_SHARE_SUCCESS.equals(code)) {
                        //recevice confirm share success
                        messageTitle = getResources().getString(R.string.title_notificaton);
                        messageBody = getResources().getString(R.string.message_borrow_vehicle_success);
                        new SendNotif().sendMessage("", mAuth.getCurrentUser().getDisplayName(), "", sendToken, token, constant.KEY_SHARE_SUCCESS, until.dateTimeToString(new Date()));
                    } else if (constant.KEY_CONFIRM_SHARE_FAILED.equals(code)) {
                        //recevice confirm share failed
                        messageTitle = getResources().getString(R.string.title_notificaton);
                        messageBody = messageBody + getResources().getString(R.string.message_denied_require_borrow_vehicle);
                        new SendNotif().sendMessage("", mAuth.getCurrentUser().getDisplayName(), "", sendToken, token, constant.KEY_SHARE_ERROR, until.dateTimeToString(new Date()));
                    } else if (constant.KEY_SHARE_SUCCESS.equals(code)) {
                        //show share success
                        messageTitle = getResources().getString(R.string.title_notificaton);
                        messageBody = messageBody + getResources().getString(R.string.message_was_borrow_vehicle_success);
                    } else if (constant.KEY_SHARE_FAILED.equals(code)) {
                        //show share failed
                        messageTitle = getResources().getString(R.string.title_notificaton);
                        messageBody = getResources().getString(R.string.message_error_borrow_vehicle) + nomalizeTime;
                    } else if (constant.KEY_SHARE_ERROR.equals(code)) {
                        messageTitle = getResources().getString(R.string.title_notificaton);
                        messageBody = getResources().getString(R.string.message_denied_borrow_vehicle) + messageBody;
                    }
                    // receive content notify
                    if (!constant.KEY_CONFIRM_SHARE.equals(code)) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                        int notificationId = new Random().nextInt(60000);
                        String channelId = getString(R.string.app_name);
                        Bitmap bitmap = null;
                        try {
                            if (!imageUri.isEmpty()) {
                                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(imageUri));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        NotificationCompat.Builder notificationBuilder =
                                new NotificationCompat.Builder(this, channelId)
                                        .setContentTitle(messageTitle)
                                        .setContentText(messageBody)
                                        .setAutoCancel(true)
                                        .setSound(defaultSoundUri)
                                        .setContentIntent(pendingIntent)
                                        .setLargeIcon(bitmap)  //set it in the notification
                                        .setSmallIcon(R.drawable.ic_parking_notification)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .setPriority(NotificationManager.IMPORTANCE_HIGH);
                        if (bitmap != null) {
                            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
                        }
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        // Since android Oreo notification channel is needed.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel(
                                    channelId,
                                    constant.CHANNEL,
                                    NotificationManager.IMPORTANCE_DEFAULT);
                            notificationManager.createNotificationChannel(channel);
                        }
                        notificationManager.notify(notificationId, notificationBuilder.build());
                    }
                }
            }
        } catch (Exception e) {
            mAuth.signOut();
        }
    }

    private boolean isRuning() {
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        Boolean isInBackground = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        if (isInBackground) {
            return false;
        } else {
            return true;
        }
    }
}
