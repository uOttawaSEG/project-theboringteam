package com.uOttawaSEG.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PhotoID extends AppCompatActivity {

    public TextView photoIDInfo;
    public EditText photoIDFirstName, photoIDLastName, photoIDBirthDate, photoIDAddress;
    public Button savePhotoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_id);

        photoIDInfo = findViewById(R.id.photoIDInfo);
        photoIDFirstName = findViewById(R.id.photoIDFirstName);
        photoIDLastName = findViewById(R.id.photoIDLastName);
        photoIDBirthDate = findViewById(R.id.photoIDBirthDate);
        photoIDAddress = findViewById(R.id.photoIDAddress);
        savePhotoID = findViewById(R.id.savePhotoID);

        savePhotoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoID.this, WelcomeScreen.class));
            }
        });


    }
}
