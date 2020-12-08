package com.uOttawaSEG.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
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

public class RequestDisplay extends AppCompatActivity {

    String requestID = getIntent().getStringExtra("requestID");
    ListView listViewRequestInfo;
    ArrayList<String> info = new ArrayList();

    Button approve, deny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_display);

        listViewRequestInfo = findViewById(R.id.listViewRequestInfo);
        deny = findViewById(R.id.Deny);
        approve = findViewById(R.id.Approve);

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denyRequest(requestID);
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveRequest(requestID);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference requestedDB = FirebaseDatabase.getInstance().getReference("ServiceRequests").child(requestID);
        requestedDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                info.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.child("info").getChildren()) {

                    String infoChunk = postSnapshot.getKey() +" : "+ postSnapshot.getValue(String.class);

                    info.add(infoChunk);


                }

                RequirementList sAdapter = new RequirementList(com.uOttawaSEG.servicenovigrad.RequestDisplay.this, info);
                listViewRequestInfo.setAdapter(sAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });

    }

    private void approveRequest(String requestID){

        DatabaseReference requestedDB = FirebaseDatabase.getInstance().getReference("ServiceRequests").child(requestID);
        requestedDB.child("status").setValue("approved");
    }

    private void denyRequest(String requestID){

        DatabaseReference requestedDB = FirebaseDatabase.getInstance().getReference("ServiceRequests").child(requestID);
        requestedDB.removeValue();
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
