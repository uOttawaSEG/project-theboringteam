package com.uOttawaSEG.servicenovigrad;

<<<<<<< HEAD:app/src/main/java/com/uOttawaSEG/servicenovigrad/User.java
class User {
    private String _id;
    private String _name;
    private String _email;
    private String _type;

    public User(){

    }

    public User(String id, String name, String email, String type) {
        _id = id;
        _name = name;
        _email = email;
        _type = type;
=======
import android.os.Parcelable;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class Users {
    private String id;
    private String name;
    private String email;
    private String type;

    public Users() {
        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser= mAuth.getCurrentUser();

        //I dont know how to access the database, or where!!! someone help!!! AHHHHH!!
    }
    public Users(String id, String name, String email, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
>>>>>>> d1402290294b6eb7c1f8fcd89fd5d43646fe2c6b:app/src/main/java/com/uOttawaSEG/servicenovigrad/Users.java
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setType(String type) {this.type = type; }
    public String getType() {
        return type;
    }


}
