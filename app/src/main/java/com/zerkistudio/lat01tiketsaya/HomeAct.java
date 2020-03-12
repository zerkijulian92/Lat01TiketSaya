package com.zerkistudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {
    LinearLayout btnTicketPisa, btnTicketTorri, btnTicketPagoda, btnTicketCandi,btnTicketSphinx, btnTicketMonas;
    CircleView btnToProfile;
    ImageView photoHomeUser;
    TextView userName, bio, userBalance;

    DatabaseReference reference;

    String USERNAME_KEY     = "usernamekey";
    String username_key     = "";
    String username_key_new = "";

    // Function "Tekan Sekali Lagi Untuk Keluar"
    private static final int TIME_INTERVAL = 2000;
    private long mBackPresed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //memanggil userNameLocal
        getUsernameLocal();

        photoHomeUser   = findViewById(R.id.photo_home_user);
        userName        = findViewById(R.id.username);
        bio             = findViewById(R.id.please_fill);
        userBalance     = findViewById(R.id.user_balance);
        btnTicketPisa   = findViewById(R.id.btn_ticket_pisa);
        btnTicketTorri  = findViewById(R.id.btn_ticket_torri);
        btnTicketPagoda = findViewById(R.id.btn_ticket_pagoda);
        btnTicketCandi  = findViewById(R.id.btn_ticket_candi);
        btnTicketSphinx = findViewById(R.id.btn_ticket_sphinx);
        btnTicketMonas  = findViewById(R.id.btn_ticket_monas);
        btnToProfile    = findViewById(R.id.btn_to_profile);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                userBalance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this)
                        .load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit()
                        .into(photoHomeUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //goto Ticket Detail ( Ticket Pisa )
        btnTicketPisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailAct.class);
                //meletakan data ke intent
                gotopisaticket.putExtra("jenis_tiket", "Pisa");
                startActivity(gotopisaticket);
            }
        });

        //goto Ticket Detail ( Ticket Torri )
        btnTicketTorri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gototorriticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gototorriticket.putExtra("jenis_tiket", "Torri");
                startActivity(gototorriticket);
            }
        });

        //goto Ticket Detail ( Ticket Pagoda )
        btnTicketPagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotopagodaticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotopagodaticket.putExtra("jenis_tiket", "Pagoda");
                startActivity(gotopagodaticket);
            }
        });

        //goto Ticket Detail ( Ticket Candi )
        btnTicketCandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotocanditicket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotocanditicket.putExtra("jenis_tiket", "Candi");
                startActivity(gotocanditicket);
            }
        });

        //goto Ticket Detail ( Ticket Sphinx )
        btnTicketSphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotosphinxticket = new Intent(HomeAct.this, TicketDetailAct.class);
                gotosphinxticket.putExtra("jenis_tiket", "Sphinx");
                startActivity(gotosphinxticket);
            }
        });

        //goto Ticket Detail ( Ticket Monas )
        btnTicketMonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotomonasticket =  new Intent(HomeAct.this, TicketDetailAct.class);
                gotomonasticket.putExtra("jenis_tiket", "Monas");
                startActivity(gotomonasticket);
            }
        });

        //goto Edit Profile
        btnToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotomyprofile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotomyprofile);
            }
        });
    }

    // Function "Tekan Sekali Lagi Untuk Keluar"
    @Override
    public void onBackPressed() {
        if (mBackPresed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            // Menghapus isi / nilai / value dari username local
            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username_key, null);
            editor.apply();

            // Berpindah Activity
            Intent gotosignin = new Intent(HomeAct.this, SigInAct.class);
            startActivity(gotosignin);
            finish();
        }
        else {
            Toast.makeText(getBaseContext(), "Tekan Sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            mBackPresed = System.currentTimeMillis();
        }
    }
    //---

    //getUsernameLocal
    public void  getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_MULTI_PROCESS);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
