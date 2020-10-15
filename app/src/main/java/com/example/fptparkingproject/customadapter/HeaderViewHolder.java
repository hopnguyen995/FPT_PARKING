package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    TextView headerTitle;
    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        headerTitle = itemView.findViewById(R.id.txtHeader);
    }
}
