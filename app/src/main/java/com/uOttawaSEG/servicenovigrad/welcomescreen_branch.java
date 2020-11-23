package com.uOttawaSEG.servicenovigrad;

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

    Button buttonAddService, hours;
    ListView listViewServices;
    DatabaseReference databaseServices, databaseBranch, database;
    TextView title,name;
    private FirebaseAuth mAuth;
    private String mUserID;
    Branch branch;
    int nextService;

    final List<Service> services = new ArrayList<>();
    final List<String> servicesID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen_branch);

        listViewServices = findViewById(R.id.listViewServices);
        buttonAddService = findViewById(R.id.addService);
        title = findViewById(R.id.serviceTitle);
        name = findViewById(R.id.welcomeMessageName);
        hours = findViewById(R.id.hours);

        mAuth = FirebaseAuth.getInstance();
        mUserID = mAuth.getCurrentUser().getUid();

        databaseServices= FirebaseDatabase.getInstance().getReference("Services");
        database= FirebaseDatabase.getInstance().getReference();

        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService();
            }
        });

        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(welcomescreen_branch.this, branchHours.class);
                intent.putExtra("Branches", branch.getId());
                startActivity(new Intent(welcomescreen_branch.this, branchHours.class));
            }
        });

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Service selected = services.get(i);
                if(selected == null){
                    toastMessage("broken");
                    return false;
                }
                updateDelete(selected, i);
                return true;
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        //attaching value listener event
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String branchID = dataSnapshot.child("Users").child(mUserID).child("branch_id").getValue(String.class);
                databaseBranch= FirebaseDatabase.getInstance().getReference("Branches").child(branchID);

                name.setText(dataSnapshot.child("Users").child(mUserID).child("name").getValue(String.class));

                servicesID.clear();
                branch = new Branch();

                branch.setAddress(dataSnapshot.child("Branches").child(branchID).child("address").getValue(String.class));
                branch.setId(branchID);
                branch.setName(dataSnapshot.child("Branches").child(branchID).child("name").getValue(String.class));

                title.setText("Welcome to\n"+branch.getName()+"Branch!");

                for(DataSnapshot postSnapshot : dataSnapshot.child("Branches").child(branchID).getChildren()){

                    String serviceID = postSnapshot.getValue(String.class);
                    servicesID.add(serviceID);

                }

                services.clear();

                for(String id : servicesID) {

                    if(dataSnapshot.child("Services").child(id).child("name").exists()){
                        String nameS = dataSnapshot.child("Services").child(id).child("name").getValue(String.class);
                        Service service = new Service(nameS, id);
                        services.add(service);
                    }
                }

                ServiceList sAdapter = new ServiceList(welcomescreen_branch.this, services);
                listViewServices.setAdapter(sAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });


    }



    private void updateDelete(final Service tempService, final int index) {




    }




    private void addService() {
        //getting values to save
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_addservice_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        final Button buttonCreate = dialogView.findViewById(R.id.buttonCreate);

        dialogBuilder.setTitle("New Service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the primary key for our product
                    String id = databaseServices.push().getKey();

                    //creating a Product object
                    Service service = new Service(name,id);
                    //saving the product
                    databaseServices.child(id).setValue(service);

                    //
                    services.add(service);

                    //displaying a success toast
                    toastMessage("Product added");
                    b.dismiss();
                }else{
                    //if the value is not given displaying a toast
                    toastMessage("Please enter a name");
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }



    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}