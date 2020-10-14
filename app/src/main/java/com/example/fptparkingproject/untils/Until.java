package com.example.fptparkingproject.untils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;

import com.example.fptparkingproject.constant.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Until {
    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }
        return false;
    }

    public String nomalizeDateTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }

    public String dateTimeToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS dd-MM-yyyy");
        return formatter.format(date);
    }

    public Date stringToDateTime(String input){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS dd-MM-yyyy");
        Date date = new Date();
        try {
            date = formatter.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public long subDateTime(Instant dateStart,Instant dateEnd){
        return Duration.between(dateStart, dateEnd).toMillis();
    }

    public DatabaseReference connectDatabase(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public void showAlertDialog(int title, int mesage, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getText(mesage));
        builder.setTitle(context.getResources().getText(title));
        builder.create();
        builder.show();
    }
}
