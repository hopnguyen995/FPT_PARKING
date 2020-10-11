package com.example.fptparckingproject.qrshare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fptparckingproject.R;
import com.example.fptparckingproject.constant.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ShareActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private int QRcodeWidth = 350;
    private ImageView imageView;
    private Bitmap bitmap;
    private String uid;
    private String qrcodeshare;
    private String token = "";
    private TextView txtShareInfo;
    Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorToolbar)));
        setContentView(R.layout.activity_share);
        imageView = findViewById(R.id.imageView);
        txtShareInfo = findViewById(R.id.txtInfo);
        sharedPreferences = getApplicationContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        uid = sharedPreferences.getString("id", "");
        try {
            mAuth = FirebaseAuth.getInstance();
            qrcodeshare = "{\"" + constant.SHARE_VEHICLE + "\":\"" + uid + "\",\"" + constant.TOKEN + "\":\"" + token + "\"}";
            bitmap = TextToImageEncode(qrcodeshare);
            imageView.setImageBitmap(bitmap);
            txtShareInfo.setText(R.string.share_info);
        } catch (Exception ex) {

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
                    QRcodeWidth, QRcodeWidth, null
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