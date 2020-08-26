package com.example.collegeproject.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.collegeproject.R;
import com.example.collegeproject.utility.SosManagement;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean isLogCheck;

                isLogCheck = new SosManagement(SplashActivity.this).getLogin();

                if (isLogCheck) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, 1000);
    }
}