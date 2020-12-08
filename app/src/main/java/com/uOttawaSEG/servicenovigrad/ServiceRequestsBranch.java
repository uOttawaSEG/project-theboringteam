package com.uOttawaSEG.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceRequestsBranch extends AppCompatActivity {

        DatabaseReference databaseRequirements;
        ListView listViewRequests;
        ArrayList<ServiceRequest> requests = new ArrayList();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_service_requests_branch);

            listViewRequests = findViewById(R.id.listViewRequests);

            listViewRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(ServiceRequestsBranch.this, RequestDisplay.class);
                    intent.putExtra("requestID", requests.get(i).getRequestID());
                    startActivity(intent);
                    return true;
                }
            });

        }

        @Override
        protected void onStart() {
            super.onStart();

            databaseRequirements = FirebaseDatabase.getInstance().getReference("Requests");
            databaseRequirements.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    requests.clear();

                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if(postSnapshot.child("branchID").getValue(String.class).equals(getIntent().getStringExtra("branchID"))){
                            String userID = postSnapshot.child("userID").getValue(String.class);
                            String branchID = postSnapshot.child("branchID").getValue(String.class);
                            String serviceID = postSnapshot.child("serviceID").getValue(String.class);
                            String requestID = postSnapshot.getKey();


                            ServiceRequest tempRequest = new ServiceRequest(requestID,userID,branchID,serviceID);
                            requests.add(tempRequest);
                        }

                    }

                    ServiceRequestList sAdapter = new ServiceRequestList(com.uOttawaSEG.servicenovigrad.ServiceRequestsBranch.this, requests);
                    listViewRequests.setAdapter(sAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error){}
            });

        }

        private void toastMessage(String message) {
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
    }
