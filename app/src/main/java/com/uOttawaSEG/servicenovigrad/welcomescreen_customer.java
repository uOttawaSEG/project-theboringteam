package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class welcomescreen_customer extends AppCompatActivity {

    public TextView welcomeName, welcomeType;
    public Button logOut;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference usersDatabase;
    private String userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen_branch);

        welcomeName = findViewById(R.id.welcomeMessageName);
        welcomeType = findViewById(R.id.welcomeMessage);
        logOut = findViewById(R.id.btnLogout);

        // ### FIREBASE STUFF ###
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userid = user.getUid();
        usersDatabase = mFirebaseDatabase.getReference("Users").child(userid);


        usersDatabase.addValueEventListener(new ValueEventListener() { //gets called when there is a change in the database OR when the activity starts
            @Override
            //a snapshot of the database is like a snapshot of the whole database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //since we have the whole database, we need to iterate through all the users inside the database
                    User mUser = new User();
                    //we first go to the child of the user id and
                    mUser.setName(snapshot.child("name").getValue(String.class)); //set the name
                    mUser.setEmail(snapshot.child("email").getValue(String.class)); //set the email
                    mUser.setType(snapshot.child("type").getValue(String.class)); //set the type

                    welcomeName.setText("Welcome " + mUser.getName()+"!");
                    welcomeType.setText("You are signed in as " + mUser.getType() + ".");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcomescreen_customer.this, MainActivity.class));
            }
        });
    }


    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
