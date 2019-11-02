package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TicketCheckoutAct extends AppCompatActivity {
    Button btnBuyTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btnBuyTicket    = findViewById(R.id.btn_buy_ticket);

        //Goto SuccesBuyTicket
        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosuccesbuyticket = new Intent(TicketCheckoutAct.this, SuccesBuyTicketAct.class);
                startActivity(gotosuccesbuyticket);
            }
        });
    }
}
