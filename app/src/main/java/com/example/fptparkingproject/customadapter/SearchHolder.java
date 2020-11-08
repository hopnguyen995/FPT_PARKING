package com.example.fptparkingproject.customadapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fptparkingproject.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchHolder extends RecyclerView.ViewHolder{

    public EditText inputSearch;
    public ListView list_view;
    public TextView user_name;
    public SearchHolder(@NonNull View itemView) {
        super(itemView);

        inputSearch = itemView.findViewById(R.id.inputSearch);
        list_view = itemView.findViewById(R.id.list_view);
        user_name = itemView.findViewById(R.id.user_name);
    }

}
