package com.example.fptparkingproject.ui.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fptparkingproject.AdminActivity;
import com.example.fptparkingproject.MainActivity;
import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.User;
import com.example.fptparkingproject.model.Vehicle;
import com.example.fptparkingproject.notification.SendNotif;
import com.example.fptparkingproject.untils.Until;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignInWithGoogle extends AppCompatActivity {
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
    private DatabaseReference ref;
    Constant constant = new Constant();
    Until until = new Until();
    private SharedPreferences prefs;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Check account signed in
        try {
            user = new User().getUser(prefs);
            if (mAuth.getCurrentUser() != null) {
                if (user.getRole()) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                return;
            }
        } catch (Exception e) {
            mAuth.signOut();
        }
        setContentView(R.layout.activity_sign_in_with_google);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonSignin = findViewById(R.id.sign_in_button);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.sign_in_google_button);
        setGoogleButtonText(btnSignIn, R.string.button_signin_google);

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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser fuser = mAuth.getCurrentUser();
                    User user = new User();
                    user.setUserid(fuser.getUid());
                    user.setUsername(fuser.getDisplayName());
                    user.setEmail(fuser.getEmail());
                    user.setRole(true);
                    user.saveUser(prefs, user);
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    Toast.makeText(SignInWithGoogle.this, R.string.signinsuccess,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInWithGoogle.this, R.string.signinfailed,
                            Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == constant.RC_SIGN_IN) {
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
        //getting the auth credential
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        //Now using firebase we are signing in the user here
        final User newUser = new User();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser uAuth = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            if (uAuth.getEmail().contains(new Constant().MAIL)) {
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
                                ref = until.connectDatabase();
                                ref.child(constant.TABLE_USERS).child(uAuth.getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        User fuser = dataSnapshot.getValue(User.class);
                                        if (fuser == null) {
                                            //create new user
                                            newUser.setUserid(uAuth.getUid());
                                            newUser.setEmail(uAuth.getEmail());
                                            newUser.setUsername(uAuth.getDisplayName());
                                            newUser.setRole(false);
                                            ref.child("Users").child(uAuth.getUid()).setValue(newUser);
                                        }else if (mAuth.getCurrentUser() != null) {
                                            fuser.saveUser(prefs, fuser);
                                            if (fuser.getToken().equals("")) {
                                                ref.child(constant.TABLE_USERS).child(mAuth.getUid()).child(constant.TABLE_USERS_CHILD_TOKEN).setValue(newUser.getToken());
                                            } else if (fuser.getToken().equals(newUser.getToken())) {
                                                fuser.saveUser(prefs, fuser);
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                finish();
                                                if (timerStarted) {
                                                    Timer.cancel();
                                                    timerStarted = false;
                                                }
                                                Toast.makeText(SignInWithGoogle.this, R.string.signinsuccess,
                                                        Toast.LENGTH_SHORT).show();
                                                getVehicleIDByUserID(fuser);
                                            } else {
                                                SendNotif sendNotif = new SendNotif();
                                                sendNotif.sendMessage("", "" + until.dateTimeToString(new Date()) + ".", "", fuser.getToken(), newUser.getToken(), constant.KEY_SIGNOUT, until.dateTimeToString(new Date()));
                                                Timer = new CountDownTimer(new Constant().TIMEOUT_SIGNIN, new Constant().COUNTDOWN) {
                                                    public void onTick(long millisUntilFinished) {
                                                        timerStarted = true;
                                                    }

                                                    public void onFinish() {
                                                        ref.child(constant.TABLE_USERS).child(mAuth.getUid()).child(constant.TABLE_USERS_CHILD_TOKEN).setValue(newUser.getToken());
                                                        Timer.cancel();
                                                    }
                                                }.start();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        mAuth.signOut();
                                        mGoogleSignInClient.signOut();
                                    }
                                });
                            } else {
                                mAuth.signOut();
                                mGoogleSignInClient.signOut();
                                Toast.makeText(SignInWithGoogle.this, R.string.accountnotsupport, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInWithGoogle.this, R.string.signinfailed,
                                    Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    }
                });
    }

    //this method is called on click
    private void signInGoogle() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, constant.RC_SIGN_IN);
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

    private void getVehicleIDByUserID(final User user) {
        ref.child(constant.TABLE_VEHICLES).child(user.getUserid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Vehicle> vehicleList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {
                        vehicleList.add(dataSnapshot.getValue(Vehicle.class));
                    }
                    SharedPreferences prefRemember = getSharedPreferences(constant.KEY_VEHICLEPLATE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefRemember.edit();
                    editor.putString(constant.KEY_VEHICLEPLATE, "");
                    for (Vehicle vehicle : vehicleList
                    ) {
                        if (vehicle.getStatus()) {
                            editor.putString(constant.KEY_VEHICLEPLATE, vehicle.getPlate());
                            break;
                        }
                    }
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}