package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SigInAct extends AppCompatActivity {
    TextView btnCreateNewAccount;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnCreateNewAccount = findViewById(R.id.btn_create_new_account);
        btnSignIn = findViewById(R.id.btn_sign_in);

        //Goto RegisterOne
        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(SigInAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });

        //Goto HomeAct
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SigInAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
}
