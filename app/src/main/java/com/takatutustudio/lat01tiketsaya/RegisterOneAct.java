package com.takatutustudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {
    LinearLayout btnBack;
    Button btnContinue;
    EditText userName, passWord, emailAddress;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        userName        = findViewById(R.id.username);
        passWord        = findViewById(R.id.password);
        emailAddress    = findViewById(R.id.email_address);
        btnBack         = findViewById(R.id.btn_back);
        btnContinue     = findViewById(R.id.btn_continue);


        //Goto RegisterTwo
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ubah state menjadi loading
                btnContinue.setEnabled(false);
                btnContinue.setText("Loading...");

                //Menyimpan data kepada local Storage ( Handphone )
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, userName.getText().toString());
                editor.apply();

               // simpan kepada database
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userName.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(userName.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(passWord.getText().toString());
                        dataSnapshot.getRef().child("email_adddress").setValue(emailAddress.getText().toString());
                        dataSnapshot.getRef().child("user_balance").setValue(900);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //berpindah activity
                Intent gotoregistertwo = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(gotoregistertwo);
            }
        });

        //Fungsi btnBack
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
