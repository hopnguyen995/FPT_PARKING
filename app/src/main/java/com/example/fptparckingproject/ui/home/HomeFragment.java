package com.example.fptparckingproject.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fptparckingproject.R;
import com.example.fptparckingproject.constant.Constant;
import com.example.fptparckingproject.model.Share;
import com.example.fptparckingproject.notification.SendNotif;
import com.example.fptparckingproject.qrscan.QRScanActivity;
import com.example.fptparckingproject.qrshare.ShareActivity;
import com.example.fptparckingproject.signin.SignInWithGoogle;
import com.example.fptparckingproject.untils.Until;
import com.google.firebase.auth.FirebaseAuth;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class HomeFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private TextView user;
    private String uid;
    private String username;
    Constant constant = new Constant();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final Button buttonQRScan = root.findViewById(R.id.buttonQR);
        buttonQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), QRScanActivity.class), 300);
            }
        });
        final Button buttonShare = root.findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShareActivity.class));
            }
        });
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        user = root.findViewById(R.id.user);
        mAuth = FirebaseAuth.getInstance();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivityForResult(new Intent(getContext(), SignInWithGoogle.class),100);
        } else {
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("name","");
            user.setText(username);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == 400) {
            //get result qr scan
            String QRresult = data.getStringExtra("QRResult");
            //process
            if (new Until().isParking(constant.PARKING_IN,QRresult)) {
                showAlertDialog(R.string.information, R.string.parkingin);
            } else if (new Until().isParking(constant.PARKING_OUT,QRresult)) {
                showAlertDialog(R.string.information, R.string.parkingout);
            } else if (new Until().isSharing(QRresult)) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Share shareVehicle = mapper.readValue(QRresult,Share.class);
                    String token = shareVehicle.getToken();
                    //Update database


                    //notification
                    new SendNotif().sendMessage("Thông báo",username + " đã mượn xe của bạn. \uD83D\uDEF5",token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), R.string.not_support, Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 100 && resultCode == 200){
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("name","");
            user.setText(username);
        }
    }

    private void showAlertDialog(int title, int mesage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getContext().getResources().getText(mesage));
        builder.setTitle(getContext().getResources().getText(title));
        builder.create();
        builder.show();
    }
}