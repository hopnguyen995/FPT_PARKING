package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Notification;
import com.example.fptparkingproject.ui.notifications.NotificationDetailActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Notification> listNotification;
    Constant constant = new Constant();

    public NotificationAdapter(Context context, ArrayList<Notification> listNotification) {
        this.context = context;
        this.listNotification = listNotification;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
            return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Notification notification = listNotification.get(position);
            ((ItemViewHolder) holder).txtTitle.setText(notification.getNotificationTitle());
            ((ItemViewHolder) holder).txtShortContent.setText(notification.getNotificationShortContent());
            if (notification.getNotificationImage() != null && !notification.getNotificationImage().isEmpty()) {
                new Until().circleTransformAvatar(context, ((ItemViewHolder) holder).imgImage, notification.getNotificationImage(), R.drawable.ic_image);
            }
            ((ItemViewHolder) holder).setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Gson gson = new Gson();
                    Intent intent = new Intent(context, NotificationDetailActivity.class);
                    intent.putExtra(constant.INTENT_NOTIFICATION_DETAIL_NOTIFICATION, gson.toJson(listNotification.get(position)));
                    view.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return listNotification.size();
    }
}
