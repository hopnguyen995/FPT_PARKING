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
import com.example.fptparkingproject.model.History;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_view_feedback, parent, false);
        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Feedback feedback = listFeedbacks.get(position);
        ((RowViewHolder) holder).txtName.setText(feedback.getFeedbackTitle());
        ((RowViewHolder) holder).txtDatetime.setText(new Until().nomalizeDateTime(feedback.getFeedbackDateTime()));
        ((RowViewHolder) holder).txtName.setText(feedback.getFeedbackContent());
    }

    private void setHeaderBg(View view) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg);
    }

    private void setContentBg(View view) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    @Override
    public int getItemCount() {
        return listHistories.size();
    }
}
