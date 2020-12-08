package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceApplication extends AppCompatActivity {

    DatabaseReference databaseRequirements;
    ListView listViewRequirements;
    ArrayList<String> requirementsID = new ArrayList();
    ServiceRequest request;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_application);

        listViewRequirements = findViewById(R.id.listViewRequirements);
        send = findViewById(R.id.sendRequest);

        final String serviceID = getIntent().getStringExtra("serviceID");
        final String branchID = getIntent().getStringExtra("branchID");
        request = new ServiceRequest(FirebaseAuth.getInstance().getCurrentUser().getUid(),branchID,serviceID);

        databaseRequirements = FirebaseDatabase.getInstance().getReference("Services").child(serviceID);

        listViewRequirements.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = requirementsID.get(i);
                if(selected == null){
                    toastMessage("broken");
                    return false;
                }
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ServiceApplication.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_update_user_info, null);
                dialogBuilder.setView(dialogView);

                final EditText edtInfo = dialogView.findViewById(R.id.info);
                final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
                final String requirementsName = requirementsID.get(i);
                dialogBuilder.setTitle("Update " + requirementsID.get(i));

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateUserInfo(edtInfo.getText().toString(),requirementsName);
                    }
                });
                final AlertDialog b = dialogBuilder.create();
                b.show();

                return true;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value listener event
        databaseRequirements.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requirementsID.clear();


                if(!dataSnapshot.exists()){
                    return;
                }

                for(DataSnapshot postSnapshot : dataSnapshot.child("reqInfo").getChildren()) {
                    String req = postSnapshot.getKey();
                    requirementsID.add(req);
                }

                RequirementList sAdapter = new RequirementList(ServiceApplication.this, requirementsID);
                listViewRequirements.setAdapter(sAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });

    }

    public void updateUserInfo(String information,String requirementName){
        if(information.equals("")){
            toastMessage("Your " + requirementName +" information has been cleared");
            request.removeInfo(requirementName);
        }
        else{
            toastMessage("Your " + requirementName +" is now " + information);
            request.updateInfo(requirementName,information);
        }

    }

    public void sendRequest(){

        final DatabaseReference requestDB = FirebaseDatabase.getInstance().getReference("Requests");

        String iD = requestDB.push().getKey();
        request.setRequestID(iD);
        requestDB.child(iD).setValue(request);

    }



    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

