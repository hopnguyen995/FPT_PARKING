package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;

public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView user_name;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        user_name = itemView.findViewById(R.id.user_name);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
