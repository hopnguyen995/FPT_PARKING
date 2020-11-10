package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Feedback;
import com.example.fptparkingproject.uiadmin.feedback.FeedbackReplyAdminActivity;
import com.example.fptparkingproject.uiadmin.feedback.ListFeedbackAdminActivity;
import com.example.fptparkingproject.untils.Until;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewFeedBackAdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Feedback> listFeedbacks;
    Constant constant = new Constant();

    public ViewFeedBackAdminAdapter(Context context, ArrayList<Feedback> listFeedbacks) {
        this.context = context;
        this.listFeedbacks = listFeedbacks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.feedback_list_admin_layout, parent, false);
        return new FeedBackRowAdminViewHolder(itemView);
    }

    private void setHeaderBg(View view) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg);
    }

    private void setContentBg(View view) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Feedback feedback = listFeedbacks.get(position);
        ((FeedBackRowAdminViewHolder) holder).txtTitle.setText(feedback.getFeedbackTitle());
        ((FeedBackRowAdminViewHolder) holder).txtDatetime.setText(new Until().nomalizeDateTime(feedback.getFeedbackDateTime()));
        ((FeedBackRowAdminViewHolder) holder).txtContent.setText(feedback.getFeedbackContent());
        if("1".equals(feedback.getFeedbackStatus())){
            ((FeedBackRowAdminViewHolder) holder).txtStatus.setText(R.string.feedback_not_approve);
            ((FeedBackRowAdminViewHolder) holder).txtStatus.setTextColor(Color.RED);
            ((FeedBackRowAdminViewHolder) holder).setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Gson gson = new Gson();
                    Intent intent = new Intent(context, FeedbackReplyAdminActivity.class);
                    intent.putExtra(constant.INTENT_DETAIL_FEEDBACK, gson.toJson(listFeedbacks.get(position)));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });
        }else{
            ((FeedBackRowAdminViewHolder) holder).txtStatus.setText(R.string.feedback_approve);
            ((FeedBackRowAdminViewHolder) holder).txtStatus.setTextColor(Color.GREEN);
            ((FeedBackRowAdminViewHolder) holder).setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Toast.makeText(context, R.string.feedback_approved, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listFeedbacks.size();
    }
}
