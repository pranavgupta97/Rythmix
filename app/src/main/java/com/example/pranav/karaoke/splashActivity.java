package com.example.pranav.karaoke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Atharva on 9/9/2017.
 */

public class splashActivity extends Activity {
    ImageView rotateK;
    boolean clicked = false;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        rotateK = (ImageView) findViewById(R.id.rotate_K);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.k_rotate_animation);
        rotateK.startAnimation(startRotateAnimation);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        View.OnClickListener skip = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
                if (firebaseUser != null) {
                    Intent userProfileIntent = new Intent(splashActivity.this, userProfileActivity.class);
                    startActivity(userProfileIntent);
                    finish();
                }
                else {
                    Intent splash = new Intent(splashActivity.this, phoneVerificationActivity.class);
                    startActivity(splash);
                    finish();
                }
            }
        };
        rotateK.setOnClickListener(skip);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(4000);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    if (!clicked) {
                        if (firebaseUser != null) {
                            Intent userProfileIntent = new Intent(splashActivity.this, userProfileActivity.class);
                            startActivity(userProfileIntent);
                            finish();
                        }
                        else {
                            Intent splash = new Intent(splashActivity.this, phoneVerificationActivity.class);
                            startActivity(splash);
                            finish();
                        }
                    }
                }
            }
        };
        timerThread.start();
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}