package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class EditDetailProfileActivity extends AppCompatActivity {
    TextView textUsernameProfile,textEmailProfile,textAlamatProfile,textTeleponProfile;
    ImageView btnBackEditProfile,btnSubmitEditProfile;
    TextView textIdProfile;
    String username,email,alamat,telepon,token,created_at,updated_at;
    SharedPreferences sharedPreferences;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_profile);
        getSupportActionBar().hide();



        textUsernameProfile = findViewById(R.id.tUsernameEdit);
        textEmailProfile = findViewById(R.id.tEmailEdit);
        textAlamatProfile = findViewById(R.id.tAlamatEdit);
        textTeleponProfile = findViewById(R.id.tPhoneEdit);

        textIdProfile = findViewById(R.id.tIdDetailProfileEdit);

        btnBackEditProfile = findViewById(R.id.btnDetailBackEdit);
        btnSubmitEditProfile = findViewById(R.id.btnSubmitCheckDetailProfile);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            id = bundle.getInt("id");
            username = bundle.getString("username");
            email = bundle.getString("email");
            alamat = bundle.getString("alamat");
            telepon = bundle.getString("telepon");
            created_at = bundle.getString("created_at");
            updated_at = bundle.getString("updated_at");

        }

        textIdProfile.setText(String.valueOf(id));
        textUsernameProfile.setText(username);
        textEmailProfile.setText(email);
        textAlamatProfile.setText(alamat);
        textTeleponProfile.setText(telepon);




        btnBackEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(EditDetailProfileActivity.this,DetailProfileActivity.class);
                sendData(intentBack);
                startActivity(intentBack);
            }
        });

        btnSubmitEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }
    private void updateProfile() {
        String baseUrl = getString(R.string.api_server)+"/profile";

        LocalStorage localStorage = new LocalStorage(EditDetailProfileActivity.this);


        ProfileModel profile = new ProfileModel(
                textIdProfile.getText().toString(),
                textUsernameProfile.getText().toString(),
                textEmailProfile.getText().toString(),
                textAlamatProfile.getText().toString(),
                textTeleponProfile.getText().toString()
        );
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(EditDetailProfileActivity.this,baseUrl);
                http.setMethod("PUT");

                String authToken = localStorage.getToken();
                if (authToken!=null){
                    http.setToken(true);
                }
                Log.d("AUTH:","AUTH:"+authToken);

                //Mengubah objek profileModel menjadi JSON
                Gson gson = new Gson();
                String jsonProfile = gson.toJson(profile);
                http.setData(jsonProfile);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        String response = http.getResponse();
                        Log.d("Codes:", "sad"+String.valueOf(code));
                        Log.d("Respons:","sads:"+response);
                        if (code>=200 & code <= 299) {
                            successAlert("Data berhasil di ubah");
                        } else {
                            Toast.makeText(EditDetailProfileActivity.this, "Gagal memperbarui data profil", Toast.LENGTH_SHORT).show();
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
                .setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendUser();
                    }
                }).show();
    }
    private void sendUser(){
        String url = getString(R.string.api_server)+"/user";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(EditDetailProfileActivity.this,url);

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
                                 id = response.getInt("id");
                                 username = response.getString("username");
                                 email = response.getString("email");
                                 alamat = response.getString("alamat");
                                 telepon = response.getString("telepon");
                                 created_at = response.getString("created_at");
                                 updated_at = response.getString("updated_at");

                                Intent intent = new Intent(EditDetailProfileActivity.this, DetailProfileActivity.class);
                                sendData(intent);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (code == 401) {
                            Toast.makeText(EditDetailProfileActivity.this, "Unauthorized access. Please login again.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditDetailProfileActivity.this, LoginActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(EditDetailProfileActivity.this, "Data tidak terdeteksi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
    private void sendData(Intent intent){
        intent.putExtra("id",id);
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        intent.putExtra("alamat",alamat);
        intent.putExtra("telepon",telepon);

        intent.putExtra("created_at",created_at);
        intent.putExtra("updated_at",updated_at);
    }
//    private void returnToDetailProfile(ProfileModel profile) {
//        Intent intent = new Intent(EditDetailProfileActivity.this, DetailProfileActivity.class);
//        intent.putExtra("profile", profile);
//        startActivity(intent);
//        finish(); // Optional: Menutup EditDetailProfileActivity agar tidak kembali ke halaman ini saat menekan tombol back di DetailProfileActivity
//    }
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        //API Service untuk Update profile
//        ApiService apiService = retrofit.create(ApiService.class);
//        sharedPreferences = getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
//        token = sharedPreferences.getString("TOKEN", "");

    //Menggunakan textIdProfile dikarenakan apabila value dari ProfileModelnya masih bernilai null atau default
//        Call<Void> call = apiService.updateProfile(profile);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(EditDetailProfileActivity.this, "Data profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(EditDetailProfileActivity.this, "Gagal memperbarui data profil", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                // Kegagalan komunikasi dengan server
//                Toast.makeText(EditDetailProfileActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
}