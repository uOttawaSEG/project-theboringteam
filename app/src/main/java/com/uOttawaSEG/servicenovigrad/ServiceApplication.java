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
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class ServiceApplication extends AppCompatActivity{
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
