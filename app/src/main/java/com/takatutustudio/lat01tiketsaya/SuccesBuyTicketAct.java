package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccesBuyTicketAct extends AppCompatActivity {
    Animation appSplash, bottomToTop, topToBottom;
    ImageView iconSucces;
    TextView appTitle, appSubtitle;
    Button btnEditProfile, btnMyDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy_ticket);

        //Registrasi element
        iconSucces      = findViewById(R.id.icon_succes);
        appTitle        = findViewById(R.id.title_ticket);
        appSubtitle     = findViewById(R.id.location_ticket);
        btnEditProfile  = findViewById(R.id.btn_edit_profile);
        btnMyDashboard  = findViewById(R.id.btn_my_dashboard);

        //Regitrasi Animasi
        appSplash       = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomToTop     = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        topToBottom     = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);

        //Run Animasi
        iconSucces.startAnimation(appSplash);
        appTitle.startAnimation(topToBottom);
        appSubtitle.startAnimation(topToBottom);
        btnEditProfile.startAnimation(bottomToTop);
        btnMyDashboard.startAnimation(bottomToTop);
    }
}
