package com.uOttawaSEG.servicenovigrad;

import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.annotation.Nullable;
=======
>>>>>>> d1402290294b6eb7c1f8fcd89fd5d43646fe2c6b
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class WelcomeScreen extends AppCompatActivity {

    public TextView welcomeName, welcomeType;
    public Button logOut;
<<<<<<< HEAD
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference usersDatabase;
    private String userid;
=======
    public String welcome, role;
    private Users currentUser;
>>>>>>> d1402290294b6eb7c1f8fcd89fd5d43646fe2c6b


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeName = findViewById(R.id.welcomeMessageName);
        welcomeType = findViewById(R.id.welcomeMessage);
        logOut = findViewById(R.id.btnLogout);

<<<<<<< HEAD
        // ### FIREBASE STUFF ###
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        usersDatabase = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userid = user.getUid();

//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null){
//                    //user is signed in
//
//                }else{
//                    //user is signed out
//                }
//            }
//        };

        usersDatabase.addValueEventListener(new ValueEventListener() { //gets called when there is a change in the database OR when the activity starts
            @Override
            //a snapshot of the database is like a snapshot of the whole database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //since we have the whole database, we need to iterate through all the users inside the database
                for(DataSnapshot ds : snapshot.getChildren()){
                    User mUser = new User();
                    //we first go to the child of the user id and
                    mUser.setName(ds.child(userid).getValue(User.class).getName()); //set the name
                    mUser.setEmail(ds.child(userid).getValue(User.class).getEmail()); //set the email
                    mUser.setType(ds.child(userid).getValue(User.class).getType()); //set the type

                    welcomeName = mUser.getName();
                    welcomeType = mUser.getType();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
=======
        currentUser = new Users();

        if(currentUser.getEmail() != null && currentUser.getName() != null && currentUser.getId() != null && currentUser.getType() != null){
            welcomeName.setText("Welcome " + currentUser.getName() + "!");
            welcomeType.setText("You are signed in as " + currentUser.getType() + ".");
        }
        else{
            welcome = "Error loading profile";
            role = "Please log in again.";

            if(currentUser.getEmail() == null){
                welcome = welcome+"s";
            }
            if(currentUser.getName() == null){
                welcome = welcome+"h";
            }
            if(currentUser.getId() == null){
                welcome = welcome+"i";
            }
            if(currentUser.getType() == null){
                welcome = welcome+"t";
            }
            welcomeName.setText(welcome);
            welcomeType.setText(role);
        }


>>>>>>> d1402290294b6eb7c1f8fcd89fd5d43646fe2c6b

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
            }
        });
<<<<<<< HEAD
=======
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
>>>>>>> d1402290294b6eb7c1f8fcd89fd5d43646fe2c6b
    }
}
