package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeScreen extends AppCompatActivity {

    public TextView welcomeName, welcomeType;
    public Button logOut;
    public String[] nametype;
    public String name,type;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeName = findViewById(R.id.welcomeMessageName);
        welcomeType = findViewById(R.id.welcomeMessage);
        logOut = findViewById(R.id.btnLogout);
        nametype = mAuth.getCurrentUser().getDisplayName().split(" ");
        name = "Welcome " + nametype[0] + "!";
        type = "You are signed in as " + nametype[1] + ".";

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