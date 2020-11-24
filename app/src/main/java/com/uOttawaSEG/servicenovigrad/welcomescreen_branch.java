package com.uOttawaSEG.servicenovigrad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.List;

public class welcomescreen_branch extends AppCompatActivity {

    Button buttonAddService, hours, changeBranch, btnServiceRequest;
    ListView listViewServicesBranch;
    DatabaseReference databaseServices, database;
    TextView title,name;
    private DatabaseReference servicesDB;


    final List<Service> services = new ArrayList<>();
    final List<Service> listServicesBranch = new ArrayList<Service>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen_branch);

        listViewServicesBranch = findViewById(R.id.listViewServices);
        buttonAddService = findViewById(R.id.addService);
        title = findViewById(R.id.serviceTitle);
        name = findViewById(R.id.welcomeMessageName);
        hours = findViewById(R.id.hours);
        changeBranch = findViewById((R.id.changeBranch));

        btnServiceRequest = findViewById(R.id.requests);
        btnServiceRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcomescreen_branch.this, ServiceRequests.class));
            }
        });

        databaseServices= FirebaseDatabase.getInstance().getReference("Services");
        database= FirebaseDatabase.getInstance().getReference();

        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayServicesAvailable();
            }
        });

        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String branchid = getIntent().getStringExtra("branchID");
                Intent intent = new Intent(welcomescreen_branch.this, branchHours.class);
                intent.putExtra("Branches", branchid);
                startActivity(intent);
            }
        });

        listViewServicesBranch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = listServicesBranch.get(i);
                showDeleteDialog(service.getId(),service.getName());
                return true;
            }
        });

        changeBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(welcomescreen_branch.this, ChooseBranch.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        String branchid = getIntent().getStringExtra("branchID");
        servicesDB = FirebaseDatabase.getInstance().getReference("Branches").child(branchid).child("services");
        servicesDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listServicesBranch.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nameS = postSnapshot.getValue(String.class);
                    String id = postSnapshot.getKey();
                    Service service = new Service(nameS,id);
                    listServicesBranch.add(service);
                }
                ServiceList branchAdapter = new ServiceList(welcomescreen_branch.this, listServicesBranch);
                listViewServicesBranch.setAdapter(branchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    public void displayServicesAvailable() {
        // setup the alert builder
        final String branchid = getIntent().getStringExtra("branchID");
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
                final ServiceList serviceAdapter = new ServiceList(welcomescreen_branch.this, services);
                builder.setAdapter(serviceAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Service serviceName = serviceAdapter.getItem(which);
                        toastMessage("You clicked on: " + serviceName.getName());
                        servicesDB = FirebaseDatabase.getInstance().getReference("Branches").child(branchid).child("services");
                        servicesDB.child(serviceName.getId()).setValue(serviceName.getName());
                    }
                });
                builder.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    private void showDeleteDialog(final String serviceID, String serviceName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update_branch_service, null);
        dialogBuilder.setView(dialogView);

        final TextView title = dialogView.findViewById(R.id.serviceTitle);
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);

        title.setText(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBranchService(serviceID);
                b.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    private void deleteBranchService(String id){
        String branchid = getIntent().getStringExtra("branchID");
        //getting the branch service to be deleted from firebase
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Branches").child(branchid).child("services").child(id);
        //deleting the branch service
        dR.removeValue();
        toastMessage("Branch service deleted");
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}