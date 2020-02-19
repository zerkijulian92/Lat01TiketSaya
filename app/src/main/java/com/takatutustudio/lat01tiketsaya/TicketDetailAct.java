package com.takatutustudio.lat01tiketsaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailAct extends AppCompatActivity {
    ImageView headerTicketDetail;
    TextView titleTicket, locationTicket, photoSpotTicket, wifiTicket, festivalTicket, shortDescTicket;
    Button btnBuyTicket;
    LinearLayout btnBack;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        //Deklarasi ID
        headerTicketDetail  = findViewById(R.id.header_ticket_detail);
        titleTicket         = findViewById(R.id.xname_lengkap);
        locationTicket      = findViewById(R.id.bio);
        photoSpotTicket     = findViewById(R.id.photo_spot_ticket);
        wifiTicket          = findViewById(R.id.wifi_ticket);
        festivalTicket      = findViewById(R.id.xnama_wisata);
        shortDescTicket     = findViewById(R.id.short_desc_ticket);
        btnBuyTicket        = findViewById(R.id.btn_buy_ticket);
        btnBack             = findViewById(R.id.btn_back);

        //Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");
//        Toast.makeText(getApplicationContext(), "Jenis Tiket: " + jenis_tiket_baru, Toast.LENGTH_SHORT).show();

        //Mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru
                titleTicket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                locationTicket.setText(dataSnapshot.child("lokasi").getValue().toString());
                photoSpotTicket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifiTicket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festivalTicket.setText(dataSnapshot.child("is_festival").getValue().toString());
                shortDescTicket.setText(dataSnapshot.child("short_desc").getValue().toString());
                Picasso.with(TicketDetailAct.this)
                        .load(dataSnapshot.child("url_thumbnail")
                        .getValue().toString()).centerCrop().fit()
                        .into(headerTicketDetail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Goto Checkout
        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocheckout = new Intent(TicketDetailAct.this, TicketCheckoutAct.class);
                // meletekan data kepada intent
                gotocheckout.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotocheckout);
            }
        });

        //btn Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
