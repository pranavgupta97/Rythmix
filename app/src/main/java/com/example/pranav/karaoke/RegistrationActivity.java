package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;


public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout fullName;
    private TextInputLayout username;
    private Button saveButton;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullName = (TextInputLayout) findViewById(R.id.fullNameEditText);
        username = (TextInputLayout) findViewById(R.id.userNameEditText);
        saveButton = (Button) findViewById(R.id.saveButton);



    }


    public void onStart() {
        super.onStart();
        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String name = fullName.getEditText().getText().toString();
                final String user = username.getEditText().getText().toString();

                if(user.length() < 4){
                    Toast.makeText(RegistrationActivity.this, "Username too short. Please create a username with at least 4 characters.", Toast.LENGTH_LONG).show();
                   username.getEditText().setText("");
                }
                else if((Pattern.matches("[0-9]+", user) == true) ){
                    Toast.makeText(RegistrationActivity.this, "Username must contain at least 1 letter.", Toast.LENGTH_LONG).show();
                    username.getEditText().setText("");
                }
                else if(name.length() < 2 || (Pattern.matches("([a-zA-Z]+.)+", name) == false)){
                    Toast.makeText(RegistrationActivity.this, "Please enter your full name.", Toast.LENGTH_LONG).show();
                }
                else {
                    Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(user);

                    usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                Toast.makeText(RegistrationActivity.this, "This username is taken. Choose a different username.", Toast.LENGTH_SHORT).show();
                            } else {
                                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("fullName", name);
                                userMap.put("username", user);
                                mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //move to next activity
                                            Intent userProfileIntent = new Intent(RegistrationActivity.this, userProfileActivity.class);
                                            startActivity(userProfileIntent);
                                            finish();
                                            Toast.makeText(RegistrationActivity.this, "Database Updated", Toast.LENGTH_LONG).show();
                                            //test
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
    }


}
