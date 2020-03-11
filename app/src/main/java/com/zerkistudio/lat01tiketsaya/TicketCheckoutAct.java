package com.zerkistudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {
    Button btnBuyTicket, btnMinus, btnPlus;
    LinearLayout btnBack;
    TextView textJmlTiket, textMyBalance, textTotalHarga, textNamaWisata, textLokasi, textKetentuan;
    ImageView icNoMoney;
    Integer myBalance = 0;
    Integer valueJumlahTiket = 1;
    Integer valuetotalHarga = 0;
    Integer valuehargaTiket = 0;
    Integer sisaBalance = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY     = "usernamekey";
    String username_key     = "";
    String username_key_new = "";

    String dateWisata = "";
    String timeWisata = "";

    //generate nomor integer secara random
    // karena kita ingin membuat transaksi secara unik
    Integer nomorTransaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        //Registrasi Element
        btnMinus        = findViewById(R.id.btn_minus);
        btnPlus         = findViewById(R.id.btn_plus);
        textJmlTiket    = findViewById(R.id.text_jml_tiket);
        btnBuyTicket    = findViewById(R.id.btn_buy_ticket);
        textMyBalance   = findViewById(R.id.text_my_balance);
        textTotalHarga  = findViewById(R.id.text_total_harga);
        icNoMoney       = findViewById(R.id.ic_no_money);
        btnBack         = findViewById(R.id.btn_back);
        textNamaWisata  = findViewById(R.id.nama_wisata);
        textLokasi      = findViewById(R.id.lokasi);
        textKetentuan   = findViewById(R.id.ketentuan);

       //Setting Value baru untuk beberapa komponen
        textJmlTiket.setText(valueJumlahTiket.toString());


        //Menghilangkan btnMinus di awal ( Default ) ketika
        //aplikasi pertama kali di jalankan
        icNoMoney.setVisibility(View.GONE); // menyembunyikan icNoMoney
        btnMinus.animate().alpha(0).setDuration(300).start();
        btnMinus.setEnabled(false);

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // mengambil data user dari firebase
                myBalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textMyBalance.setText("US$ " + myBalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru
                textNamaWisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                textLokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                textKetentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                valuehargaTiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                dateWisata = dataSnapshot.child("date_wisata").getValue().toString();
                timeWisata = dataSnapshot.child("time_wisata").getValue().toString();

                valuetotalHarga = valuehargaTiket * valueJumlahTiket;
                textTotalHarga.setText("US$ " + valuetotalHarga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Fungsi Btn Minus
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueJumlahTiket-=1;
                textJmlTiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket < 2) {
                    btnMinus.animate().alpha(0).setDuration(300).start();
                    btnMinus.setEnabled(false);
                }
                valuetotalHarga = valuehargaTiket * valueJumlahTiket;
                textTotalHarga.setText("US$ " + valuetotalHarga+"");

                //Fungsi jika valueTotalHarga lebih kecil dari myBalance
                if (valuetotalHarga < myBalance) {
                    btnBuyTicket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btnBuyTicket.setEnabled(true);
                    textMyBalance.setTextColor(Color.parseColor("#203DD1"));
                    icNoMoney.setVisibility(View.GONE); // menyembunyikan icNoMoney
                }
            }
        });

        //Fungsi Btn Plus
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueJumlahTiket+=1;
                textJmlTiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket > 1) {
                    btnMinus.animate().alpha(1).setDuration(300).start();
                    btnMinus.setEnabled(true);
                }
                valuetotalHarga = valuehargaTiket * valueJumlahTiket;
                textTotalHarga.setText("US$ " + valuetotalHarga+"");

                //Fungsi jika valueTotalHarga lebih besar dari myBalance
                if (valuetotalHarga > myBalance) {
                    btnBuyTicket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btnBuyTicket.setEnabled(false);
                    textMyBalance.setTextColor(Color.parseColor("#D1206B"));
                    Toast.makeText(getApplicationContext(), "Maaf, saldo anda tidak mencukupi untuk membeli Tiket Ini",Toast.LENGTH_SHORT).show();
                    icNoMoney.setVisibility(View.VISIBLE); //Memunculkan icMoney
                }
            }
        });

        //Goto SuccesBuyTicket
        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menyimpan data user kepada firebase dan membuat tabel baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance()
                        .getReference().child("MyTickets")
                        .child(username_key_new)
                        .child(textNamaWisata.getText().toString() + nomorTransaksi);

                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(textNamaWisata.getText().toString() + nomorTransaksi);
                        reference3.getRef().child("nama_wisata").setValue(textNamaWisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(textLokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(textKetentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTiket.toString());
                        reference3.getRef().child("date_wisata").setValue(dateWisata);
                        reference3.getRef().child("time_wisata").setValue(timeWisata);

                        finish();
                        Intent gotosuccesbuyticket = new Intent(TicketCheckoutAct.this, SuccesBuyTicketAct.class);
                        startActivity(gotosuccesbuyticket);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // Update data balance kepada Users ( yang saat ini login)
                // Mengambil data user dari firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisaBalance = myBalance - valuetotalHarga;
                        reference4.getRef().child("user_balance").setValue(sisaBalance);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //btn Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onBackPressed();

                // onBackPrassed dimatikan
                // karena menggunakan finish() untuk menghapus activity yang menimban
                // sebagai gantinya menggunakan intent dan ditambah finish() untuk menghapus activity yg menimban

                finish();
                Intent gotobacticketdetail= new Intent(TicketCheckoutAct.this, TicketDetailAct.class);
                startActivity(gotobacticketdetail);
            }
        });
    }
    //Fungsi mendapatkan username local
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

    }
}
