package com.example.fptparkingproject.ui.history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.HistoryAdapter;
import com.example.fptparkingproject.model.History;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistorysFragment extends Fragment {
    RecyclerView recyclerView;
    HistoryAdapter HistoryAdapter;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Constant constant = new Constant();
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_historys, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final ArrayList<History> listHistoryDb = new ArrayList<>();
        ref = new Until().connectDatabase();
        user = new User().getUser(prefs);
        ref.child(constant.TABLE_PARKINGS).child(user.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Parking parking = ds.getValue(Parking.class);
                        History history = new History();
                        history.setHistoryId(parking.getParkingid());
                        history.setHistoryDateTime(parking.getTime());
                        history.setHistoryContent(parking.isType() ? "Bạn đã cho xe vào" : "Bạn đã lấy xe ra");
                        history.setHistoryImage(parking.isType() ? "InCome" : "GetOut");
                        listHistoryDb.add(history);
                    }
                }
                HistoryAdapter = new HistoryAdapter(getContext(), listHistoryDb);
                recyclerView.setAdapter(HistoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        HistoryAdapter = new HistoryAdapter(getContext(), listHistoryDb);
        recyclerView.setAdapter(HistoryAdapter);
        return root;
    }
}
