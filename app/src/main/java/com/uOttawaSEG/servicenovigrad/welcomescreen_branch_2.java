package com.uOttawaSEG.servicenovigrad;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;

public class welcomescreen_branch_2 extends AppCompatActivity {

    private ListView listViewServicesBranch;
    private Button btnAddService;
    private DatabaseReference servicesDB;
    private DatabaseReference branchServicesDb;

    final List<Service> services = new ArrayList<Service>();
    final List<Service> listBranchServices = new ArrayList<Service>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen_branch_2);

        //get current user

        //get the service name

        listViewServicesBranch = findViewById(R.id.listViewBranchServices);
        btnAddService = findViewById(R.id.addService);
        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayServicesAvailable();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        branchServicesDb = FirebaseDatabase.getInstance().getReference("Branches");
        branchServicesDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listBranchServices.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nameS = postSnapshot.child("name").getValue(String.class);
                    String id = postSnapshot.child("id").getValue(String.class);
                    Service service = new Service(nameS,id);
                    listBranchServices.add(service);
                }
                ServiceList branchAdapter = new ServiceList(welcomescreen_branch_2.this, listBranchServices);
                listViewServicesBranch.setAdapter(branchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    public void displayServicesAvailable() {
        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a service");
        servicesDB = FirebaseDatabase.getInstance().getReference("Services");
        servicesDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                services.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nameS = postSnapshot.child("name").getValue(String.class);
                    String id = postSnapshot.child("id").getValue(String.class);
                    Service service = new Service(nameS,id);
                    services.add(service);
                }
                final ServiceList serviceAdapter = new ServiceList(welcomescreen_branch_2.this, services);
                builder.setAdapter(serviceAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toastMessage("You clicked on: ");
                        String id = branchServicesDb.push().getKey();
                        //Service service = new Service(name,id);
                    }
                });
                builder.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}