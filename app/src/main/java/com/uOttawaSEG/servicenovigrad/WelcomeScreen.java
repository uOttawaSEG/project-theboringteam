package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class WelcomeScreen extends AppCompatActivity {

    public TextView welcomeName, welcomeType;
    public Button logOut;
    public String[] nametype;
    public String typenameString,name,type;
    private FirebaseUser currentUser;


    // Get a reference to our posts
    final FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = userDatabase.getReference("service-novigrad-98386/Users");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeName = findViewById(R.id.welcomeMessageName);
        welcomeType = findViewById(R.id.welcomeMessage);
        logOut = findViewById(R.id.btnLogout);

        currentUser = new Users();
        typenameString = currentUser.getName();

        if(0==1){}
        else if(typenameString != null){
            nametype = Objects.requireNonNull(typenameString.split(" "));
            name = "Welcome " + nametype[0] + "!";
            type = "You are signed in as " + nametype[1] + ".";
        }
        else{
            name= "Welcome!";
            type = "You are signed in";
        }

        welcomeName.setText(name);
        welcomeType.setText(type);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
            }
        });
        // Attach a listener to read the data at our posts reference
        /**ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(user);
            }
        }*/
    }
}
