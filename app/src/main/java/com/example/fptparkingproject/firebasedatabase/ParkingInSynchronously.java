package com.example.fptparkingproject.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.ShareVehicle;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.model.Vehicle;
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
    Boolean isAddParkingSuccess = false;
    Boolean isAddParkingTempSuccess = false;
    Boolean isSharing = false;
    Boolean status = false;
    String parkingID = new Until().randomID();
    Constant constant = new Constant();

    public void checkShareVehicle(final User user, final String plate) {
        ref.child(constant.TABLE_SHARES).child(user.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ShareVehicle shareVehicle = snapshot.getValue(ShareVehicle.class);
                    if (shareVehicle.getStatus()) {
                        user.setUserid(shareVehicle.getUserid());
                        if (shareVehicle.getCount() < 1) {
                            shareVehicle.setCount(shareVehicle.getCount() + 1);
                        } else {
                            shareVehicle.setStatus(false);
                            shareVehicle.setCount(0);
                        }
                        addDataParkingSynchronous(user, shareVehicle.getPlate(), shareVehicle);
                    }
                } else if (!plate.isEmpty()) {
                    addDataParkingSynchronous(user, plate, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addDataParkingSynchronous(final User user, final String plate, final ShareVehicle shareVehicle) {
        ref.child(constant.TABLE_PARKINGS_TEMP).child(user.getUserid()).child(constant.TABLE_PARKINGS_TEMP_CHILD_PARKING_STATUS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    status = (Boolean) snapshot.getValue();
                }
                addParking(user, plate, shareVehicle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                addParking(user, plate, shareVehicle);
            }
        });
    }

    private void addParking(User user, String plate, ShareVehicle shareVehicle) {
        if (!status) {
            Parking parking = new Parking(parkingID, user.getUsername(), plate, true, new Until().nomalizeDateTime(new Date()));
            ref.child(constant.TABLE_PARKINGS).child(user.getUserid()).child(parking.getParkingid()).setValue(parking, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error != null) {
                        isAddParkingSuccess = false;
                    } else {
                        isAddParkingSuccess = true;
                    }
                }
            });
            ref.child(constant.TABLE_PARKINGS_TEMP).child(user.getUserid()).child(constant.TABLE_PARKINGS_TEMP_CHILD_PARKING_STATUS).setValue(true, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error != null) {
                        isAddParkingTempSuccess = false;
                    } else {
                        isAddParkingTempSuccess = true;
                    }
                }
            });
            if (shareVehicle != null) {
                ref.child(constant.TABLE_SHARES).child(shareVehicle.getUserborrowid()).setValue(shareVehicle);
                ref.child(constant.TABLE_SHARES_TEMP).child(shareVehicle.getUserid()).child(constant.TABLE_SHARES_TEMP_CHILD_STATUS).setValue(false);
            }
        } else {
            isAddParkingSuccess = false;
        }
    }

    public boolean getResult() {
        return isAddParkingSuccess && isAddParkingTempSuccess;
    }
}
