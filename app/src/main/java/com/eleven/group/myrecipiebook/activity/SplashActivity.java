package com.eleven.group.myrecipiebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.eleven.group.myrecipiebook.R;



public class SplashActivity extends AppCompatActivity {

    /*
     * Created By: Jake Rushing
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Runnable endSplash = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(i);
            }
        };
        new Handler().postDelayed(endSplash, 3000L);
    }
}
