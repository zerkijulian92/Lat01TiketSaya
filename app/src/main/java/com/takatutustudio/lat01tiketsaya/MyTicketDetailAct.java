package com.takatutustudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTicketDetailAct extends AppCompatActivity {

    DatabaseReference reference;
    TextView xNamaWisata, xLokasi, xTimeWisata, xDateWisata, xKetentuan;
    LinearLayout btnBack;
    Button btnRefund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        // Deklarasikan ID
        xNamaWisata     = findViewById(R.id.xnama_wisata);
        xLokasi         = findViewById(R.id.xlokasi);
        xTimeWisata     = findViewById(R.id.xtime_wisata);
        xDateWisata     = findViewById(R.id.xdate_wisata);
        xKetentuan      = findViewById(R.id.xketentuan);
        btnBack         = findViewById(R.id.btn_back);
        btnRefund       = findViewById(R.id.btn_refund);

        // Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");

        // Mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xNamaWisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                xLokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                xTimeWisata.setText(dataSnapshot.child("time_wisata").getValue().toString());
                xDateWisata.setText(dataSnapshot.child("date_wisata").getValue().toString());
                xKetentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Fungsi Btn Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Fungsi Btn Refund Now
        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Maaf, untuk saat ini belum bisa melakukan refund tiket!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
