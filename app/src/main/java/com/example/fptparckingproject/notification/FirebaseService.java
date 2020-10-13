package com.example.fptparckingproject.notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.fptparckingproject.MainActivity;
import com.example.fptparckingproject.R;
import com.example.fptparckingproject.constant.Constant;
import com.example.fptparckingproject.signin.SignInWithGoogle;
import com.example.fptparckingproject.untils.Until;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public FirebaseService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Until().connectDatabase().push();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // handle a notification payload.
        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            sendNotification(notification.getBody(), notification.getTitle(), notification.getImageUrl(), notification.getTag());
        }else if(remoteMessage.getData() != null){
            sendNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"), null, remoteMessage.getData().get("tag"));
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

    private void sendNotification(String messageBody, String messageTitle, Uri imageUri, String tagToken) {
        if (messageTitle.equals(new Constant().PRESIGNOUT)) {//sign out account in old device
            if (mAuth.getCurrentUser() != null) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignIn.getClient(getApplicationContext(), gso).signOut();
                SharedPreferences prefRemember = getApplicationContext().getSharedPreferences("account", Context.MODE_PRIVATE);
                prefRemember.edit().clear().commit();
                new SendNotif().sendMessage(new Constant().SIGNOUTSUCCESS, "Đăng nhập trên thiết bị mới thành công.", tagToken, tagToken);
                if (isRuning()) {
                    Intent dialogIntent = new Intent(getApplicationContext(), SignInWithGoogle.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                }
            }
        } else if (messageTitle.equals(new Constant().SIGNOUTSUCCESS)) {//update token account in new device
            new Until().connectDatabase().child("Users").child(mAuth.getUid()).child("token").setValue(tagToken);
        }
        // receive content notify
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        int notificationId = new Random().nextInt(60000);
        String channelId = getString(R.string.app_name);
        Bitmap bitmap = null;
        try {
            if (imageUri != null) {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
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
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notificationId, notificationBuilder.build());
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
