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
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public EditText mEmail, mPassword;
    public Button mBtnSignUp;
    public TextView existingUserSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mBtnSignUp = findViewById(R.id.btnSignIn);
        existingUserSignIn = findViewById(R.id.nonExistingAccountTextview);

        mAuth = FirebaseAuth.getInstance();

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
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
                                task.getException().printStackTrace();
                                toastMessage("Sign up unsuccessful.");
                            }
                        }
                    });
                }
                else {
                    toastMessage("An error has occurred. Please try again!");
                }
            }
        });

        existingUserSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    //Customizable toast message maker
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}