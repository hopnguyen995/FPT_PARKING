package com.example.fptparkingproject.constant;

public class Constant {
    //intent
    public final int SIGNIN_REQUEST_CODE = 100;
    public final int SIGNIN_RESPONSE_CODE = 200;
    public final int QRSCAN_REQUEST_CODE = 300;
    public final int QRSCAN_RESPONSE_CODE = 400;
    public final int PERMISSION_GRANTED_REQUEST_CODE = 500;
    public final int RC_SIGN_IN = 600;
    public final String INTENT_QRSCAN_RESULT = "QRResult";
    public final String INTENT_ALERTDIALOG_TITLE = "title";
    public final String INTENT_ALERTDIALOG_MESSAGE = "message";
    public final String INTENT_ALERTDIALOG_SENDTOKEN = "sendtoken";
    public final String INTENT_ALERTDIALOG_TOKEN = "token";
    public final String INTENT_NOTIFICATION_DETAIL_NOTIFICATION = "notification";
    //Qrcode
    public final int QRCODE_WIDTH = 350;
    //SharedPreferences
    public final String KEY_NOTIFICATION = "notifications";
    public final String KEY_USER = "user";
    public final String KEY_VEHICLEID = "vehicleid";
    //mail
    public final String MAIL = "@fpt.edu.vn";
    //parking
    public final String PARKING_IN = "12urX&cB^Ls7Ep=28Wmd5Bg&xOAsdN7E!&&34xdh64Zs1PfKCuEbUzHs7ZD5GW!h2FoP!jISaJ73ugnEi4VTfJ^RPctn69H7@^Wz";
    public final String PARKING_OUT = "QaBG2FFzLq0s0szkncQWMSfP5sa98LicB!SalJANH!qHuR@ILKEgZIUGpdDszKAFL^UDVEIKn!Ko9iHLMFvcFaXJyvOOeS4BYbF3";
    public final String SHARE_VEHICLE = "share_vehicle";
    public final String TOKEN = "token";
    public final String KEY_SIGNOUT = "38489997243116404069";
    public final String KEY_SUCCESS = "39175305556757245386";
    public final String KEY_CONFIRM_SHARE = "89139950973670169294";
    public final String KEY_CONFIRM_SHARE_SUCCESS = "06763050968907669648";
    public final String KEY_SHARE_SUCCESS = "10738968957923868185";
    public final String KEY_CONFIRM_SHARE_FAILED = "52352307670109756189";
    public final String KEY_SHARE_FAILED = "81896888455510588869";
    public final String KEY_SHARE_ERROR = "98432742839287548736";
    //TIME OUT
    public final int TIMEOUT_SIGNIN = 10000;
    public final int COUNTDOWN = 1000;
    public final int TIMEOUT_PARKING = 5000;

    //notification
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    public static final String CHANNEL = "Channel human readable title";
    //JSON key data
    public final String JSON_KEY_DATA = "data";
    public final String JSON_KEY_TO = "to";
    public final String JSON_KEY_BODY = "body";
    public final String JSON_KEY_TITLE = "title";
    public final String JSON_KEY_SENDTOKEN = "sendtoken";
    public final String JSON_KEY_TOKEN = "token";
    public final String JSON_KEY_CODE = "code";
    public final String JSON_KEY_TIME = "time";
    //database
    public final String TABLE_USERS = "Users";
    public final String TABLE_USERS_CHILD_TOKEN = "token";
    public final String TABLE_PARKINGS = "Parkings";
    public final String TABLE_NOTIFICATIONS = "Notifications";
    public final String TABLE_PARKINGS_TEMP = "Parkings_temp";
    public final String TABLE_PARKINGS_TEMP_CHILD_PARKING_STATUS = "parking_status";
    public final String TABLE_VEHICLES_TEMP = "Vehicles_temp";
    public final String TABLE_VEHICLES_TEMP_CHILD_VEHICLEID = "vehicleid";
}
