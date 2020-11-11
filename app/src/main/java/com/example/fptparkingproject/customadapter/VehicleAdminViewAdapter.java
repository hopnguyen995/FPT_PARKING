package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.model.Vehicle;

import java.util.ArrayList;

public class VehicleAdminViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Vehicle> listVehicles;

    public VehicleAdminViewAdapter(Context context, ArrayList<Vehicle> listVehicles) {
        this.context = context;
        this.listVehicles = listVehicles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.vehicle_admin_layout, parent, false);
        return new VehicleAdminViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Vehicle vehicle = listVehicles.get(position);
        ((VehicleAdminViewHolder) holder).txt_No.setText(String.valueOf(position + 1));
        ((VehicleAdminViewHolder) holder).txt_number_plate.setText(vehicle.getPlate());
        if (vehicle.getStatus()) {
            ((VehicleAdminViewHolder) holder).txt_status.setText(R.string.txtStatusActive);
            ((VehicleAdminViewHolder) holder).txt_status.setTextColor(Color.GREEN);
        } else {
            ((VehicleAdminViewHolder) holder).txt_status.setText(R.string.txtStatusNotActive);
            ((VehicleAdminViewHolder) holder).txt_status.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return listVehicles.size();
    }
}
