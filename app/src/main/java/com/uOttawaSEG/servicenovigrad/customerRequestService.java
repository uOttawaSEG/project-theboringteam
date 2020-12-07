package com.uOttawaSEG.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customerRequestService extends AppCompatActivity {

    public Button btnBack, btnTorontoBranch;
    private DatabaseReference servicesDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_request_service);

        btnBack = findViewById(R.id.btnBack);
        btnTorontoBranch = findViewById(R.id.btnTorontoBranch);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customerRequestService.this, welcomescreen_customer.class));
            }
        });
        btnTorontoBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customerRequestService.this, listOfServicesCustomer.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        servicesDB = FirebaseDatabase.getInstance().getReference("Branches").child("MNuflpifDbyatZxrmyT").child("services");


    }


}
