package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {
    Animation   appSplash, btmToTop;
    ImageView   appLogo;
    TextView    appSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       //Load animation
        appSplash   = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btmToTop    = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);

        // load element
        appLogo     = findViewById(R.id.app_logo);
        appSubtitle = findViewById(R.id.please_fill);

        //Run animation
        appLogo.startAnimation(appSplash);
        appSubtitle.startAnimation(btmToTop);

       //setting timer untuk 1 detik
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gogetstarted = new Intent(SplashAct.this, GetStartedAct.class);
                startActivity(gogetstarted);
                finish();
            }
        }, 2000); // 2000 millis =  2 detik
    }
}
