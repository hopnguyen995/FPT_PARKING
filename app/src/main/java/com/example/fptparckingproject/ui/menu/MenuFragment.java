package com.example.fptparckingproject.ui.menu;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fptparckingproject.R;
import com.example.fptparckingproject.model.User;
import com.example.fptparckingproject.signin.SignInWithGoogle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MenuFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private ImageView imgAvatar;
    private TextView txtUsername;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        mAuth = FirebaseAuth.getInstance();
        final Button buttonSignOut = root.findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignIn.getClient(getActivity(), gso).signOut();
                startActivity(new Intent(getContext(), SignInWithGoogle.class));
                SharedPreferences prefRemember = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                prefRemember.edit().clear().commit();
                Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
            }
        });
        imgAvatar = root.findViewById(R.id.imgAvatar);
        txtUsername = root.findViewById(R.id.txtusername);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivityForResult(new Intent(getContext(), SignInWithGoogle.class),100);
        } else {
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            txtUsername.setText(sharedPreferences.getString("name",""));
            circleTransformAvatar(imgAvatar, mAuth.getCurrentUser().getPhotoUrl(), 50, 50);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200){
            circleTransformAvatar(imgAvatar, mAuth.getCurrentUser().getPhotoUrl(), 50, 50);
            sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
            txtUsername.setText(sharedPreferences.getString("name",""));
        }
    }

    public void circleTransformAvatar(final ImageView img, Uri uri, int width, int height) {
        Picasso.with(getContext()).load(uri).resize(width, height).placeholder(R.drawable.ic_user_avatar).into(img, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap imageBitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                imageDrawable.setCircular(true);
                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                img.setImageDrawable(imageDrawable);
            }

            @Override
            public void onError() {
                img.setImageResource(R.drawable.ic_user_avatar);
            }

        });
    }
}