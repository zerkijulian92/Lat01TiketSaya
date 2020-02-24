package com.zerkistudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedAct extends AppCompatActivity {
    Animation btmToTop, topToBottom;
    Button btnSignIn, btnNewAccount;
    TextView introApp;
    ImageView emblemWhite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        emblemWhite     = findViewById(R.id.ic_emblem_white);
        introApp        = findViewById(R.id.intro_app);
        btnSignIn       = findViewById(R.id.btn_sign_in);
        btnNewAccount   = findViewById(R.id.btn_new_account);

        //load animation
        btmToTop        = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        topToBottom     = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);

        //Run Animation
        emblemWhite.startAnimation(topToBottom);
        introApp.startAnimation(topToBottom);

        btnSignIn.startAnimation(btmToTop);
        btnNewAccount.startAnimation(btmToTop);

        //Goto Sign In
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosign = new Intent(GetStartedAct.this, SigInAct.class);
                startActivity(gotosign);
            }
        });

        //Goto RegisterOne
        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
    }
}
