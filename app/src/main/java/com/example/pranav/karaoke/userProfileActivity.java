package com.example.pranav.karaoke;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

public class userProfileActivity extends AppCompatActivity {

    private ImageButton editProfile;
    private Button logout;
    private FloatingActionButton newSession;
    private DatabaseReference userDatabase;
    private FirebaseUser currentUser;


    //make editText
    private TextView textName2;
    private TextView textUsername2;
    private TextView textPhoneNr;
   // private Button saveEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        editProfile = (ImageButton) findViewById(R.id.imageButton);
        logout = (Button) findViewById(R.id.button3);
        newSession = (FloatingActionButton) findViewById(R.id.fabNewSession);

        View.OnClickListener editProfileListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userProfileActivity.this, editProfileActivity.class));
            }
        };
        editProfile.setOnClickListener(editProfileListener);

        View.OnClickListener logoutListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent phoneVerificationActivityIntent = new Intent(userProfileActivity.this, phoneVerificationActivity.class);
                startActivity(phoneVerificationActivityIntent);
                finish();
            }
        };
        logout.setOnClickListener(logoutListener);

        View.OnClickListener fabListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userProfileActivity.this, createSessionActivity.class));
            }
        };
        newSession.setOnClickListener(fabListener);


        textName2 = (TextView) findViewById(R.id.signUpFullNameEditText);
        textUsername2 = (TextView) findViewById(R.id.signUpUserNameEditText);
        textPhoneNr = (TextView) findViewById(R.id.signUpEmailEditText);
        logout = (Button) findViewById(R.id.button3);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_uid = currentUser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

            userDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("fullName").getValue().toString();
                    String username = dataSnapshot.child("username").getValue().toString();
                    String phone = dataSnapshot.child("phoneNumber").getValue().toString();

                    textPhoneNr.setText(phone);
                    textName2.setText(name);
                    textUsername2.setText(username);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
    }



