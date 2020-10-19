package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivityEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText mEmail, mPassword,mUsername;
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mUsername = findViewById(R.id.Username);


        Button mbtnBack, mbtnSignUp;
        mbtnSignUp = findViewById(R.id.btnSignUp);
        mbtnBack = findViewById(R.id.btnBack);

        mbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is where the .createUserWithEmailAndPassword will be implemented
                // create entry in firebase

                //redirect to MainActivity after Signup
            }
        });


        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //directs to MainActivity
            }
        });
    }
}