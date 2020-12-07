package com.uOttawaSEG.servicenovigrad;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class welcomescreen_customer extends AppCompatActivity {

    public TextView welcomeName, welcomeType;
    public EditText searchFieldHours, searchFieldAddress;
    public Button searchButton, searchFieldService;
    public ListView listViewBranches;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference usersDatabase;
    private DatabaseReference servicesDB;
    private DatabaseReference branchesDatabase;
    private String userid;
    private String serviceQuery;

    final List<Service> services = new ArrayList<>();
    final List<Branch> searchedBranches = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen_customer);

        welcomeName = findViewById(R.id.welcomeMessageName);
        welcomeType = findViewById(R.id.info);
        searchFieldHours = findViewById(R.id.search_field_hours);
        searchFieldAddress = findViewById(R.id.search_field_address);
        searchFieldService = findViewById(R.id.search_field_service);
        searchButton = findViewById(R.id.search_button);
        listViewBranches = findViewById(R.id.listViewBranches);
        serviceQuery = "";

        // ### FIREBASE STUFF ###
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userid = user.getUid();
        usersDatabase = mFirebaseDatabase.getReference("Users").child(userid);
        branchesDatabase = mFirebaseDatabase.getReference("Branches");

        searchFieldService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceQuery = "";
                displayServicesAvailable();
                toastMessage("Search a branch by service");
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchAddress = searchFieldAddress.getText().toString();
                String searchHours = searchFieldHours.getText().toString();
                String searchServiceId = serviceQuery;
                firebaseServiceSearch(searchHours, searchAddress, searchServiceId);
            }
        });

        usersDatabase.addValueEventListener(new ValueEventListener() { //gets called when there is a change in the database OR when the activity starts
            @Override
            //a snapshot of the database is like a snapshot of the whole database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //since we have the whole database, we need to iterate through all the users inside the database
                User mUser = new User();
                //we first go to the child of the user id and
                mUser.setName(snapshot.child("name").getValue(String.class)); //set the name
                mUser.setEmail(snapshot.child("email").getValue(String.class)); //set the email
                mUser.setType(snapshot.child("type").getValue(String.class)); //set the type

                welcomeName.setText("Welcome " + mUser.getName()+"!");
                welcomeType.setText("You are signed in as " + mUser.getType() + ".");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void firebaseServiceSearch(final String searchHours, final String searchAddress, final String searchServiceId) {

        branchesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchedBranches.clear();

                boolean validHours, validAddress, validService;

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    validAddress = searchAddress.isEmpty();
                    validHours = searchHours.isEmpty();
                    validService = searchServiceId.equals("");

                    if(!validAddress){
                        for(String subString : searchAddress.split("\\s+")){
                            if(postSnapshot.child("address").getValue(String.class).contains(subString)){
                                validAddress = true;
                                break;
                            }
                        }
                    }


                    if(!(validHours && validService)){
                        for(DataSnapshot postpostSnapshot : postSnapshot.child("workingHours").getChildren()){

                            if(!postpostSnapshot.getValue(String.class).equals("closed") && !validHours){
                                String startEnd = postpostSnapshot.getValue(String.class);

                                String startTime = startEnd.trim().split("-")[0];

                                int startHour = Integer.parseInt(startTime.trim().split(":")[0],10);
                                //int startHour = Integer.parseInt(startEnd.substring(0,1),10);
                                int startMinute = Integer.parseInt(startTime.trim().split(":")[1],10);
                                //int startMinute = Integer.parseInt(startEnd.substring(3,4),10);

                                String endTime = startEnd.trim().split("-")[1];

                                int endHour = Integer.parseInt(endTime.trim().split(":")[0],10);
                                //int endHour = Integer.parseInt(startEnd.substring(8,9),10);
                                int endMinute = Integer.parseInt(endTime.trim().split(":")[1],10);
                                //int endMinute = Integer.parseInt(startEnd.substring(11,12),10);

                                int searchHour = Integer.parseInt(searchHours.trim().split(":")[0],10);
                                int searchMinute = Integer.parseInt(searchHours.trim().split(":")[1],10);

                                boolean startIsValid = (searchHour > startHour || (searchHour == startHour &&  searchMinute >= startMinute));
                                boolean endIsValid = (searchHour < endHour || (searchHour == endHour &&  searchMinute <= endMinute));

                                if(startIsValid && endIsValid){
                                    validHours = true;
                                    break;
                                }
                            }
                        }
                    }


                    if(!validService){
                        for(DataSnapshot postpostSnapshot : postSnapshot.child("services").getChildren()){

                            if(postpostSnapshot.getKey().equals(searchServiceId)){
                                validService = true;
                                break;
                            }
                        }
                    }


                    if(validAddress && validHours && validService){
                        String name = postSnapshot.child("name").getValue(String.class);
                        String address = postSnapshot.child("address").getValue(String.class);
                        String id = postSnapshot.getKey();
                        Branch branch = new Branch(name, id, address);

                        searchedBranches.add(branch);
                    }
                }
                BranchList branchAdapter = new BranchList(welcomescreen_customer.this, searchedBranches);
                listViewBranches.setAdapter(branchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    public void displayServicesAvailable() {
        // setup the alert builder
        //final String branchid = getIntent().getStringExtra("branchID");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a service");
        servicesDB = FirebaseDatabase.getInstance().getReference("Services");
        servicesDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                services.clear();
                services.add(new Service("NONE",""));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String nameS = postSnapshot.child("name").getValue(String.class);
                    String id = postSnapshot.child("id").getValue(String.class);
                    Service service = new Service(nameS,id);
                    services.add(service);
                }
                final ServiceList serviceAdapter = new ServiceList(welcomescreen_customer.this, services);
                builder.setAdapter(serviceAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Service serviceName = serviceAdapter.getItem(which);
                        serviceQuery = serviceName.getId();
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
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
    


}


