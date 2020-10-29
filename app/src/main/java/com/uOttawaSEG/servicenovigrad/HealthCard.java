package com.uOttawaSEG.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthCard extends AppCompatActivity {
    public TextView healthCardInfo;
    public EditText healthFirstName, healthLastName, healthBirthDate, healthAddress;
    public Button saveHealthCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_card);

        healthCardInfo = findViewById(R.id.healthCardInfo);
        healthFirstName = findViewById(R.id.healthFirstName);
        healthLastName = findViewById(R.id.healthLastName);
        healthBirthDate = findViewById(R.id.healthBirthDate);
        healthAddress = findViewById(R.id.healthAddress);
        saveHealthCard = findViewById(R.id.saveHealthCard);

        saveHealthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthCard.this, WelcomeScreen.class));
            }
        });

    }
}
