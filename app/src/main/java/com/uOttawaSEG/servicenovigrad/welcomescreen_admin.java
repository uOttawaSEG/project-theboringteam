package com.uOttawaSEG.servicenovigrad;

import android.os.Bundle;
import android.text.TextUtils;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class welcomescreen_admin extends AppCompatActivity {

    Button buttonAddService;
    ListView listViewServices;
    DatabaseReference databaseServices;

    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen_administrator);

        listViewServices = (ListView) findViewById(R.id.listViewServices);
        buttonAddService = (Button) findViewById(R.id.addService);

        databaseServices= FirebaseDatabase.getInstance().getReference("Services");
        services = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService();
            }
        });

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service selected = services.get(i);
                updateDelete(selected.getName());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value listener event
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                //iterating through all the nodes
                //Any time you read data from the Database, you receive the data as a DataSnapshot
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting product
                    String nameS = postSnapshot.child("name").getValue(String.class);
                    toastMessage(nameS);
                    Service service = new Service(nameS);
                    services.add(service);
                }

                //creating the adapter
                ServiceList sAdapter = new ServiceList(welcomescreen_admin.this, services);
                //attaching adapter to the listview
                listViewServices.setAdapter(sAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    private void updateDelete(final String productName) {
        //intent to other page
    }

    private void addService() {
        //getting values to save
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_addservice_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);
        final Button buttonCreate = (Button) dialogView.findViewById(R.id.buttonCreate);

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
                    Service service = new Service(name);
                    //saving the product
                    databaseServices.child(id).setValue(service);

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