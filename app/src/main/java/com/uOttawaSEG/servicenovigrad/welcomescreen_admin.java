package com.uOttawaSEG.servicenovigrad;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class welcomescreen_admin extends AppCompatActivity {

    Button buttonAddService;
    ListView listViewServices;
    DatabaseReference databaseServices;

    final List<Service> services = new ArrayList<Service>();
    final List<String> servicesID = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen_administrator);

        listViewServices = findViewById(R.id.listViewServices);
        buttonAddService = findViewById(R.id.addService);

        databaseServices= FirebaseDatabase.getInstance().getReference("Services");

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
                String selectedID = servicesID.get(i);
                updateDelete(selected, selectedID, i);
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
                    HashMap<String,String> reqInfo = new HashMap<>();
                    Integer nextReq = postSnapshot.child("nextReq").getValue(Integer.class);

                    for(DataSnapshot postpostSnapshot : postSnapshot.child("reqInfo").getChildren()){
                        reqInfo.put(postpostSnapshot.getKey(),postpostSnapshot.getValue(String.class));

                    }

                    String id = postSnapshot.getKey();


                    Service service = new Service(nameS, reqInfo,nextReq);
                    services.add(service);
                    servicesID.add(id);

                }

                ServiceList sAdapter = new ServiceList(welcomescreen_admin.this, services);

                listViewServices.setAdapter(sAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }



    private void updateDelete(final Service service, final String id, final int index) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_updatedelete_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView title = dialogView.findViewById(R.id.serviceTitle);
        final EditText editTextName = dialogView.findViewById(R.id.addReq);
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);
        final Button addReq = dialogView.findViewById(R.id.buttonCreate);
        final ListView displayReq = dialogView.findViewById(R.id.listViewReq);



        title.setText("Update or Delete objects from "+service.getName());

        RequirementList rAdapter = new RequirementList(this, service);

        displayReq.setAdapter(rAdapter);

        dialogBuilder.setTitle("Update/Delete Service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        addReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String req = editTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(req)) {

                    service.addInfo(req);
                    //saving the product
                    databaseServices.child(id).setValue(service);

                    services.set(index, service);

                    //displaying a success toast
                    Toast.makeText(getApplicationContext(), "Service requirement '"+ service.getName() +"' Updated",Toast.LENGTH_LONG).show();
                    b.dismiss();
                }else{
                    //if the value is not given displaying a toast
                    toastMessage("Please enter a name");
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(databaseServices.child(id).removeValue().isSuccessful()) {
                        services.remove(index);
                        servicesID.remove(index);
                        b.dismiss();
                        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"This item does not exsist", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        displayReq.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                Integer[] deleteKey = (Integer[]) service.getReqInfo().keySet().toArray();

                databaseServices.child(id).child(service.getRequiremnt(deleteKey[i].toString())).removeValue();

                databaseServices.child(id).child("infoReq");
                service.removeReqiuirement(i);
                services.set(index,service);
                Toast.makeText(getApplicationContext(),"Requirement Updated", Toast.LENGTH_LONG).show();
                return true;
            }
        });


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
                    Service service = new Service(name);
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