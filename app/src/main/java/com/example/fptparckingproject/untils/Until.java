package com.example.fptparckingproject.untils;

import com.example.fptparckingproject.constant.Constant;

public class Until {
    Constant constant = new Constant();
    public boolean isParking(String input,String qrCode){
        return input.equals(qrCode);
    }
    public boolean isSharing(String input){
        return input.contains(constant.SHARE_VEHICLE);
    }
}
