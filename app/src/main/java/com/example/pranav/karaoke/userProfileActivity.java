package com.example.pranav.karaoke;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class userProfileActivity extends AppCompatActivity {

    private ImageButton editProfile;
    private Button logout;
    private FloatingActionButton newSession;

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
                startActivity(new Intent(userProfileActivity.this, splashActivity.class));
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
    }
}
