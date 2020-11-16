package com.example.fptparkingproject.ui.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.NewfeedAdapter;
import com.example.fptparkingproject.model.Newfeed;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Newfeed> listNewfeed;
    NewfeedAdapter newfeedAdapter;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Constant constant = new Constant();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = root.findViewById(R.id.recyclerView1);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final ArrayList<Newfeed> listNewfeedDb = new ArrayList<>();
        listNewfeed = new Newfeed().getListNewfeed(prefs);
        if (listNewfeed == null) {
            listNewfeed = new ArrayList<>();
        }
        newfeedAdapter = new NewfeedAdapter(getContext(), listNewfeed);
        recyclerView.setAdapter(newfeedAdapter);
        ref = new Until().connectDatabase();
        ref.child(constant.TABLE_NEWFEEDS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Newfeed newfeed = ds.getValue(Newfeed.class);
                        listNewfeedDb.add(newfeed);
                    }
                    for (Newfeed newfDb : listNewfeedDb
                    ) {
                        boolean isExist = false;
                        for (Newfeed news : listNewfeed
                        ) {
                            if (news.equals(newfDb)) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            listNewfeed.add(newfDb);
                        }
                    }
                    Collections.sort(listNewfeed, Collections.<Newfeed>reverseOrder());
                }
                newfeedAdapter = new NewfeedAdapter(getContext(), listNewfeed);
                recyclerView.setAdapter(newfeedAdapter);
                new Newfeed().saveListNewfeed(prefs,listNewfeed);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setHasFixedSize(true);
        newfeedAdapter = new NewfeedAdapter(getContext(), listNewfeed);
        recyclerView.setAdapter(newfeedAdapter);
        return root;
    }
}