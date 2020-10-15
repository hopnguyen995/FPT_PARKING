package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fptparkingproject.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView txtTitle;
    TextView txtShortContent;
    ImageView imgImage;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imgImage = itemView.findViewById(R.id.imgImage);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtShortContent = itemView.findViewById(R.id.txtShortContent);
    }
}
