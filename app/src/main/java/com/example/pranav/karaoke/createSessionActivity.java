package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class createSessionActivity extends AppCompatActivity {

    //declare Variables

    Spinner modeSpinner;
    ArrayAdapter<CharSequence>myAdapter;
    String selectedMode;
    FloatingActionButton fabNext;

    //**************************?//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        //Instantiate fab button
        fabNext = (FloatingActionButton)findViewById(R.id.sessionFab);
        //set up fab button on click listener
        View.OnClickListener nextListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMode.equals("Sober Mode")){

                    startActivity(new Intent(createSessionActivity.this, soberModeActivity.class));
                    finish();

                }else if(selectedMode.equals("Drunk Mode")){

                    startActivity(new Intent(createSessionActivity.this, drunkModeActivity.class));
                    finish();
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
