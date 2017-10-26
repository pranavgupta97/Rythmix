package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.R.attr.entries;

public class createSessionActivity extends AppCompatActivity {

    //declare Variables

    Spinner modeSpinner;
    ArrayAdapter<CharSequence>myAdapter;
    String selectedMode;
    FloatingActionButton fabNext;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;
    EditText sessionName;
    String username;
    ArrayList<String> usernames = new ArrayList<String>();
    //**************************?//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
        sessionName = (EditText) findViewById(R.id.sessionName);
        //Instantiate fab button
        fabNext = (FloatingActionButton)findViewById(R.id.sessionFab);
        //set up fab button on click listener
        View.OnClickListener nextListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMode.equals("Sober Mode")){

                    String sessName = sessionName.getText().toString();
                    String sessMode = selectedMode;

                    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Karaoke Session").child(currentUser.getUid());



                    HashMap<String, String> sessionMap = new HashMap<>();
                    sessionMap.put("session Name", sessName);
                    sessionMap.put("session Mode", sessMode);
                    mDatabase.setValue(sessionMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //loop through users
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    User user = snapshot.getValue(User.class);
                                                    if(user != null) {
                                                        usernames.add(user.username);
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                Intent createSessIntent = new Intent(createSessionActivity.this, soberModeActivity.class);
                                createSessIntent.putStringArrayListExtra("This a list of usernames", usernames);
                                startActivity(createSessIntent);
                                finish();

                                Toast.makeText(createSessionActivity.this, "Session Created", Toast.LENGTH_LONG).show();
                                //test
                            }
                        }
                    });


                }
                else if(selectedMode.equals("Drunk Mode")){
                    String sessName = sessionName.getText().toString();
                    String sessMode = selectedMode;
                    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Karaoke Session").child(currentUser.getUid());
                    HashMap<String, String> sessionMap = new HashMap<>();
                    sessionMap.put("session Name", sessName);
                    sessionMap.put("session Mode", sessMode);
                    mDatabase.setValue(sessionMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //move to next activity
                                //loop through users
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    User user = snapshot.getValue(User.class);
                                                    if(user != null) {

                                                        usernames.add(user.username);

                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                Intent createDSessIntent = new Intent(createSessionActivity.this, drunkModeActivity.class);
                                createDSessIntent.putStringArrayListExtra("This a list of usernames", usernames);
                                startActivity(createDSessIntent);
                                finish();

                                Toast.makeText(createSessionActivity.this, "Session Created", Toast.LENGTH_LONG).show();
                                //test
                            }
                        }
                    });
                }else{
                    Toast.makeText(createSessionActivity.this, "Please Select a Mode for your Session!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        fabNext.setOnClickListener(nextListener);


        //instantiate spinner and adapter
        modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
        myAdapter = ArrayAdapter.createFromResource(this, R.array.modes, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(myAdapter);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMode = (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


}
