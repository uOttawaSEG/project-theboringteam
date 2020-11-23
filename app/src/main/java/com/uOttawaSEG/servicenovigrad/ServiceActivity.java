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

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceActivity extends AppCompatActivity {

    Button btnBack, btnAdd, btnDelete;
    ListView requirementsListview;
    TextView title;

    DatabaseReference databaseService;

    ArrayList<String> requirementsID = new ArrayList();
    HashMap<String, String> reqInfo= new HashMap<>();
    String nextReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        requirementsListview = findViewById(R.id.listViewServices);
        btnAdd = findViewById(R.id.addRequirement);
        btnBack = findViewById(R.id.backServices);
        btnDelete = findViewById(R.id.deleteService);
        title = findViewById(R.id.serviceTitle);

        databaseService= FirebaseDatabase.getInstance().getReference("Services").child(getIntent().getStringExtra("SERVICE"));



        requirementsListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = requirementsID.get(i);
                if(selected == null){
                    toastMessage("broken");
                    return false;
                }
                delete(selected, i);
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequirement();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value listener event
        databaseService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requirementsID.clear();
                reqInfo.clear();

                if(!dataSnapshot.exists()){
                    return;
                }

                nextReq = dataSnapshot.child("nextReq").getValue().toString();

                for(DataSnapshot postSnapshot : dataSnapshot.child("reqInfo").getChildren()) {

                    String req = postSnapshot.getKey();
                    reqInfo.put(req,postSnapshot.getValue().toString());
                    requirementsID.add(req);

                }



                RequirementList sAdapter = new RequirementList(ServiceActivity.this, requirementsID,reqInfo);

                requirementsListview.setAdapter(sAdapter);

                title.setText(dataSnapshot.child("name").getValue().toString());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });

    }

    private void delete(final String requirement, final int index) {

        databaseService.child("reqInfo").child(requirement).removeValue();

        Toast.makeText(getApplicationContext(),"Requirement Updated", Toast.LENGTH_LONG).show();

    }

    private void addRequirement(){


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_addservice_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        final Button buttonCreate = dialogView.findViewById(R.id.buttonCreate);

        editTextName.setHint("Requirement title");

        dialogBuilder.setTitle("New Requirement");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {

                    requirementsID.add(nextReq);
                    reqInfo.put(nextReq,name);

                    databaseService.child("reqInfo").child(nextReq).setValue(name);

                    databaseService.child("nextReq").setValue((new Integer(Integer.valueOf(nextReq).intValue()+1)).toString());

                    //displaying a success toast
                    toastMessage("Service added");
                    b.dismiss();
                }else{
                    //if the value is not given displaying a toast
                    toastMessage("Requirement "+name+" added");
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

    private void deleteService(){

        databaseService.removeValue();

        DatabaseReference cleanerDB = FirebaseDatabase.getInstance().getReference("Branches");

        cleanerDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DatabaseReference branchRef = FirebaseDatabase.getInstance().getReference("Branches");

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String branchID = postSnapshot.getKey();
                    branchRef.child(branchID).child("services").child(getIntent().getStringExtra("SERVICE")).removeValue();

                }



                RequirementList sAdapter = new RequirementList(ServiceActivity.this, requirementsID,reqInfo);

                requirementsListview.setAdapter(sAdapter);

                title.setText(dataSnapshot.child("name").getValue().toString());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });

        finish();
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
