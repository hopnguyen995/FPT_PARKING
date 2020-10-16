package com.example.fptparkingproject.qrscan;

import androidx.annotation.NonNull;

import com.example.fptparkingproject.model.Parking;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class ParkingIn {
    Until until = new Until();
    //save parking in information to database
    public void parkingIn(User user){
        Parking parking = new Parking(until.randomID(),user.getUserid(),"7fCqjZjS19qQ6Ba4Kew1",true,until.nomalizeDateTime(new Date()));
        DatabaseReference ref = until.connectDatabase();
        ref.child("Parkings").child(parking.getParkingid()).setValue(parking);
    }
}
