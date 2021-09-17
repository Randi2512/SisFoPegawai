package com.example.tugasbesar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2500);

                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity((intent));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        };
        timer.start();
    }
}
