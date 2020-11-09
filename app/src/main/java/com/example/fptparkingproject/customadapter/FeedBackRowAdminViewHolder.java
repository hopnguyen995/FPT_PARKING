package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;

public class FeedBackRowAdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtTitle;
    public TextView txtStatus;
    public TextView txtDatetime;
    public TextView txtContent;
    private ItemClickListener itemClickListener;

    public FeedBackRowAdminViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtDatetime = itemView.findViewById(R.id.txtFeedBackDatetimeAdmin);
        txtStatus = itemView.findViewById(R.id.txtStatus);
        txtContent = itemView.findViewById(R.id.txtFeedBackContentAdmin);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
