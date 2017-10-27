package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class  editProfileActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    private FirebaseUser currentUser;


    //make editText
    private EditText textName;
    private EditText textUsername;
    private FloatingActionButton saveEditProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        textName = (EditText) findViewById(R.id.signUpFullNameEditText);
        textUsername = (EditText) findViewById(R.id.signUpUserNameEditText);
        saveEditProfile = (FloatingActionButton) findViewById(R.id.saveButton);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

       String current_uid = currentUser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullName").getValue().toString();
                String username = dataSnapshot.child("username").getValue().toString();

                textName.setText(name);
                textUsername.setText(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void onStart(){
        super.onStart();
        saveEditProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String name = textName.getText().toString();
                final String user = textUsername.getText().toString();

                if (user.length() < 4) {
                    Toast.makeText(editProfileActivity.this, "Username too short. Please create a username with at least 4 characters.", Toast.LENGTH_LONG).show();
                    textUsername.setText("");
                } else if ((Pattern.matches("[0-9]+", user) == true)) {
                    Toast.makeText(editProfileActivity.this, "Username must contain at least 1 letter.", Toast.LENGTH_LONG).show();
                    textUsername.setText("");
                } else if (name.length() < 2 || (Pattern.matches("([a-zA-Z]+.)+", name) == false)) {
                    Toast.makeText(editProfileActivity.this, "Please enter your full name.", Toast.LENGTH_LONG).show();
                } else {
                    Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(user);

                    usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                Toast.makeText(editProfileActivity.this, "This username is taken. Choose a different username.", Toast.LENGTH_SHORT).show();
                            } else {
                                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("fullName", name);
                                userMap.put("username", user);

                                /*Added this because it was creating a null pointer when you save from edit profile
                                 *Once you hit save in edit profile
                                 *It updates the user data and deletes the phone number
                                 *So this change is valid fix for now
                                 */
                                userMap.put("phoneNumber", currentUser.getPhoneNumber());
                                userDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //move to next activity
                                            Intent userProfileIntent = new Intent(editProfileActivity.this, userProfileActivity.class);
                                            startActivity(userProfileIntent);
                                            finish();
                                            Toast.makeText(editProfileActivity.this, "Database Updated", Toast.LENGTH_LONG).show();
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
