package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class WelcomeScreen extends AppCompatActivity {

    public TextView welcomeName, welcomeType;
    public Button logOut;
    public String[] nametype;
    public String typenameString,name,type;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeName = findViewById(R.id.welcomeMessageName);
        welcomeType = findViewById(R.id.welcomeMessage);
        logOut = findViewById(R.id.btnLogout);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        typenameString = currentUser.getDisplayName();


        if(currentUser == null){
            name= "poop!";
            type = "poopy!";
        }
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
    }
}
