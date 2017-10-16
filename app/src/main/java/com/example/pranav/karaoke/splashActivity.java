package com.example.pranav.karaoke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Atharva on 9/9/2017.
 */

public class splashActivity extends Activity {
    ImageView rotateK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        rotateK = (ImageView) findViewById(R.id.rotate_K);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.k_rotate_animation);
        rotateK.startAnimation(startRotateAnimation);

        View.OnClickListener skip = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(splashActivity.this, RegistrationActivity.class));
            }
        };
        rotateK.setOnClickListener(skip);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);

                }catch(InterruptedException e){
                    e.printStackTrace();

                }finally{
                    Intent splash = new Intent(splashActivity.this,RegistrationActivity.class);
                    //Intent splash = new Intent(splashActivity.this,RegistrationActivity.class);
                    startActivity(splash);

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