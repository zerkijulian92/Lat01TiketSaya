package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MyProfileAct extends AppCompatActivity {
    LinearLayout ItemMyTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ItemMyTicket    = findViewById(R.id.item_my_ticket);

        //Goto My Profile
        ItemMyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyticket = new Intent(MyProfileAct.this, MyTicketDetailAct.class);
                startActivity(gotomyticket);
            }
        });
    }
}
