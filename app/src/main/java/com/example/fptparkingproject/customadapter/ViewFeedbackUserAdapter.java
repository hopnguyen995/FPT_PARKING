package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Feedback;
import com.example.fptparkingproject.untils.Until;

import java.util.ArrayList;

public class ViewFeedbackUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Feedback> listFeedbacks;

    public ViewFeedbackUserAdapter(Context context, ArrayList<Feedback> listFeedbacks) {
        this.context = context;
        this.listFeedbacks = listFeedbacks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_feedback_layout, parent, false);
        return new FeedBackRowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Feedback feedback = listFeedbacks.get(position);
        ((FeedBackRowViewHolder) holder).txtTitle.setText(feedback.getFeedbackTitle());
        ((FeedBackRowViewHolder) holder).txtDatetime.setText(new Until().nomalizeDateTime(feedback.getFeedbackDateTime()));
        ((FeedBackRowViewHolder) holder).txtContent.setText(feedback.getFeedbackContent());
        ((FeedBackRowViewHolder) holder).txtStatus.setText("1".equals(feedback.getFeedbackStatus()) ? "Not Approve" : "Approved");
    }

    private void setHeaderBg(View view) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg);
    }

    private void setContentBg(View view) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    @Override
    public int getItemCount() {
        return listFeedbacks.size();
    }
}
