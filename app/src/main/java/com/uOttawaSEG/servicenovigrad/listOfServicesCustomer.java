package com.uOttawaSEG.servicenovigrad;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class listOfServicesCustomer extends AppCompatActivity {

    ListView listOfCustomerServices;
    TextView textView;
    Button rateBranchButton;
    private DatabaseReference servicesDB;
    final List<Service> listServicesBranch = new ArrayList<Service>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_services_customer);

        textView = findViewById(R.id.textView);
        rateBranchButton = findViewById(R.id.rateBranchButton);
        listOfCustomerServices = findViewById(R.id.listOfCustomerServices);
        listOfCustomerServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(listOfServicesCustomer.this,ServiceApplication.class);
                intent.putExtra("serviceID", listServicesBranch.get(position).getId());
                startActivity(intent);
            }
        });

        rateBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "Rate this branch + firebase";
                final float[] userRating = new float[1];
                final Dialog rankDialog = new Dialog(listOfServicesCustomer.this);
                rankDialog.setContentView(R.layout.dialog_ranking);
                rankDialog.setCancelable(true);
                final RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.ratingBar);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        userRating[0] = rating;
                    }
                });

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_branch);
                text.setText(name);

                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rankDialog.dismiss();
                        toastMessage("The number of stars is " + userRating[0]);
                    }
                });
                rankDialog.show();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        String branchID = getIntent().getStringExtra("BranchID");
        servicesDB = FirebaseDatabase.getInstance().getReference("Branches").child(branchID).child("services");
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
                ServiceList branchAdapter = new ServiceList(listOfServicesCustomer.this, listServicesBranch);
                listOfCustomerServices.setAdapter(branchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });

    }
    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
