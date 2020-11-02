package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivityCustomer extends AppCompatActivity {

    public EditText mEmail, mPassword, mUsername;
    public Button mBtnSignUp, mBtnBack;
    public boolean canSignIn;
    public DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_customer);

        mUsername = findViewById(R.id.username);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        mBtnSignUp = findViewById(R.id.btnSignUp);
        mBtnBack = findViewById(R.id.btnBack);
        canSignIn = true;


        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                String name = mUsername.getText().toString();
                Log.d("btnSignupClicked", "succeess");

                if (email.isEmpty()){
                    mEmail.setError("Please enter your email");
                    mEmail.requestFocus();
                    canSignIn = false;
                }
                if (pass.length()<8 || pass.length() > 32){
                    mPassword.setError("Please enter a 8-32 character password");
                    mPassword.requestFocus();
                    canSignIn = false;
                }
                for(int i = 0; i<name.length();i++) {
                    if (!Character.isLetter(name.charAt(i)) || (name.charAt(i) == ' ')) {
                        mUsername.setError("Please enter an actual name");
                        mUsername.requestFocus();
                        canSignIn = false;
                        break;
                    }
                }
                if(name.isEmpty()){
                    mUsername.setError("Input Name");
                    mUsername.requestFocus();
                    canSignIn = false;
                }
                if(email.isEmpty() || pass.isEmpty() || name.isEmpty()){
                    toastMessage("Field(s) are empty");
                    canSignIn = false;
                }


                if(canSignIn) {
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(SignupActivityCustomer.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        toastMessage("New account created!");
                                        String name = mUsername.getText().toString();
                                        String email = mEmail.getText().toString();

                                        //FIREBASE STUFF
                                        FirebaseUser mUser = mAuth.getCurrentUser();

                                        //Creating a local user
                                        user = new User(mUser.getUid(),name,email,"customer");
                                        mRef = mDB.getReference("Users/"+mUser.getUid());

                                        //Setting the local user to firebase
                                        mRef.setValue(user);

                                        //Old way of adding user type
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .build();

                                        mUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    startActivity(new Intent(SignupActivityCustomer.this, WelcomeScreen.class));
                                                }
                                            }
                                        });
                                    }
                                    if (!task.isSuccessful()){
                                        toastMessage("Sign up unsuccessful.");
                                    }
                                }
                            });
                }
                else {
                    toastMessage("An error has occurred. Please try again!");
                    canSignIn = true;
                }
            }
        });
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}