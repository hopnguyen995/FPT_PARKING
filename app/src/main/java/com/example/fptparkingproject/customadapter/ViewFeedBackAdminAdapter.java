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
import com.example.fptparkingproject.model.Feedback;
import com.example.fptparkingproject.uiadmin.feedback.FeedbackReplyAdminActivity;
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
        return new FeedBackRowViewHolder(itemView);
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
        ((FeedBackRowAdminViewHolder) holder).txtStatus.setText("1".equals(feedback.getFeedbackStatus()) ? R.string.feedback_not_approve : R.string.feedback_approve);

        ((FeedBackRowAdminViewHolder) holder).setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Gson gson = new Gson();
                Intent intent = new Intent(context, FeedbackReplyAdminActivity.class);
                intent.putExtra(constant.INTENT_DETAIL_FEEDBACK, gson.toJson(listFeedbacks.get(position)));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFeedbacks.size();
    }
}
