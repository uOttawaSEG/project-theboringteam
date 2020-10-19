package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseTypeActivity extends AppCompatActivity {
    public Button  mBtnBack, mBtnCustomer, mBtnEmployee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choosetype_l);


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
                startActivity(new Intent(ChooseTypeActivity.this, SignupActivityCustomer.class));
            }
        });
        mBtnEmployee= findViewById(R.id.btnEmployee);
        mBtnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseTypeActivity.this, SignupActivityEmployee.class));
            }
        });
    }
}