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

public class SuccesBuyTicketAct extends AppCompatActivity {
    Animation appSplash, bottomToTop, topToBottom;
    ImageView iconSucces;
    TextView appTitle, appSubtitle;
    Button btnEditProfile, btnMyDashboard, btnViewTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy_ticket);

        //Registrasi element
        iconSucces      = findViewById(R.id.icon_succes);
        appTitle        = findViewById(R.id.xname_lengkap);
        appSubtitle     = findViewById(R.id.bio);
        btnEditProfile  = findViewById(R.id.btn_view_ticket);
        btnMyDashboard  = findViewById(R.id.btn_my_dashboard);
        btnViewTicket   = findViewById(R.id.btn_view_ticket);

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

        // Goto MyDasboard
        btnMyDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomydashboard = new Intent(SuccesBuyTicketAct.this, HomeAct.class);
                startActivity(gotomydashboard);
            }
        });

        // Goto ViewTickets
        btnViewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoviewticket = new Intent(SuccesBuyTicketAct.this, MyProfileAct.class);
                startActivity(gotoviewticket);
            }
        });
    }
}
