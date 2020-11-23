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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseBranch extends AppCompatActivity {

    Button btnAddBranch;
    ListView listViewBranches;
    DatabaseReference databaseBranches;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String userid;
    private String branchid;
    private FirebaseUser user;

    final List<Branch> branches = new ArrayList<Branch>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_branch);

        listViewBranches = findViewById(R.id.listViewBranches);
        btnAddBranch = findViewById(R.id.addBranch);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        databaseBranches= FirebaseDatabase.getInstance().getReference("Branches");

        btnAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBranch();
            }
        });

        listViewBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Branch branch = branches.get(i);
                showDeleteDialog(branch.getId(),branch.getName());
                return true;
            }
        });
        listViewBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setBranch(position);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                branches.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Branch branch = postSnapshot.getValue(Branch.class);
                    branches.add(branch);
                }
                BranchList branchAdapter = new BranchList(ChooseBranch.this, branches);
                listViewBranches.setAdapter(branchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    private void showDeleteDialog(final String branchID, String branchName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update_branch, null);
        dialogBuilder.setView(dialogView);

        final TextView title = dialogView.findViewById(R.id.branchTitle);
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);

        title.setText(branchName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBranch(branchID);
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

    private void deleteBranch(String id){
        //getting the branch to be deleted from firebase
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Branches").child(id);
        //deleting the branch
        dR.removeValue();
        toastMessage("Branch deleted");
    }

    private void addBranch() {
        //getting values to save
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_branch, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText address = dialogView.findViewById(R.id.address);
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        final Button buttonCreate = dialogView.findViewById(R.id.buttonCreate);

        dialogBuilder.setTitle("New Branch");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String branchAddress = address.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the primary key for our product
                    String id = databaseBranches.push().getKey();

                    //creating a Branch object
                    Branch branch = new Branch(name,id,branchAddress);

                    //saving the Branch into firebase
                    databaseBranches.child(id).setValue(branch);

                    branches.add(branch);

                    //displaying a success toast
                    toastMessage("Branch added");
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

    private void setBranch(int index){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        userid = user.getUid();
        branchid = branches.get(index).getId();
        mFirebaseDatabase.getReference("Users").child(userid).child("branch_id").setValue(branchid);

        startActivity(new Intent(ChooseBranch.this, welcomescreen_branch.class));
    }
    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}