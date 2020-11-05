package com.example.fptparkingproject.ui.qrshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Share;
import com.example.fptparkingproject.model.ShareTemp;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.untils.Until;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Date;

public class ShareActivity extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap bitmap;
    private String qrcodeshare;
    private TextView txtShareInfo;
    private TextView txtShareNotif;
    private Button buttonClickHere;
    private ProgressBar progressBar;
    Constant constant = new Constant();
    private SharedPreferences prefs;
    private DatabaseReference ref;
    ShareTemp shareTemp;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_toolbar_share);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_share);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user = new User().getUser(prefs);
        imageView = findViewById(R.id.imageView);
        txtShareInfo = findViewById(R.id.txtInfo);
        txtShareNotif = findViewById(R.id.txtShareNotif);
        buttonClickHere = findViewById(R.id.txtClickHere);
        progressBar = findViewById(R.id.progressBar2);
        ref = new Until().connectDatabase();
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        txtShareInfo.setVisibility(View.INVISIBLE);
        txtShareNotif.setVisibility(View.INVISIBLE);
        buttonClickHere.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences(constant.KEY_VEHICLEPLATE, Context.MODE_PRIVATE);
        String plate = sharedPreferences.getString(constant.KEY_VEHICLEPLATE, "");
        if (!plate.isEmpty()) {
            buttonClickHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref.child(constant.TABLE_SHARES_TEMP).child(user.getUserid()).child(constant.TABLE_SHARES_TEMP_CHILD_STATUS).setValue(false);
                    ref.child(constant.TABLE_SHARES).child(shareTemp.getUserborrowid()).child(constant.TABLE_SHARES_TEMP_CHILD_STATUS).setValue(false);
                    new SendNotif().sendMessage("", shareTemp.getUsername(), "", shareTemp.getSendtoken(), shareTemp.getToken(), new Constant().KEY_CONFIRM_SHARE_FAILED, new Until().dateTimeToString(new Date()));
                }
            });
            ref.child(constant.TABLE_SHARES_TEMP).child(user.getUserid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (snapshot.exists()) {
                        shareTemp = snapshot.getValue(ShareTemp.class);
                        if (shareTemp.getStatus()) {
                            imageView.setVisibility(View.INVISIBLE);
                            txtShareInfo.setVisibility(View.INVISIBLE);
                            txtShareNotif.setVisibility(View.VISIBLE);
                            buttonClickHere.setVisibility(View.VISIBLE);
                        } else {
                            txtShareNotif.setVisibility(View.INVISIBLE);
                            buttonClickHere.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.VISIBLE);
                            txtShareInfo.setVisibility(View.VISIBLE);
                        }
                    }else {
                        txtShareNotif.setVisibility(View.INVISIBLE);
                        buttonClickHere.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        txtShareInfo.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
            try {
                Share share = new Share();
                share.setShare_vehicle(user.getUserid());
                share.setToken(user.getToken());
                Gson gson = new Gson();
                qrcodeshare = "{\"" + constant.SHARE_VEHICLE + "\":\"" + user.getUserid() + "\",\"" + constant.TOKEN + "\":\"" + user.getToken() + "\"}";
                bitmap = TextToImageEncode(gson.toJson(share));
                imageView.setImageBitmap(bitmap);
                txtShareInfo.setText(R.string.share_info);
            } catch (Exception ex) {

            }
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            txtShareNotif.setVisibility(View.VISIBLE);
            txtShareNotif.setText(R.string.txtNotifNotHaveVehicle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    constant.QRCODE_WIDTH, constant.QRCODE_WIDTH, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.zxing_result_view) : getResources().getColor(R.color.zxing_status_text);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}