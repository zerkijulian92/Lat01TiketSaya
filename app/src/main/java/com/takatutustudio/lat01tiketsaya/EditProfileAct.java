package com.takatutustudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditProfileAct extends AppCompatActivity {
    ImageView photoEditProfile;
    EditText xNamaLengkap, xBio, xUsername, xPassword, xEmailAddress;
    Button btnSaveProfile;
    LinearLayout btnBack;

    DatabaseReference reference;

    String USERNAME_KEY     = "usernamekey";
    String username_key     = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // Deklarasi ID
        photoEditProfile = findViewById(R.id.photo_edit_profile);
        xNamaLengkap     = findViewById(R.id.xname_lengkap);
        xBio             = findViewById(R.id.xbio);
        xUsername        = findViewById(R.id.xusername);
        xPassword        = findViewById(R.id.xpassword);
        xEmailAddress    = findViewById(R.id.xemail_address);
        btnSaveProfile   = findViewById(R.id.btn_save_profile);
        btnBack          = findViewById(R.id.btn_back);

        // Memanggil Fungsi mendapatkan username secara local
        getUsernameLocal();

        // Menghubungkan data ke firebase
        // mendapatkan data dari firebase dan memunculkannya di EditText
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xNamaLengkap.setText(dataSnapshot.child("Users").getValue().toString());
                xBio.setText(dataSnapshot.child("bio").getValue().toString());
                xUsername.setText(dataSnapshot.child("username").getValue().toString());
                xPassword.setText(dataSnapshot.child("password").getValue().toString());
                xEmailAddress.setText(dataSnapshot.child("email_address").getValue().toString());
                Picasso.with(EditProfileAct.this)
                        .load(dataSnapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit()
                        .into(photoEditProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Fungsi Btn Save Profile
        // Mengupdate data local yang ada di EditText ke firebase
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(xNamaLengkap.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(xBio.getText().toString());
                        dataSnapshot.getRef().child("username").setValue(xUsername.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(xPassword.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(xEmailAddress.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    // Fungsi mendapatkan username secara local
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
