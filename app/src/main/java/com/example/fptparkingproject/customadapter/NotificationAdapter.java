package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.model.Notification;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    ArrayList<Notification> listNotification;

    public NotificationAdapter(Context context, ArrayList<Notification> listNotification) {
        this.context = context;
        this.listNotification = listNotification;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
            Notification notification = listNotification.get(position);
            holder.txtTitle.setText(notification.getNotificationTitle());
            holder.txtShortContent.setText(notification.getNotificationShortContent());
            Picasso.with(context).load(notification.getNotificationImage()).into(holder.imgImage);
    }

    @Override
    public int getItemCount() {
        return listNotification.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgImage;
        TextView txtTitle, txtShortContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.imgImage);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtShortContent = itemView.findViewById(R.id.txtShortContent);

        }
    }
}
