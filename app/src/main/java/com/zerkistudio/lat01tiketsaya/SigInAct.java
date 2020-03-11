package com.zerkistudio.lat01tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigInAct extends AppCompatActivity {
    TextView btnCreateNewAccount;
    Button btnSignIn;
    EditText xuserName, xpassWord;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        xuserName            = findViewById(R.id.edtusername);
        xpassWord            = findViewById(R.id.edtpassword);
        btnCreateNewAccount = findViewById(R.id.btn_create_new_account);
        btnSignIn           = findViewById(R.id.btn_sign_in);

        //Goto RegisterOne
        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotoregisterone = new Intent(SigInAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
        //Goto HomeAct
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ubah state menjadi loading
                btnSignIn.setEnabled(false);
                btnSignIn.setText("Loading...");

                final String username = xuserName.getText().toString();
                final String password = xpassWord.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi loading
                    btnSignIn.setEnabled(true);
                    btnSignIn.setText("SIGN IN");
                }
                else {
                    if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                        //ubah state menjadi loading
                        btnSignIn.setEnabled(true);
                        btnSignIn.setText("SIGN IN");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //ambil data password dari firebase
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password dengan password firebase
                                    if (password.equals(passwordFromFirebase)) {
                                        // simpan username (key) kepada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xuserName.getText().toString());
                                        editor.apply();

                                        //berpindah activity ke home
                                        finish();
                                        Intent gotohome = new Intent(SigInAct.this, HomeAct.class);
                                        startActivity(gotohome);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_SHORT).show();
                                        // ubah state menjadi loading
                                        btnSignIn.setEnabled(true);
                                        btnSignIn.setText("SIGN IN");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username tidak ada!", Toast.LENGTH_SHORT).show();
                                    // ubah state menjadi loading
                                    btnSignIn.setEnabled(true);
                                    btnSignIn.setText("SIGN IN");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
        });
    }
}
