package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {

    EditText eEmail,ePasswordOld,ePasswordNew;
    Button btnSubmit;
    ImageView btnBack;
    SharedPreferences sharedPreferences;
    String emailText,oldPassword,newPassword,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sharedPreferences = getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");
        eEmail = findViewById(R.id.eEmail);
        ePasswordOld = findViewById(R.id.ePasswordLama);
        ePasswordNew = findViewById(R.id.ePasswordBaru);
        btnBack = findViewById(R.id.btnChangePassword);
        btnSubmit = findViewById(R.id.bSubmit);
        getUser();

        eEmail.setText(getIntent().getStringExtra("email"));




        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emailText = eEmail.getText().toString();
                oldPassword = ePasswordOld.getText().toString();
                newPassword = ePasswordNew.getText().toString();

                if (emailText.isEmpty()){
                    Toast.makeText(ChangePassword.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }if(oldPassword.isEmpty()){
                    Toast.makeText(ChangePassword.this, "Password Lama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }if (newPassword.isEmpty()){
                    Toast.makeText(ChangePassword.this, "Password Baru tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    changePassword();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUser();
            }
        });
    }
    private void getUser(){
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String alamat = intent.getStringExtra("alamat");
        String telepon = intent.getStringExtra("telepon");
        String createdAt = intent.getStringExtra("created_at");
        String updatedAt = intent.getStringExtra("updated_at");

    }
    private void sendUser(){
        Intent intent = getIntent();
        String sUsername = intent.getStringExtra("username");
        String sEmail = intent.getStringExtra("email");
        String sAlamat = intent.getStringExtra("alamat");
        String sTelepon = intent.getStringExtra("telepon");
        String sCreatedAt = intent.getStringExtra("created_at");
        String sUpdatedAt = intent.getStringExtra("updated_at");

        Intent intenz = new Intent(ChangePassword.this, ProfileActivity.class);
        intenz.putExtra("username",sUsername);
        intenz.putExtra("email",sEmail);
        intenz.putExtra("alamat",sAlamat);
        intenz.putExtra("telepon",sTelepon);
        intenz.putExtra("created_at",sCreatedAt);
        intenz.putExtra("updated_at",sUpdatedAt);
        startActivity(intenz);


    }
    private void changePassword(){

        JSONObject params = new JSONObject();

        try {
            params.put("email",emailText);
            params.put("old_password",oldPassword);
            params.put("new_password",newPassword);
        }catch (JSONException e){

        }
        String data = params.toString();
        String url = getString(R.string.api_server)+"/change";

        if (url != null){
            Log.d("TAGS","Status URL: " + url);
        }else{
            Toast.makeText(ChangePassword.this, "URL tidak ditemukan", Toast.LENGTH_SHORT).show();
            Log.d("TAGS","Status URL: " + url);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(ChangePassword.this,url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        Log.d("TAGS","Status Code: " + code);
                        if (code == 200){
                            successAlert("Data Berhasil Di ubah");
                            Log.d("Tags","Code Status: " + code);

                        }if(code == 401){
                            Toast.makeText(ChangePassword.this, "Data tidak bisa di proses", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ChangePassword.this, "Gagal mengubah sandi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void successAlert(String msg){
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setIcon(R.drawable.ic_check)
                .setMessage(msg).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                }).show();
    }

//    private void getUser(String username,String email,String alamat,String telepon,String createdAt,String updatedAt){
//        Intent intent = getIntent();
//        username = intent.getStringExtra("username");
//        email = intent.getStringExtra("email");
//        alamat = intent.getStringExtra("alamat");
//        telepon = intent.getStringExtra("telepon");
//        createdAt = intent.getStringExtra("created_at");
//        updatedAt = intent.getStringExtra("updated_at");
//
//    }
//    private void sendUser(){
//        String username;
//        String email;
//
//        String alamat;
//        String telepon;
//        String createdAt;
//        String updatedAt;
//        getUser(username,email,alamat,telepon,createdAt,updatedAt);
//
//    }
}