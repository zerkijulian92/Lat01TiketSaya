package com.zerkistudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {
    LinearLayout btnBack;
    Button btnContinue, btnAddPhoto;
    ImageView picPhotoRegisterUser;
    EditText NameLengkap, bio;

    Uri photoLocation;
    Integer photoMax = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY     = "usernamekey";
    String username_key     = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal(); // mendapatkan Username local di awal

        btnBack                 = findViewById(R.id.btn_back);
        btnContinue             = findViewById(R.id.btn_continue);
        btnAddPhoto             = findViewById(R.id.btn_add_new_photo);
        picPhotoRegisterUser    = findViewById(R.id.pic_photo_register_user);
        NameLengkap            = findViewById(R.id.nama_lengkap);
        bio                     = findViewById(R.id.edtbio);

        //Fungsi btnBack
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Goto SuccesRegister
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ubah state menjadi loading
                btnContinue.setEnabled(false);
                btnContinue.setText("Loading...");

                final String name = NameLengkap.getText().toString();
                final String bIO  = bio.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi loading
                    btnContinue.setEnabled(true);
                    btnContinue.setText("CONTINUE");
                }
                else {
                    if (bIO.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Bio kosong!", Toast.LENGTH_SHORT).show();
                        //ubah state menjadi loading
                        btnContinue.setEnabled(true);
                        btnContinue.setText("CONTINUE");
                    }
                    else {

                        //menyimpan kepada firebase
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(username_key_new);

                        //menyimpan kepada firebase Storage
                        storage = FirebaseStorage.getInstance().getReference()
                                .child("PhotoUsers")
                                .child(username_key_new);

                        //validasi untuk file (apakah ada?)
                        if (photoLocation !=null) {
                            final StorageReference storageReference1 =
                                    storage.child(System.currentTimeMillis() + "."
                                            + getFileExtension(photoLocation));

                            //Update String Uri Photo Profile
                            storageReference1.putFile(photoLocation)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                            reference.getRef().child("nama_lengkap").setValue(NameLengkap.getText().toString());
                                            reference.getRef().child("bio").setValue(bio.getText().toString());

                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            //berpindah activity
                                            finish();
                                            Intent gotosuccesregister = new Intent(RegisterTwoAct.this, SuccesRegisterAct.class);
                                            startActivity(gotosuccesregister);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
            }
        });

        //Add Photo
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto(); //memanggil fungsi findPhoto
            }
        });
    }

    // Mendapatkan alamat file
    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // fungsi findPhoto
    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photoMax);
    }

   //Fungsi Menimban Photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photoMax && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photoLocation = data.getData();
            Picasso.with(this).load(photoLocation)
                    .centerCrop()
                    .fit()
                    .into(picPhotoRegisterUser);
        }
    }

    //Fungsi mendapatkan username local
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

    }
}
