package com.example.fptparckingproject.untils;

import android.app.ActivityManager;
import android.content.Context;

import com.example.fptparckingproject.constant.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Until {
    Constant constant = new Constant();
    public boolean isParking(String input,String qrCode){
        return input.equals(qrCode);
    }
    public boolean isSharing(String input){
        return input.contains(constant.SHARE_VEHICLE);
    }

    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }
        return false;
    }

    public String dateTimeToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return formatter.format(date);
    }

    public DatabaseReference connectDatabase(){
        return FirebaseDatabase.getInstance().getReference();
    }
}
