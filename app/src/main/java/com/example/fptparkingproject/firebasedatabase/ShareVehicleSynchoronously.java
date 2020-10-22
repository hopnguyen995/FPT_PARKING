package com.example.fptparkingproject.firebasedatabase;

import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.ShareTemp;
import com.example.fptparkingproject.model.ShareVehicle;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DatabaseReference;

public class ShareVehicleSynchoronously {
    DatabaseReference ref = new Until().connectDatabase();
    String shareVehicleID = new Until().randomID();
    Constant constant = new Constant();

    public void shareVehicle(String username, String userborrowname, String userid, String userborrowid, Boolean status, String plate, String token, String sendtoken) {
        ShareVehicle shareVehicle = new ShareVehicle(shareVehicleID, username, userborrowname, userid, userborrowid, status, plate, 0);
        ShareTemp shareTemp = new ShareTemp(username, userborrowid, userborrowname, status, token, sendtoken);
        ref.child(constant.TABLE_SHARES).child(shareVehicle.getUserborrowid()).setValue(shareVehicle);
        ref.child(constant.TABLE_SHARES_TEMP).child(userid).setValue(shareTemp);
    }
}
