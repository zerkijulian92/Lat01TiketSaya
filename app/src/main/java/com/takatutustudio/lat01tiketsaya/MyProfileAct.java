package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyProfileAct extends AppCompatActivity {
    LinearLayout ItemMyTicket;
    Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ItemMyTicket    = findViewById(R.id.item_my_ticket);
        btnEditProfile  = findViewById(R.id.btn_edit_profile);

        //Goto My Ticket Detail
        ItemMyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyticket = new Intent(MyProfileAct.this, MyTicketDetailAct.class);
                startActivity(gotomyticket);
            }
        });

        //Goto Edit Profile
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoeditprofile = new Intent(MyProfileAct.this, EditProfileAct.class);
                startActivity(gotoeditprofile);
            }
        });
    }
}
