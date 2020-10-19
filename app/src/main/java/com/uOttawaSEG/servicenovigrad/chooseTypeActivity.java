package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import android.util.Log;
import java.util.ArrayList;

public class chooseTypeActivity extends AppCompatActivity {
    public Button  mBtnBack, mBtnCustomer, mBtnEmployee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosetype);

        mBtnBack = (Button) findViewById(R.id.btnBack);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnCustomer= findViewById(R.id.btnCustomer);
        mBtnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseTypeActivity.this, SignupActivityCustomer.class));
        });
        mBtnEmployee= (Button) findViewById(R.id.btnEmployee);
        mBtnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseTypeActivity.this, SignupActivityEmployee.class));
            }
        });
    }
}
