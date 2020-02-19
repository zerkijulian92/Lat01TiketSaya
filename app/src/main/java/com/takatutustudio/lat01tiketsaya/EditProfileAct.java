package com.takatutustudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditProfileAct extends AppCompatActivity {
    ImageView photoEditProfile;
    EditText edtNamaLengkap, edtBio, edtUsername, edtPassword, edtEmailAddress;
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
        edtNamaLengkap     = findViewById(R.id.edtnama_lengkap);
        edtBio             = findViewById(R.id.edtbio);
        edtUsername        = findViewById(R.id.edtusername);
        edtPassword        = findViewById(R.id.edtpassword);
        edtEmailAddress    = findViewById(R.id.edtemail_address);
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
                edtNamaLengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                edtBio.setText(dataSnapshot.child("bio").getValue().toString());
                edtUsername.setText(dataSnapshot.child("username").getValue().toString());
                edtPassword.setText(dataSnapshot.child("password").getValue().toString());
                edtEmailAddress.setText(dataSnapshot.child("email_address").getValue().toString());
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

                //Ubah state menjadi loading
                btnSaveProfile.setEnabled(false);
                btnSaveProfile.setText("Loading...");

                // Fungsi kondisi ketika EditText Kosong
                final String xedtnamalengkap  = edtNamaLengkap.getText().toString();
                final String xedtbio          = edtBio.getText().toString();
                final String xedtusername     = edtUsername.getText().toString();
                final String xedtpassword     = edtPassword.getText().toString();
                final String xedtemailaddress = edtEmailAddress.getText().toString();

                if (xedtnamalengkap.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama kosong!", Toast.LENGTH_SHORT).show();
                    //Ubah state menjadi loading
                    btnSaveProfile.setEnabled(true);
                    btnSaveProfile.setText("SAVE PROFILE");

                } else if (xedtbio.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Bio kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi loading
                    btnSaveProfile.setEnabled(true);
                    btnSaveProfile.setText("SAVE PROFILE");

                } else if (xedtusername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi loading
                    btnSaveProfile.setEnabled(true);
                    btnSaveProfile.setText("SAVE PROFILE");

                } else if (xedtpassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state mejadi loading
                    btnSaveProfile.setEnabled(true);
                    btnSaveProfile.setText("SAVE PROFILE");

                } else {
                    if (xedtemailaddress.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Email address kosong!", Toast.LENGTH_SHORT).show();
                        //ubah state menjadi loading
                        btnSaveProfile.setEnabled(true);
                        btnSaveProfile.setText("SAVE PROFILE");
                    }
                    else {

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("nama_lengkap").setValue(edtNamaLengkap.getText().toString());
                                dataSnapshot.getRef().child("bio").setValue(edtBio.getText().toString());
                                dataSnapshot.getRef().child("username").setValue(edtUsername.getText().toString());
                                dataSnapshot.getRef().child("password").setValue(edtPassword.getText().toString());
                                dataSnapshot.getRef().child("email_address").setValue(edtEmailAddress.getText().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        // Berpindah activity
                        Intent gotobackprofile = new Intent(EditProfileAct.this, MyProfileAct.class);
                        startActivity(gotobackprofile);

                    }
                }

//                reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("nama_lengkap").setValue(edtNamaLengkap.getText().toString());
//                        dataSnapshot.getRef().child("bio").setValue(edtBio.getText().toString());
//                        dataSnapshot.getRef().child("username").setValue(edtUsername.getText().toString());
//                        dataSnapshot.getRef().child("password").setValue(edtPassword.getText().toString());
//                        dataSnapshot.getRef().child("email_address").setValue(edtEmailAddress.getText().toString());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                // Berpindah activity
//                Intent gotobackprofile = new Intent(EditProfileAct.this, MyProfileAct.class);
//                startActivity(gotobackprofile);
            }
        });
    }

    // Fungsi mendapatkan username secara local
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
