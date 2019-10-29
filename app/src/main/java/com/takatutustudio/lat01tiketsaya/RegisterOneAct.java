package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class RegisterOneAct extends AppCompatActivity {
    LinearLayout btnBack;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btnBack     = findViewById(R.id.btn_back);
        btnContinue     = findViewById(R.id.btn_continue);

       //Fungsi btnBack
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Goto RegisterTwo
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregistertwo = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(gotoregistertwo);
            }
        });
    }
}
