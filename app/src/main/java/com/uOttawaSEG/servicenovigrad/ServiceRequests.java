package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ServiceRequests extends AppCompatActivity {

    RecyclerView serviceRequestsView;
    ArrayList<String> serviceRequests = new ArrayList<String> (Arrays.asList("Driver's License", "Health card", "Dummy service"));
    ArrayList<Boolean> isApproved = new ArrayList<Boolean>(Arrays.asList(true, false,true));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requests);
        serviceRequestsView = findViewById(R.id.serviceRequests);

        serviceRequestViewAdapter serviceRequestViewAdapter = new serviceRequestViewAdapter(this, serviceRequests,isApproved);
        serviceRequestsView.setAdapter(serviceRequestViewAdapter);
        serviceRequestsView.setLayoutManager(new LinearLayoutManager(this));

    }
}