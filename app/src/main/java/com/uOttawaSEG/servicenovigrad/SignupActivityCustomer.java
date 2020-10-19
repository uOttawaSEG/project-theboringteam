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


public class SignupActivityCustomer extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public EditText mEmail, mPassword,mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mUsername = findViewById(R.id.Username);

        mAuth = FirebaseAuth.getInstance();

        Button mbtnBack, mbtnSignUp;
        mbtnSignUp = findViewById(R.id.btnSignUp);
        mbtnBack = findViewById(R.id.btnBack);

        mbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                if (email.isEmpty()){
                    mEmail.setError("Please enter your email");
                    mPassword.requestFocus();
                }
                else if (pass.isEmpty()){
                    mPassword.setError("Please enter your password");
                    mPassword.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty()){
                    toastMessage("Fields are empty");
                }
                else if ((!email.isEmpty() && !pass.isEmpty())){
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("createUser","Success");
                                    if (task.isSuccessful()) {
                                        Log.d("createUserTask","Success");
                                        toastMessage("New account created!");
                                        Intent intToHomeActivity = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intToHomeActivity);
                                    }
                                    if (!task.isSuccessful()){
                                        Log.d("createUserTask","Not Success");
                                        task.getException().printStackTrace();
                                        toastMessage("Sign up unsuccessful.");
                                    }
                                }
                            });
                }
                else {
                    toastMessage("An error has occurred. Please try again!");
                }
                // specify as a customer in firebase after creation
                //redirect to helloCustomer
            }
        });

        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //directs to MainActivity
            }
        });
    }
    //Customizable toast message maker
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}