package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.History;
import com.example.fptparkingproject.model.Notification;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.ui.history.HistoryDetailActivity;
import com.example.fptparkingproject.ui.notifications.NotificationDetailActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<History> listHistory;
    Constant constant = new Constant();

    public HistoryAdapter(Context context, ArrayList<History> listHistory) {
        this.context = context;
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History history = listHistory.get(position);
        ((ItemViewHolder) holder).txtShortContent.setText(history.getHistoryContent());
        if (history.getHistoryImage() != null && !history.getHistoryImage().isEmpty()) {
            new Until().circleTransformAvatar(context, ((ItemViewHolder) holder).imgImage, history.getHistoryImage(), R.drawable.ic_image);
        }
        ((ItemViewHolder) holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Gson gson = new Gson();
                Intent intent = new Intent(context, HistoryDetailActivity.class);
                intent.putExtra(constant.INTENT_HISTORY_DETAIL_HISTORY, gson.toJson(listHistory.get(position)));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }
}
