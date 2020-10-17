package com.example.fptparkingproject.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class ParkingInSynchronously {
    ArrayList<Parking> listParking = new ArrayList<>();
    DatabaseReference ref = new Until().connectDatabase();
    Boolean isSuccess = false;
    Constant constant = new Constant();
    public void addDataParkingSynchronous(final User user) {
        ref.child(constant.TABLE_PARKINGS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Parking parking = ds.getValue(Parking.class);
                        listParking.add(parking);
                    }
                }
                addParking(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                addParking(user);
            }
        });
    }

    private boolean isExistParking(User user) {
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

    public void addParking(User user) {
        if (listParking != null) {
            if (!isExistParking(user)) {
                Parking parking = new Parking(new Until().randomID(), user.getUserid(), "7fCqjZjS19qQ6Ba4Kew1", true, new Until().nomalizeDateTime(new Date()));
                ref.child(constant.TABLE_PARKINGS).child(parking.getParkingid()).setValue(parking, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            isSuccess = false;
                        } else {
                            isSuccess = true;
                        }
                    }
                });
            }
        } else {
            isSuccess = false;
        }
    }

    public boolean getResult() {
        return isSuccess;
    }
}
