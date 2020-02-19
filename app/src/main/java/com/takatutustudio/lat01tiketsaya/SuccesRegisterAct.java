package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccesRegisterAct extends AppCompatActivity {
    Animation appSplash, bottomToTop, topToBottom;
    ImageView iconSucces;
    TextView appTitle, appSubtitle;
    Button btnExplore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_register);

        //Load Element
        iconSucces      = findViewById(R.id.icon_succes);
        appTitle        = findViewById(R.id.xname_lengkap);
        appSubtitle     = findViewById(R.id.bio);
        btnExplore      = findViewById(R.id.btn_explore);

        //Load Element Animasi
        appSplash       = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomToTop     = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        topToBottom     = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);

        //Run Animasi
        iconSucces.startAnimation(appSplash);
        appTitle.startAnimation(topToBottom);
        appSubtitle.startAnimation(topToBottom);
        btnExplore.startAnimation(bottomToTop);

        //Goto Home
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccesRegisterAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
}
