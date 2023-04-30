package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {
    Button btnLogout;
    Button btnDetail;
    Button btnBack;

    TextView tUsername,tEmail,tAlamat,tTelepon,createdAt,updatedAt;

//    SharedPreferences pref;
//    SharedPreferences.Editor editor;
//    private  static  final String SHARED_PREF_NAME = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);
//            pref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

//            editor = pref.edit();

            tUsername = findViewById(R.id.tUsername);
        tEmail = findViewById(R.id.tEmail);
        tAlamat = findViewById(R.id.tAlamat);
        tTelepon = findViewById(R.id.tTelepon);
         createdAt = findViewById(R.id.createdAt);
        updatedAt = findViewById(R.id.updatedAt);

            btnBack = findViewById(R.id.btnBack);
            btnLogout = findViewById(R.id.btnLogout);
            btnDetail = findViewById(R.id.btnDetail);

            getUser();

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
//                    editor.clear();
//                    editor.apply();


                }

            });
            btnDetail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,DetailActivity.class));


            }
        });
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intents = new Intent(DashboardActivity.this,MainActivity.class);
                    startActivity(intents);
                }
            });
        }
    private void getUser(){
        String url = getString(R.string.api_server)+"/user";
        Log.d("Tag","Http:"+url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(DashboardActivity.this,url);

                http.setToken(true);
                http.send();
                Log.d("Tag","Http:"+http);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String username = response.getString("username");
                                String email = response.getString("email");
                                String alamat = response.getString("alamat");
                                String telepon = response.getString("telepon");
                                String created_at = response.getString("created_at");
                                String updated_at = response.getString("updated_at");

                                tUsername.setText(username);
                                tEmail.setText(email);
                                tAlamat.setText(alamat);
                                tTelepon.setText(telepon);
                                createdAt.setText(created_at);
                                updatedAt.setText(updated_at);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (code == 401) {
                            Toast.makeText(DashboardActivity.this, "Unauthorized access. Please login again.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(DashboardActivity.this, "Data tidak terdeteksi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }


    private void logout(){
        String url = getString(R.string.api_server)+"/logout";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(DashboardActivity.this,url);
                http.setMethod("POST");
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200){
                            Intent intents = new Intent(DashboardActivity.this, MainActivity.class);
                            startActivity(intents);
                            finish();
                            Toast.makeText(DashboardActivity.this,"Logout Berhasil",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();


        }
    // Get the SharedPreferences object
  }


//super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_dashboard);
//    Intent intent = getIntent();
//    String data = intent.getStringExtra("username");
//        Log.i("data intent",data);
//
//    pref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
//    editor = pref.edit();
//
//    btnBack = findViewById(R.id.btnBack);
//    btnLogout = findViewById(R.id.btnLogout);
//    btnDetail = findViewById(R.id.btnDetail);
//
////        pref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
////        String name = pref.getString("username", null);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            editor.clear();
//            editor.apply();
//            Intent intents = new Intent(DashboardActivity.this, MainActivity.class);
//            startActivity(intents);
//            finish();
//            Toast.makeText(DashboardActivity.this,"Logout Berhasil",Toast.LENGTH_LONG).show();
//        }
//
//    });
////        btnDetail.setOnClickListener(new View.OnClickListener(){
////            String username;
////            String alamat;
////            @Override
////            public void onClick(View view) {
////                startActivity(new Intent(DashboardActivity.this,DetailActivity.class));
////
////
////            }
////        });
//        btnBack.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intents = new Intent(DashboardActivity.this,MainActivity.class);
//            startActivity(intents);
//        }
//    });
//}