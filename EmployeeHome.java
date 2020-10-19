package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class EmployeeHome extends AppCompatActivity{
    Button btnLogoutEmployee;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_home);
        btnLogoutEmployee = findViewById(R.id.btnLogoutEmployee);

        btnLogoutEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToLoginAcitivity = new Intent(EmployeeHome.this, LoginActivity.class);
                startActivity(intToLoginAcitivity);
            }
        });
    }
}
