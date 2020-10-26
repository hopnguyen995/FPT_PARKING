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
import com.example.fptparkingproject.model.Newfeed;
import com.example.fptparkingproject.ui.dashboard.DashboardDetailActivity;
import com.example.fptparkingproject.ui.notifications.NotificationDetailActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.gson.Gson;

import java.util.ArrayList;

public class NewfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Newfeed> listNewfeed;
    Constant constant = new Constant();

    public NewfeedAdapter(Context context, ArrayList<Newfeed> listNewfeed) {
        this.context = context;
        this.listNewfeed = listNewfeed;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(constant.TYPE_HEADER == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newfeed_header_layout, parent, false);
            return new HeaderViewHolder(view);
        }else if (constant.TYPE_ITEM == viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.newfeed_layout, parent, false);
            return new ItemViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Newfeed newfeed = listNewfeed.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).headerTitle.setText("");
        }else if (holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).txtTitle.setText(newfeed.getNewfeedTitle());
            ((ItemViewHolder) holder).txtShortContent.setText(newfeed.getNewfeedShortContent());
            new Until().circleTransformAvatar(context, ((ItemViewHolder) holder).imgImage, newfeed.getNewfeedImage(), R.drawable.ic_image);
            ((ItemViewHolder) holder).setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Gson gson = new Gson();
                    Intent intent = new Intent(context, DashboardDetailActivity.class);
                    intent.putExtra(constant.INTENT_NEWFEED_DETAIL_NEWFEED, gson.toJson(listNewfeed.get(position)));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listNewfeed.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return constant.TYPE_HEADER;
        return constant.TYPE_ITEM;
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
