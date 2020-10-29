package com.uOttawaSEG.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DriversLicense extends AppCompatActivity {
    public TextView driversLicenseInfo, typeOfLicense;
    public EditText licenseFirstName, licenseLastName, licenseBirthDate, licenseAddress;
    public Button saveDriversLicense;
    public Spinner licenseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivers_license);

        driversLicenseInfo = findViewById(R.id.driversLicenseInfo);
        typeOfLicense = findViewById(R.id.typeOfLicense);
        licenseFirstName = findViewById(R.id.licenseFirstName);
        licenseLastName = findViewById(R.id.licenseLastName);
        licenseBirthDate = findViewById(R.id.licenseBirthDate);
        licenseAddress = findViewById(R.id.licenseAddress);
        saveDriversLicense = findViewById(R.id.saveDriversLicense);
        licenseType = findViewById(R.id.licenseType);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("G1");
        arrayList.add("G2");
        arrayList.add("G");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        licenseType.setAdapter(arrayAdapter);
        licenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveDriversLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriversLicense.this, WelcomeScreen.class));
            }
        });

    }
}
