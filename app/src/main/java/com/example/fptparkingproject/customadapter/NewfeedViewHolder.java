package com.example.fptparkingproject.customadapter;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.model.Newfeed;
import com.example.fptparkingproject.untils.Until;

public class NewfeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtTitle;
    public TextView txtShortContent;
    public TextView txtDateTime;
    public ImageView imgImage;
    public TextView txtSeeMore;
    public TextView txtLongContent;
    public NewfeedViewHolder(@NonNull View itemView) {
        super(itemView);
        imgImage = itemView.findViewById(R.id.imgImage);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtShortContent = itemView.findViewById(R.id.txtShortContent);
        txtDateTime = itemView.findViewById(R.id.txtDateTime);
        txtSeeMore = itemView.findViewById(R.id.txtSeeMore);
        txtLongContent = itemView.findViewById(R.id.txtLongContent);

        txtSeeMore.setOnClickListener(this);
    }

    public void bind(Newfeed newfeed){
        boolean expanded = newfeed.isExpanded();
        // Set the visibility based on state
        txtSeeMore.setVisibility(expanded ? View.VISIBLE : View.GONE);
        txtTitle.setText(newfeed.getNewfeedTitle());
        txtDateTime.setText(new Until().nomalizeDateTime(newfeed.getNewfeedDateTime()));
        txtShortContent.setText(newfeed.getNewfeedShortContent());
        txtLongContent.setText(newfeed.getNewfeedLongContent());
    }

    @Override
    public void onClick(View v) {
       txtSeeMore.setOnClickListener(this);
    }
}
