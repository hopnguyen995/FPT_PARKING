package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;

public class FeedBackRowAdminViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle;
    public TextView txtStatus;
    public TextView txtDatetime;
    public Button btnShow;

    public FeedBackRowAdminViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txtFeedBackTitleAdmin);
        txtDatetime = itemView.findViewById(R.id.txtFeedBackDatetimeAdmin);
        txtStatus = itemView.findViewById(R.id.txtFeedBackStatusAdmin);
        btnShow = itemView.findViewById(R.id.btnFeedBackShowAdmin);
    }
}
