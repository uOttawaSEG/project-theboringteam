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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    public EditText mEmail, mPassword;
    public Button mBtnSignUp, mBtnSignIn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDB;
    private String mUserID;


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
                                        directUserLogin();
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

    private void directUserLogin(){
        mAuth = FirebaseAuth.getInstance();
        mUserID = mAuth.getCurrentUser().getUid();
        mDB = FirebaseDatabase.getInstance().getReference().child("Users").child(mUserID);
        mDB.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.child("type").getValue(String.class);
                boolean hasBranch = dataSnapshot.child("branch_id").exists();

                if (userType.equals("customer")) {
                    startActivity(new Intent(MainActivity.this, welcomescreen_customer.class));
                } else if(userType.equals("employee") && hasBranch) {
                    startActivity(new Intent(MainActivity.this, welcomescreen_branch.class));
                } else if(userType.equals("employee")){
                    startActivity(new Intent(MainActivity.this, ChooseBranch.class));
                } else if(userType.equals("admin")) {
                    startActivity(new Intent(MainActivity.this, welcomescreen_admin.class));
                } else{
                    toastMessage("corrupt account: contact administrator");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Customizable toast message maker
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
