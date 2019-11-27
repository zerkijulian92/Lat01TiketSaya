package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TicketCheckoutAct extends AppCompatActivity {
    Button btnBuyTicket, btnMinus, btnPlus;
    LinearLayout btnBack;
    TextView textJmlTiket, textMyBalance, textTotalHarga;
    ImageView icNoMoney;
    Integer myBalance = 200;
    Integer valueJumlahTiket = 1;
    Integer valuetotalHarga = 0;
    Integer valuehargaTiket = 75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        //Registrasi Element
        btnMinus        = findViewById(R.id.btn_minus);
        btnPlus         = findViewById(R.id.btn_plus);
        textJmlTiket    = findViewById(R.id.text_jml_tiket);
        btnBuyTicket    = findViewById(R.id.btn_buy_ticket);
        textMyBalance   = findViewById(R.id.text_my_balance);
        textTotalHarga  = findViewById(R.id.text_total_harga);
        icNoMoney       = findViewById(R.id.ic_no_money);
        btnBack         = findViewById(R.id.btn_back);

       //Setting Value baru untuk beberapa komponen
        textJmlTiket.setText(valueJumlahTiket.toString());
        textMyBalance.setText("US$ " + myBalance+"");

        //Memunculakan Nilai Di Awal saat app pertama kali di jalankan
        valuetotalHarga = valuehargaTiket * valueJumlahTiket;
        textTotalHarga.setText("US$ " + valuetotalHarga+"");

        //Menghilangkan btnMinus di awal ( Default ) ketika
        //aplikasi pertama kali di jalankan
        icNoMoney.setVisibility(View.GONE); // menyembunyikan icNoMoney
        btnMinus.animate().alpha(0).setDuration(300).start();
        btnMinus.setEnabled(false);

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
                Intent gotosuccesbuyticket = new Intent(TicketCheckoutAct.this, SuccesBuyTicketAct.class);
                startActivity(gotosuccesbuyticket);
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
