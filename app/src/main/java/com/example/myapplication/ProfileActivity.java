package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    Button back,btnPassword,btnPersonal,btnLogout;

    TextView username,alamat,email,telepon,createdAt,updatedAt;
    SharedPreferences sharedPreferences;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        back = findViewById(R.id.keluar);
        username = findViewById(R.id.pUsername);

        sharedPreferences = getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");
        Log.d("TAGS","TOKEN: " + token);
        btnPassword = findViewById(R.id.bPassword);
        btnPersonal = findViewById(R.id.bPersonal);
        btnLogout = findViewById(R.id.bLogout);

        Intent intent = getIntent();

        //Untuk menaruh di personal info
        String usernames = intent.getStringExtra("username");
        String emails = intent.getStringExtra("email");
        String alamats = intent.getStringExtra("alamat");
        String telepons = intent.getStringExtra("telepon");
        String createdAts = intent.getStringExtra("created_at");
        String updatedAts = intent.getStringExtra("updated_at");

        username.setText(intent.getStringExtra("username"));

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProfileActivity.this,ChangePassword.class);
                intent1.putExtra("username",usernames);
                intent1.putExtra("email",emails);
                intent1.putExtra("alamat",alamats);
                intent1.putExtra("telepon",telepons);
                intent1.putExtra("created_at",createdAts);
                intent1.putExtra("updated_at",updatedAts);
                startActivity(intent1);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentz = new Intent(ProfileActivity.this,Personal.class);
                intentz.putExtra("username",usernames);
                intentz.putExtra("email",emails);
                intentz.putExtra("alamat",alamats);
                intentz.putExtra("telepon",telepons);
                intentz.putExtra("created_at",createdAts);
                intentz.putExtra("updated_at",updatedAts);
                startActivity(intentz);
            }
        });
        if (back != null){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent back = new Intent(ProfileActivity.this,DashboardActivity.class);
//                    back.putExtra("username",usernames);
//                    back.putExtra("email",emails);
//                    back.putExtra("alamat",alamats);
//                    back.putExtra("telepon",telepons);
//                    back.putExtra("created_at",createdAts);
//                    back.putExtra("updated_at",updatedAts);

                    startActivity(back);
                }
            });
        }else{
            Toast.makeText(ProfileActivity.this, "Tidak bisa back", Toast.LENGTH_SHORT).show();
        }

    }
    private void logout(){
        String url = getString(R.string.api_server)+"/logout";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(ProfileActivity.this,url);
                Log.d("TAGS","HTTP: " +http);
                http.setMethod("POST");
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        Log.d("TAGS","CODE: " + code);
                        if (code == 200){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Log.d("Tag","Editor: " + editor);
                            editor.remove("TOKEN");
                            editor.apply();
                            Intent intents = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(intents);
                            finish();
                            Toast.makeText(ProfileActivity.this,"Logout Berhasil",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();


    }

}