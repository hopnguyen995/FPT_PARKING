package com.example.fptparckingproject.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fptparckingproject.MainActivity;
import com.example.fptparckingproject.R;
import com.example.fptparckingproject.constant.Constant;
import com.example.fptparckingproject.model.User;
import com.example.fptparckingproject.notification.SendNotif;
import com.example.fptparckingproject.untils.Until;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;

public class SignInWithGoogle extends AppCompatActivity {


    private static final int RC_SIGN_IN = 234;
    //Tag for the logs optional
    private static final String TAG = "simplifiedcoding";
    SignInButton btnSignIn;
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    private ProgressBar progressBar;
    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    private CountDownTimer Timer;
    private boolean timerStarted = false;
    private Button buttonSignin;
    private EditText email;
    private EditText password;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_in_with_google);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonSignin = findViewById(R.id.sign_in_button);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.sign_in_google_button);
        setGoogleButtonText(btnSignIn, R.string.button_signin_google);
        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Now we will attach a click listener to the sign_in_button
        //and inside onClick() method we are calling the signIn() method that will open
        //google sign in intent
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                signInGoogle();
            }
        });
        //sign in button
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    signIn(email.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    // sign in with button sign in
    public void signIn(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(SignInWithGoogle.this, R.string.signinsuccess,
                        Toast.LENGTH_SHORT).show();
                return;
            }
        });
        Toast.makeText(SignInWithGoogle.this, R.string.signinfailed,
                Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //function authentication with google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        //Now using firebase we are signing in the user here
        final User newUser = new User();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser uAuth = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            if (uAuth.getEmail().contains(new Constant().Mail)) {
                                FirebaseMessaging.getInstance().getToken()
                                        .addOnCompleteListener(new OnCompleteListener<String>() {
                                            @Override
                                            public void onComplete(@NonNull Task<String> task) {
                                                if (!task.isSuccessful()) {
                                                    return;
                                                }
                                                String token = task.getResult();
                                                newUser.setToken(token);
                                            }
                                        });
                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                ref.child("Users").child(uAuth.getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        User fuser = dataSnapshot.getValue(User.class);
                                        if (fuser == null) {
                                            //create new user
                                            newUser.setUserid(uAuth.getUid());
                                            newUser.setEmail(uAuth.getEmail());
                                            newUser.setUsername(uAuth.getDisplayName());
                                            sharedReference(newUser);
                                            ref.child("Users").child(uAuth.getUid()).setValue(newUser);
                                        }
                                        //get user exist
                                        if (fuser.getToken().equals(newUser.getToken())) {
                                            sharedReference(fuser);
                                            setResult(200, new Intent());
                                            finish();
                                            if (timerStarted) {
                                                Timer.cancel();
                                                timerStarted = false;
                                            }
                                            Toast.makeText(SignInWithGoogle.this, R.string.signinsuccess,
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            SendNotif sendNotif = new SendNotif();
                                            sendNotif.sendMessage(new Constant().PRESIGNOUT, "Tài khoản của bạn đã đăng nhập trên thiết bị khác vào lúc " + new Until().dateTimeToString(new Date()) + ".", fuser.getToken(), newUser.getToken());
                                            Timer = new CountDownTimer(10000, 1000) {
                                                public void onTick(long millisUntilFinished) {
                                                    timerStarted = true;
                                                }

                                                public void onFinish() {
                                                    ref.child("Users").child(mAuth.getUid()).child("token").setValue(newUser.getToken());
                                                    Timer.cancel();
                                                }
                                            }.start();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        mAuth.signOut();
                                    }
                                });
                            } else {
                                mAuth.signOut();
                                mGoogleSignInClient.signOut();
                                Toast.makeText(SignInWithGoogle.this, R.string.accountnotsupport, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInWithGoogle.this, R.string.signinfailed,
                                    Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    }
                });
    }

    //write user information
    private void sharedReference(User user) {
        SharedPreferences prefRemember = getApplicationContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefRemember.edit();
        editor.putString("name", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putString("id", user.getUserid());
        editor.putString("token", user.getToken());
        editor.commit();
    }

    //this method is called on click
    private void signInGoogle() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void setGoogleButtonText(SignInButton signInButton, int buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }
}