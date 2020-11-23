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
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference usersDatabase;
    private String userid;

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

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userid = user.getUid();
        usersDatabase = mFirebaseDatabase.getReference("Branches").child(userid);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(branchHours.this, MainActivity.class));


            }
        });
        btnSaveBranchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int monday_minutes = simpleTimePicker.getCurrentMinute();

                mRef = mDB.getReference("Users/"+mUser.getUid());

                //Setting the local user to firebase
                mRef.setValue(user);

            }
        });
    }
}
