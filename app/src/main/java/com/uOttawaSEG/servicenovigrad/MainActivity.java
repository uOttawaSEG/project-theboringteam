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


public class MainActivity extends AppCompatActivity {
    public EditText mEmail, mPassword;
    public Button mBtnSignUp, mBtnSignIn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mBtnSignUp = findViewById(R.id.btnSignUp);
        mBtnSignIn = findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pwd = mPassword.getText().toString();
                if (pwd.isEmpty()) {
                    mPassword.setError("Please enter your password");
                    mPassword.requestFocus();
                }
                if (email.isEmpty()) {
                    mEmail.setError("Please enter your email");
                    mPassword.requestFocus();
                }
                if (email.isEmpty() || pwd.isEmpty()) {
                    toastMessage("Field(s) are empty");
                }  else {

                    mAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if("joDPeX1hEPRFCm4VxkoA3pN384v1".equals(mAuth.getCurrentUser().getUid()))
                                            startActivity(new Intent(MainActivity.this, welcomescreen_admin.class));
                                        else
                                            startActivity(new Intent(MainActivity.this, WelcomeScreen.class));
                                    } else {
                                        toastMessage("Login information not found in database. Check fields again!");
                                    }
                                }
                            });
                }
            }
        });
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChooseTypeActivity.class));
            }
        });
    }

    //Customizable toast message maker
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
