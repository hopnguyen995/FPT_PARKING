package com.example.fptparkingproject.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.customadapter.NotificationAdapter;
import com.example.fptparkingproject.model.Notification;

import java.util.ArrayList;
import java.util.Date;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Notification> listNotification;
    NotificationAdapter notificationAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        listNotification = new ArrayList<>();
        listNotification.add(new Notification());
        for(int i = 0;i<10;i++){
            Notification notification1 = new Notification("1","Nga mời đi ăn \uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D","https://cdn.pixabay.com/photo/2019/02/25/03/57/hot-pot-4018869_960_720.jpg","️\uD83C\uDF89Tấm thiệp mời trên bàn thời gian địa điểm rõ ràng...la lá la là la ✉","️\uD83C\uDF89Tấm thiệp mời trên bàn thời gian địa điểm rõ ràng...la lá la là la ✉",new Date());
            Notification notification2 = new Notification("2","So cute...!","https://cdn.pixabay.com/photo/2014/11/30/14/11/kitty-551554_960_720.jpg","Đẹp chỉ để yêu, yêu kiều chỉ để ngắm. Nhưng chân ngắn mà đằm thắm … thì say đắm cả đời.","Đẹp chỉ để yêu, yêu kiều chỉ để ngắm. Nhưng chân ngắn mà đằm thắm … thì say đắm cả đời.",new Date());
            listNotification.add(notification1);
            listNotification.add(notification2);
        }

        notificationAdapter = new NotificationAdapter(getContext(),listNotification);
        recyclerView.setAdapter(notificationAdapter);
        return root;
    }
}