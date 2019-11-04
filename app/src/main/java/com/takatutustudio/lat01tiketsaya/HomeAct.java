package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.florent37.shapeofview.shapes.CircleView;

public class HomeAct extends AppCompatActivity {
    LinearLayout btnTicketPisa;
    CircleView btnToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnTicketPisa   = findViewById(R.id.btn_ticket_pisa);
        btnToProfile    = findViewById(R.id.btn_to_profile);

        //goto Ticket Detail
        btnTicketPisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailAct.class);
                startActivity(gotopisaticket);
            }
        });

        //goto Edit Profile
        btnToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyprofile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotomyprofile);
            }
        });
    }
}
