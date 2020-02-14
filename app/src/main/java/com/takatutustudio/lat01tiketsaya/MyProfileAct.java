package com.takatutustudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProfileAct extends AppCompatActivity {
    ImageView photoProfile;
    TextView namaLengkap, bio;
    LinearLayout ItemMyTicket;
    Button btnEditProfile, btnSignOut;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Memanggil getUsernameLocal()
        getUsernameLocal();

        photoProfile    = findViewById(R.id.photo_profile);
        namaLengkap     = findViewById(R.id.nama_lengkap);
        bio             = findViewById(R.id.bio);
        ItemMyTicket    = findViewById(R.id.item_my_ticket);
        btnEditProfile  = findViewById(R.id.btn_view_ticket);
        btnSignOut      = findViewById(R.id.btn_sign_out);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namaLengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.with(MyProfileAct.this)
                        .load(dataSnapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit()
                        .into(photoProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Goto My Ticket Detail
        ItemMyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyticket = new Intent(MyProfileAct.this, MyTicketDetailAct.class);
                startActivity(gotomyticket);
            }
        });

        //Goto Edit Profile
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoeditprofile = new Intent(MyProfileAct.this, EditProfileAct.class);
                startActivity(gotoeditprofile);
            }
        });

        //Goto Sign Out
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public  void  getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
