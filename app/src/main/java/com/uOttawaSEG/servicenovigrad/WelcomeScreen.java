package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class WelcomeScreen extends AppCompatActivity {

    public TextView welcomeName, welcomeType;
    public Button logOut;
    public String welcome, role;
    private Users currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeName = findViewById(R.id.welcomeMessageName);
        welcomeType = findViewById(R.id.welcomeMessage);
        logOut = findViewById(R.id.btnLogout);

        currentUser = new Users();

        if(currentUser.getEmail() != null && currentUser.getName() != null && currentUser.getId() != null && currentUser.getType() != null){
            welcomeName.setText("Welcome " + currentUser.getName() + "!");
            welcomeType.setText("You are signed in as " + currentUser.getType() + ".");
        }
        else{
            welcome = "Error loading profile";
            role = "Please log in again.";

            if(currentUser.getEmail() == null){
                welcome = welcome+"s";
            }
            if(currentUser.getName() == null){
                welcome = welcome+"h";
            }
            if(currentUser.getId() == null){
                welcome = welcome+"i";
            }
            if(currentUser.getType() == null){
                welcome = welcome+"t";
            }
            welcomeName.setText(welcome);
            welcomeType.setText(role);
        }



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
