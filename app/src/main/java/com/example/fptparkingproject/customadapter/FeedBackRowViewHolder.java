package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;

public class FeedBackRowViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle;
    public TextView txtContent;
    public TextView txtStatus;
    public TextView txtDatetime;
    public TextView txtReply;

    public FeedBackRowViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtDatetime = itemView.findViewById(R.id.txtFeedBackDatetimeAdmin);
        txtContent = itemView.findViewById(R.id.txtContent);
        txtStatus = itemView.findViewById(R.id.txtStatus);
        txtReply = itemView.findViewById(R.id.txtReply);
    }
}
