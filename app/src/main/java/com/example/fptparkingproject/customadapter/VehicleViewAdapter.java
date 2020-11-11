package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Vehicle;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VehicleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Constant constant = new Constant();
    private ArrayList<Vehicle> listVehicles;
    private DatabaseReference ref = new Until().connectDatabase();;
    public VehicleViewAdapter(Context context, ArrayList<Vehicle> listVehicles) {
        this.context = context;
        this.listVehicles = listVehicles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.vehicle_layout, parent, false);
        return new VehicleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Vehicle vehicle = listVehicles.get(position);
        ((VehicleViewHolder) holder).txt_No.setText(String.valueOf(position + 1));
        ((VehicleViewHolder) holder).txt_number_plate.setText(vehicle.getPlate());
        ((VehicleViewHolder) holder).aSwitch.setChecked(vehicle.getStatus());
        ((VehicleViewHolder) holder).aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println(isChecked);
                for (Vehicle vh:listVehicles
                     ) {
                    if(vh.getStatus()){
                        vh.setStatus(false);
                    }
                }
                Vehicle vehicle1 = listVehicles.get(position);
                vehicle.setStatus(isChecked);
                listVehicles.set(position,vehicle1);
                Map<String, Object> map = new HashMap<>();
                for (Vehicle vh:listVehicles
                ) {
                    map.put(vh.getVehicleid(),vh);
                }
                ref.child(constant.TABLE_VEHICLES).child(vehicle1.getUserid()).setValue(map);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVehicles.size();
    }
}
