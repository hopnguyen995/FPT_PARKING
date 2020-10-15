package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.model.Notification;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Notification> listNotification;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    public NotificationAdapter(Context context, ArrayList<Notification> listNotification) {
        this.context = context;
        this.listNotification = listNotification;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_header_layout, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
            return new ItemViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Notification notification = listNotification.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).headerTitle.setText(R.string.title_notifications);
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).txtTitle.setText(notification.getNotificationTitle());
            ((ItemViewHolder) holder).txtShortContent.setText(notification.getNotificationShortContent());
            Picasso.with(context).load(notification.getNotificationImage()).into(((ItemViewHolder) holder).imgImage);
        }
    }
    

    @Override
    public int getItemCount() {
        return listNotification.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }
    private boolean isPositionHeader(int position) {

        return position == 0;
    }

}
