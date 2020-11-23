package com.uOttawaSEG.servicenovigrad;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

    final List<Service> services = new ArrayList<Service>();
    final List<Service> listBranchServices = new ArrayList<Service>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen_branch_2);
        listViewServicesBranch = findViewById(R.id.listViewBranchServices);
        btnAddService = findViewById(R.id.addService);
        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayServicesAvailable();
            }
        });
        listViewServicesBranch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showDeleteDialog(service.getId(),service.getName());
                return true;
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
                    listBranchServices.clear();
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String nameS = postSnapshot.getValue(String.class);
                        String id = postSnapshot.getKey();
                        Service service = new Service(nameS,id);
                        toastMessage(id);
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
                final ServiceList serviceAdapter = new ServiceList(welcomescreen_branch_2.this, services);
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