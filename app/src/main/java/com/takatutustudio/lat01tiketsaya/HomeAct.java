package com.takatutustudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {
    LinearLayout btnTicketPisa;
    CircleView btnToProfile;
    ImageView photoHomeUser;
    TextView userName, bio, userBalance;

    DatabaseReference reference;

    String USERNAME_KEY     = "usernamekey";
    String username_key     = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //memanggil userNameLocal
        getUsernameLocal();

        photoHomeUser   = findViewById(R.id.photo_home_user);
        userName        = findViewById(R.id.username);
        bio             = findViewById(R.id.bio);
        userBalance     = findViewById(R.id.user_balance);
        btnTicketPisa   = findViewById(R.id.btn_ticket_pisa);
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

        //goto Ticket Detail
        btnTicketPisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailAct.class);
                startActivity(gotopisaticket);
            }
        });

        //goto Edit Profile
        btnToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyprofile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotomyprofile);
            }
        });
    }

    //getUsernameLocal
    public void  getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_MULTI_PROCESS);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
