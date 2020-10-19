package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    public EditText mEmail, mPassword;
    public Button mBtnSignUp, mBtnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mBtnSignIn = findViewById(R.id.btnSignIn);
        mBtnSignUp = findViewById(R.id.btnSignUp);
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

                }else {
                    toastMessage("An error has occurred.");
                }
            }
        });

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
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
    }
}
