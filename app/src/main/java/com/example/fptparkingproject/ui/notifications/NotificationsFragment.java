package com.example.fptparkingproject.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.NotificationAdapter;
import com.example.fptparkingproject.model.Notification;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Notification> listNotification;
    NotificationAdapter notificationAdapter;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Constant constant = new Constant();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final ArrayList<Notification> listNotificationDb = new ArrayList<>();
        listNotification = new Notification().getListNotification(prefs);
        if (listNotification == null) {
            listNotification = new ArrayList<>();
        }
        notificationAdapter = new NotificationAdapter(getContext(), listNotification);
        recyclerView.setAdapter(notificationAdapter);
        ref = new Until().connectDatabase();
        ref.child(constant.TABLE_NOTIFICATIONS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Notification notification = ds.getValue(Notification.class);
                        listNotificationDb.add(notification);
                    }
                    for (Notification notifDb : listNotificationDb
                    ) {
                        boolean isExist = false;
                        for (Notification notif : listNotification
                        ) {
                            if (notif.equals(notifDb)) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            listNotification.add(notifDb);
                        }
                    }
                    Collections.sort(listNotification,Collections.<Notification>reverseOrder());
                }
                notificationAdapter = new NotificationAdapter(getContext(), listNotification);
                recyclerView.setAdapter(notificationAdapter);
                new Notification().saveListNotification(prefs,listNotification);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        notificationAdapter = new NotificationAdapter(getContext(), listNotification);
        recyclerView.setAdapter(notificationAdapter);
        return root;
    }
}