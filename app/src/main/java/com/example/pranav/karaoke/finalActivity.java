package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class finalActivity extends AppCompatActivity {
    //fab variables
    FloatingActionButton cNFab;
    FloatingActionButton crNFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        //instantiating variables
        cNFab = (FloatingActionButton)findViewById(R.id.callNightFab);
        crNFab = (FloatingActionButton)findViewById(R.id.createNewFab);

        //on click listeners for activities
        cNFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(finalActivity.this, userProfileActivity.class ));
            }
        });

        crNFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(finalActivity.this, createSessionActivity.class));
            }
        });
    }
}
