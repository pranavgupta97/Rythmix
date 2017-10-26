package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class themeActivity extends AppCompatActivity {


    //declare Variables

    Spinner themeSpinner;
    ArrayAdapter<CharSequence> myAdapter;
    public String selectedTheme;
    FloatingActionButton fabNext;
    //**************************?//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //Instantiate fab button
        fabNext = (FloatingActionButton)findViewById(R.id.sessionFab);
        //set up fab button on click listener
        View.OnClickListener nextListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(themeActivity.this, soberModeActivity.class));
            }
        };
        fabNext.setOnClickListener(nextListener);


        //instantiate spinner and adapter
        themeSpinner = (Spinner) findViewById(R.id.themeSpinner);
        myAdapter = ArrayAdapter.createFromResource(this, R.array.themes, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(myAdapter);
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTheme = (String)parent.getItemAtPosition(position);
                //add theme to database

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    }

