package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    public EditText mEmail, mPassword;
    public Button mBtnSignIn;
    public TextView nonExistingUserSignIn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mBtnSignIn = findViewById(R.id.btnSignIn);
        nonExistingUserSignIn = findViewById(R.id.nonExistingAccountTextview);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    toastMessage("Login successful.");
                    Intent intToHomeActivity = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intToHomeActivity);
                }
                else{
                    toastMessage("Please enter your login details.");
                }
            }
        };

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pwd = mPassword.getText().toString();
                if (email.isEmpty()){
                    mEmail.setError("Please enter your email");
                    mPassword.requestFocus();
                }
                else if (pwd.isEmpty()){
                    mPassword.setError("Please enter your password");
                    mPassword.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    toastMessage("Fields are empty");
                }
                else if (!email.isEmpty() && !pwd.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intToHomeActivity = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intToHomeActivity);
                            }
                            else{
                                toastMessage("Login error.");
                            }
                        }
                    });
                }else {
                    toastMessage("An error has occurred.");
                }
            }
        });

        nonExistingUserSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intToMainActivity);
            }
        });
    }
    //Customizable toast message maker
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}