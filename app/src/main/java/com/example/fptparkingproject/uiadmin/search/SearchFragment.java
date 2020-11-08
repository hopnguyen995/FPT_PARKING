package com.example.fptparkingproject.uiadmin.search;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.customadapter.SearchAdapter;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private EditText inputSearch;
    private ListView list_view;
    private ArrayAdapter<String> adapter;

    ArrayList<User> listUser;
    SearchAdapter searchAdapter;
    private DatabaseReference ref;
    private SharedPreferences prefs;
    Constant constant = new Constant();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.search, container, false);
        list_view = root.findViewById(R.id.list_view);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final ArrayList<User> listUserDb = new ArrayList<>();
        listUser = new User().getListUser(prefs);
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        searchAdapter = new SearchAdapter(getContext(), listUser);
        list_view.setAdapter((ListAdapter) searchAdapter);
        ref = new Until().connectDatabase();
        ref.child(constant.TABLE_USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        listUserDb.add(user);
                    }
                    for (User uDb : listUserDb
                    ) {
                        boolean isExist = false;
                        for (User u : listUser
                        ) {
                            if (u.equals(uDb)) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            listUser.add(uDb);
                        }
                    }

                }
                searchAdapter = new SearchAdapter(getContext(), listUser);
                list_view.setAdapter((ListAdapter) searchAdapter);
                new User().saveListUser(prefs, listUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        searchAdapter = new SearchAdapter(getContext(), listUser);
        list_view.setAdapter((ListAdapter) searchAdapter);
        return root;
    }
}