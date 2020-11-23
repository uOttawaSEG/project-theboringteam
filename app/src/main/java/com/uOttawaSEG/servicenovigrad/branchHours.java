package com.uOttawaSEG.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class branchHours extends AppCompatActivity {

    TimePicker monTimePickIn, tueTimePickIn, wedTimePickIn, thuTimePickIn, friTimePickIn, satTimePickIn, sunTimePickIn;
    TimePicker monTimePickOut, tueTimePickOut, wedTimePickOut, thuTimePickOut, friTimePickOut, satTimePickOut, sunTimePickOut;
    Button btnBack, btnSaveBranchTime;
    TextView mondayTime, tuesdayTime, wednesdayTime, thursdayTime, fridayTime, saturdayTime, sundayTime;
    public DatabaseReference mRef;
    private FirebaseDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_hours);
        monTimePickIn = findViewById(R.id.monTimePickIn);
        monTimePickIn.setIs24HourView(true);
        tueTimePickIn = findViewById(R.id.tueTimePickIn);
        tueTimePickIn.setIs24HourView(true);
        wedTimePickIn = findViewById(R.id.wedTimePickIn);
        wedTimePickIn.setIs24HourView(true);
        thuTimePickIn = findViewById(R.id.thuTimePickIn);
        thuTimePickIn.setIs24HourView(true);
        friTimePickIn = findViewById(R.id.friTimePickIn);
        thuTimePickIn.setIs24HourView(true);
        satTimePickIn = findViewById(R.id.satTimePickIn);
        thuTimePickIn.setIs24HourView(true);
        sunTimePickIn = findViewById(R.id.sunTimePickIn);
        thuTimePickIn.setIs24HourView(true);

        monTimePickOut = findViewById(R.id.monTimePickOut);
        monTimePickOut.setIs24HourView(true);
        tueTimePickOut = findViewById(R.id.tueTimePickOut);
        tueTimePickOut.setIs24HourView(true);
        wedTimePickOut = findViewById(R.id.wedTimePickOut);
        wedTimePickOut.setIs24HourView(true);
        thuTimePickOut = findViewById(R.id.thuTimePickOut);
        thuTimePickOut.setIs24HourView(true);
        friTimePickOut = findViewById(R.id.friTimePickOut);
        friTimePickOut.setIs24HourView(true);
        satTimePickOut = findViewById(R.id.satTimePickOut);
        satTimePickOut.setIs24HourView(true);
        sunTimePickOut = findViewById(R.id.sunTimePickOut);
        sunTimePickOut.setIs24HourView(true);

        mondayTime = findViewById(R.id.mondayTime);
        tuesdayTime = findViewById(R.id.tuesdayTime);
        wednesdayTime = findViewById(R.id.wednesdayTime);
        thursdayTime = findViewById(R.id.thursdayTime);
        fridayTime = findViewById(R.id.fridayTime);
        saturdayTime = findViewById(R.id.saturdayTime);
        sundayTime = findViewById(R.id.sundayTime);
        btnBack = findViewById(R.id.btnBack);
        btnSaveBranchTime = findViewById(R.id.btnSaveBranchTime);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        btnSaveBranchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mondayMinutesIn = monTimePickIn.getCurrentMinute();
                int mondayHoursIn = monTimePickIn.getCurrentHour();
                String mondayTimeIn = mondayHoursIn+":"+mondayMinutesIn;
                int tuesdayMinutesIn = tueTimePickIn.getCurrentMinute();
                int tuesdayHoursIn = tueTimePickIn.getCurrentHour();
                String tuesdayTimeIn = tuesdayHoursIn+":"+tuesdayMinutesIn;
                int wednesdayMinutesIn = wedTimePickIn.getCurrentMinute();
                int wednesdayHoursIn = wedTimePickIn.getCurrentHour();
                String wednesdayTimeIn = wednesdayHoursIn+":"+wednesdayMinutesIn;
                int thursdayMinutesIn = thuTimePickIn.getCurrentMinute();
                int thursdayHoursIn = thuTimePickIn.getCurrentHour();
                String thursdayTimeIn = thursdayHoursIn+":"+thursdayMinutesIn;
                int fridayMinutesIn = friTimePickIn.getCurrentMinute();
                int fridayHoursIn = friTimePickIn.getCurrentHour();
                String fridayTimeIn = fridayHoursIn+":"+fridayMinutesIn;
                int saturdayMinutesIn = satTimePickIn.getCurrentMinute();
                int saturdayHoursIn = satTimePickIn.getCurrentHour();
                String saturdayTimeIn = saturdayHoursIn+":"+saturdayMinutesIn;
                int sundayMinutesIn = sunTimePickIn.getCurrentMinute();
                int sundayHoursIn = sunTimePickIn.getCurrentHour();
                String sundayTimeIn = sundayHoursIn+":"+sundayMinutesIn;

                int mondayMinutesOut = monTimePickOut.getCurrentMinute();
                int mondayHoursOut = monTimePickOut.getCurrentHour();
                String mondayTimeOut = mondayHoursOut+":"+mondayMinutesOut;
                int tuesdayMinutesOut = tueTimePickOut.getCurrentMinute();
                int tuesdayHoursOut = tueTimePickOut.getCurrentHour();
                String tuesdayTimeOut = tuesdayHoursOut+":"+tuesdayMinutesOut;
                int wednesdayMinutesOut = wedTimePickOut.getCurrentMinute();
                int wednesdayHoursOut = wedTimePickOut.getCurrentHour();
                String wednesdayTimeOut = wednesdayHoursOut+":"+wednesdayMinutesOut;
                int thursdayMinutesOut = thuTimePickIn.getCurrentMinute();
                int thursdayHoursOut = thuTimePickIn.getCurrentHour();
                String thursdayTimeOut = thursdayHoursOut+":"+thursdayMinutesOut;
                int fridayMinutesOut = friTimePickIn.getCurrentMinute();
                int fridayHoursOut = friTimePickIn.getCurrentHour();
                String fridayTimeOut = fridayHoursOut+":"+fridayMinutesOut;
                int saturdayMinutesOut = satTimePickIn.getCurrentMinute();
                int saturdayHoursOut = satTimePickIn.getCurrentHour();
                String saturdayTimeOut = saturdayHoursOut+":"+saturdayMinutesOut;
                int sundayMinutesOut = sunTimePickIn.getCurrentMinute();
                int sundayHoursOut = sunTimePickIn.getCurrentHour();
                String sundayTimeOut = sundayHoursOut+":"+sundayMinutesOut;

                mDB = FirebaseDatabase.getInstance();
                String branchID = getIntent().getStringExtra("Branches");
                mRef = mDB.getReference("Branches").child(branchID).child("workingHours");
                mRef.child("Monday").setValue(mondayTimeIn+" - "+mondayTimeOut);
                mRef.child("Tuesday").setValue(tuesdayTimeIn+" - "+tuesdayTimeOut);
                mRef.child("Wednesday").setValue(wednesdayTimeIn+" - "+wednesdayTimeOut);
                mRef.child("Thursday").setValue(thursdayTimeIn+" - "+thursdayTimeOut);
                mRef.child("Friday").setValue(fridayTimeIn+" - "+fridayTimeOut);
                mRef.child("Saturday").setValue(saturdayTimeIn+" - "+saturdayTimeOut);
                mRef.child("Sunday").setValue(sundayTimeIn+" - "+sundayTimeOut);
                finish();
            }
        });
    }
}
