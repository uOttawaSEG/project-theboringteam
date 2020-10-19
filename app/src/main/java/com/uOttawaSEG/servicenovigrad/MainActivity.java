package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        /*check if employee or customer then redirects them to their respective classes
                                            if employee
                                                get the username using getUsername and redirect to helloEmployee class
                                            if customer
                                                get the username using getUsername and redirect to helloCustomer class
                                            if admin
                                                redirect to the admin page
                                        */
                                        if (/*employee*/) {
                                            /* gets username*/
                                            // Here it is determined whether the Welcome Screen will log them in as employee or customer
                                            Intent i = new Intent(MainActivity.this, WelcomeScreen.class);
                                            startActivity(i);
                                        } else if (/*customer*/) {
                                            /*gets username*/
                                            Intent k = new Intent(MainActivity.this, WelcomeScreen.class);
                                            startActivity(k);
                                        } else {
                                            toastMessage("Login error.");
                                        };
                                    }
                                }
                        });
                }else {
                    toastMessage("An error has occurred.");
                };
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
private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
}
