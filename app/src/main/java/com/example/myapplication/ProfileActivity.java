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
    String usernames,emails,alamats,telepons,createdAts,updatedAts;
    int id;
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
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            id = bundle.getInt("id");
            usernames = bundle.getString("username");
            emails = bundle.getString("email");
            alamats = bundle.getString("alamat");
            telepons = bundle.getString("telepon");
            createdAts = bundle.getString("created_at");
            updatedAts = bundle.getString("updated_at");
        }
        //Untuk menaruh di personal info


        username.setText(usernames);

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPassword = new Intent(ProfileActivity.this,ChangePassword.class);
                sendUser(intentPassword);
                startActivity(intentPassword);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                finish();
            }
        });
        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPersonalInfo = new Intent(ProfileActivity.this,DetailProfileActivity.class);
                sendUser(intentPersonalInfo);
                startActivity(intentPersonalInfo);
            }
        });
        if (back != null){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent back = new Intent(ProfileActivity.this,DashboardActivity.class);

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
    private void sendUser(Intent intent){
        intent.putExtra("id",id);
        intent.putExtra("username",usernames);
        intent.putExtra("email",emails);
        intent.putExtra("alamat",alamats);
        intent.putExtra("telepon",telepons);
        intent.putExtra("updated_at",updatedAts);
        intent.putExtra("created_at",createdAts);
    }

}