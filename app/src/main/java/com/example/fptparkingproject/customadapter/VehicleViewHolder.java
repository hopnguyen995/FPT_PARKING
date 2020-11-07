package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;

public class VehicleViewHolder extends RecyclerView.ViewHolder  {
    public TextView txt_No;
    public TextView txt_number_plate;
    public TextView txt_status;

    public VehicleViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_No = itemView.findViewById(R.id.txtNo);
        txt_number_plate = itemView.findViewById(R.id.txtPlate);
        txt_status = itemView.findViewById(R.id.txtStatus);
    }
}
