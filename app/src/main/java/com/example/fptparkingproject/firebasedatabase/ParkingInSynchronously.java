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
    DatabaseReference ref = new Until().connectDatabase();
    Boolean isSuccess = false;
    Boolean status = false;
    Constant constant = new Constant();

    public void addDataParkingSynchronous(final User user, final String vehicleid) {
        ref.child(constant.TABLE_PARKINGS_TEMP).child(user.getUserid()).child(constant.TABLE_PARKINGS_TEMP_CHILD_PARKING_STATUS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    status = (Boolean) snapshot.getValue();
                }
                addParking(user,vehicleid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                addParking(user,vehicleid);
            }
        });
    }

    private void addParking(User user,String vehicleid) {
        if (!status) {
            Parking parking = new Parking(new Until().randomID(), user.getUserid(), vehicleid, true, new Until().nomalizeDateTime(new Date()));
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
            ref.child(constant.TABLE_PARKINGS_TEMP).child(user.getUserid()).child(constant.TABLE_PARKINGS_TEMP_CHILD_PARKING_STATUS).setValue(true);
        } else {
            isSuccess = false;
        }
    }

    public boolean getResult() {
        return isSuccess;
    }
}
