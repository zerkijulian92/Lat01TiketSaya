package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       //setting timer untuk 1 detik
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gogetstarted = new Intent(SplashAct.this, GetStartedAct.class);
                startActivity(gogetstarted);
                finish();
            }
        }, 1000); // 1000 millis =  1 detik
    }
}
