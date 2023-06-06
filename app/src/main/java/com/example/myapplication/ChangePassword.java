package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
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
    int id;
    String username,email,alamat,telepon,created_at,updated_at;

    boolean showPassword = false;
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

        getSupportActionBar().hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            id = bundle.getInt("id");
            username = bundle.getString("username");
            email =bundle.getString("email");
            alamat = bundle.getString("alamat");
            telepon = bundle.getString("telepon");
            created_at = bundle.getString("created_at");
            updated_at = bundle.getString("updated_at");
        }
//
        eEmail.setText(email);
        ePasswordOld.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int Drawable_Right = 15;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    Drawable icon =  ePasswordOld.getCompoundDrawables()[Drawable_Right];
                    if (icon != null){
                        int iconWidht = icon.getBounds().width();
                        int extraSpace = 15;
                        if (event.getRawX() >= (ePasswordOld.getRight() - iconWidht - extraSpace)){
                            ePasswordOld.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            showPassword = false;
                            ePasswordOld.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24,0,R.drawable.ic_baseline_remove_red_eye_24,0);
                        }else{
                            ePasswordOld.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            showPassword = true;
                            ePasswordOld.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24,0,R.drawable.ic_baseline_remove_red_eye_24_on,0);
                        }
                        return  true;
                    }
                }
                return false;
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emailText = eEmail.getText().toString();
                oldPassword = ePasswordOld.getText().toString();
                newPassword = ePasswordNew.getText().toString();

                if (emailText.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                   eEmail.setError("Isi email dengan benar");
                   eEmail.requestFocus();
                   return;
                }if(oldPassword.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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

    private void sendUser(){

        Intent intenz = new Intent(ChangePassword.this, ProfileActivity.class);
        intenz.putExtra("username",username);
        intenz.putExtra("email",email);
        intenz.putExtra("alamat",alamat);
        intenz.putExtra("telepon",telepon);
        intenz.putExtra("created_at",created_at);
        intenz.putExtra("updated_at",updated_at);
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