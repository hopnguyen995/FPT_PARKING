package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName;
    public TextView txtDatetime;
    public ImageView imgImage;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtDatetime = itemView.findViewById(R.id.txtFeedBackDatetimeAdmin);
        imgImage = itemView.findViewById(R.id.imageViewType);
    }
}
