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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


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
                String name = fullName.getEditText().getText().toString();
                String user = username.getEditText().getText().toString();
                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("fullName", name);
                userMap.put("username", user);
                mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //move to next activity
                            /*Intent userProfileIntent = new Intent(RegistrationActivity.this, userProfile.class);
                            startActivity(userProfileIntent);
                            finish();*/
                            Toast.makeText(RegistrationActivity.this, "Database Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
