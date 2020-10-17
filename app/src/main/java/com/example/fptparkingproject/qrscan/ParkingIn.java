package com.example.fptparkingproject.qrscan;

import androidx.annotation.NonNull;

import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ParkingIn {
    Until until = new Until();
    DatabaseReference ref;

    //save parking in information to database
    public void parkingIn(User user) {
        if (!isExistParking(getListParking(), user)) {
            Parking parking = new Parking(until.randomID(), user.getUserid(), "7fCqjZjS19qQ6Ba4Kew1", true, until.nomalizeDateTime(new Date()));
            ref = until.connectDatabase();
            ref.child("Parkings").child(parking.getParkingid()).setValue(parking);
        }
    }

    private ArrayList<Parking> getListParking() {
        ref = until.connectDatabase();
        final ArrayList<Parking> listParking = new ArrayList<>();
        ref.child("Parkings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Parking parking = ds.getValue(Parking.class);
                        listParking.add(parking);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listParking;
    }

    private boolean isExistParking(ArrayList<Parking> listParking, User user) {
        boolean isExist = false;
        for (Parking parking : listParking
        ) {
            if (user.getUserid().equals(parking.getUserid())) {
                if (parking.isStatus()) {
                    isExist = true;
                    break;
                }
            }
        }
        return isExist;
    }
}
