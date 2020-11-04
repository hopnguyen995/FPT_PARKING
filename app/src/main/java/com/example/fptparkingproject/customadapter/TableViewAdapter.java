package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.History;
import com.example.fptparkingproject.untils.Until;

import java.util.ArrayList;

public class TableViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<History> listHistories;

    public TableViewAdapter(Context context, ArrayList<History> listHistories) {
        this.context = context;
        this.listHistories = listHistories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.table_list_layout, parent, false);
        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        History history = listHistories.get(position);
        if(new Constant().PARKING_IN.equals(history.getHistoryImage())){
            ((RowViewHolder) holder).imgImage.setImageResource(R.drawable.ic_history_in);
        }else{
            ((RowViewHolder) holder).imgImage.setImageResource(R.drawable.ic_history_out);
        }
        ((RowViewHolder) holder).txtDatetime.setText(new Until().nomalizeDateTime(history.getHistoryDateTime()));
        ((RowViewHolder) holder).txtName.setText(history.getHistoryContent());
    }

    private void setHeaderBg(View view) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg);
    }

    private void setContentBg(View view) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    @Override
    public int getItemCount() {
        return listHistories.size();
    }
}
