package com.example.fptparkingproject.customadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class HistoryAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<History> historyList;
    private int layout;


    public HistoryAdapter(Activity activity, int layout, ArrayList<History> historyList) {
        this.activity = activity;
        this.historyList = historyList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtViewDateTime;
        TextView txtContent;
        ImageView imgImage = null;
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = activity.getLayoutInflater().inflate(layout, null);
            viewHolder.txtViewDateTime = convertView.findViewById(R.id.txtViewDateTimeHistory);
            viewHolder.txtContent = convertView.findViewById(R.id.txtContentHistory);
            viewHolder.imgImage = convertView.findViewById(R.id.imgImageHistory);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        History history = historyList.get(position);
        viewHolder.txtViewDateTime.setText(history.getHistoryDateTime());
        viewHolder.txtContent.setText(history.getHistoryContent());
        if("InCome".equals(history.getHistoryImage())){
            viewHolder.imgImage.setImageResource(R.drawable.ic_history_in);
        }else{
            viewHolder.imgImage.setImageResource(R.drawable.ic_history_out);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView txtViewDateTime;
        TextView txtContent;
        ImageView imgImage;

    }
    /*private Context context;
    private ArrayList<History> listHistory;
    Constant constant = new Constant();

    public HistoryAdapter(Context context, ArrayList<History> listHistory) {
        this.context = context;
        this.listHistory = listHistory;
    }*/

    /*@NonNull
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
    }*/
}
