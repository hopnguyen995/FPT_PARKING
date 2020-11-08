package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.untils.Until;

import java.util.ArrayList;

public class HistoryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Parking> listParkingHistoryDb;

    public HistoryViewAdapter(Context context, ArrayList<Parking> listParkingHistoryDb) {
        this.context = context;
        this.listParkingHistoryDb = listParkingHistoryDb;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.history_layout, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Parking parking = listParkingHistoryDb.get(position);
        if (parking.isType()) {
            ((HistoryViewHolder) holder).imgImage.setImageResource(R.drawable.ic_history_in);
        } else {
            ((HistoryViewHolder) holder).imgImage.setImageResource(R.drawable.ic_history_out);
        }
        ((HistoryViewHolder) holder).txtDatetime.setText(new Until().nomalizeDateTime(parking.getTime()));
        ((HistoryViewHolder) holder).txtName.setText(parking.getUsername());
    }

    @Override
    public int getItemCount() {
        return listParkingHistoryDb.size();
    }
}
