package com.takatutustudio.lat01tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SigInAct extends AppCompatActivity {
    TextView btnCreateNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnCreateNewAccount = findViewById(R.id.btn_create_new_account);
        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(SigInAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
    }
}
