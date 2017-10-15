package com.example.pranav.karaoke;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;



public class karaokeActivity extends AppCompatActivity {
    //Declaring the View variables

    private Button signUpButton;
    private Button logInButton;
    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karaoke);

        //Assigning the Views from the Layout file to their corresponding Views

        signUpButton = (Button) findViewById(R.id.signUpButton);
        logInButton = (Button) findViewById(R.id.logInButton);

        //Adding functionality to the signUp Button
        View.OnClickListener signUpListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(karaokeActivity.this, signUpActivity.class));
            }
        };
        signUpButton.setOnClickListener(signUpListener);

        //Adding functionality to the logIn Button
        View.OnClickListener logInListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(karaokeActivity.this, logInActivity.class));
            }
        };
        logInButton.setOnClickListener(logInListener);
    }
}