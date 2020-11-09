package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fptparkingproject.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtTitle;
    public TextView txtShortContent;
    public ImageView imgImage;
    private ItemClickListener itemClickListener;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imgImage = itemView.findViewById(R.id.imgImage);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtShortContent = itemView.findViewById(R.id.txtShortContent);
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
