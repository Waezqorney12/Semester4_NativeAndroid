package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditDetailProfileActivity extends AppCompatActivity {
    TextView textUsernameProfile,textEmailProfile,textAlamatProfile,textTeleponProfile;
    ImageView btnBackEditProfile,btnSubmitEditProfile;
    TextView textIdProfile;
    String username,email,alamat,telepon,token;
    SharedPreferences sharedPreferences;
    Integer id;
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
        textIdProfile.setText(intent.getStringExtra("id"));
        textUsernameProfile.setText(intent.getStringExtra("username"));
        textEmailProfile.setText(intent.getStringExtra("email"));
        textAlamatProfile.setText(intent.getStringExtra("alamat"));
        textTeleponProfile.setText(intent.getStringExtra("telepon"));

        Bundle bundle = new Bundle();
        if(bundle!=null){
            bundle.putString("id", String.valueOf(textIdProfile));
            bundle.putString("username", String.valueOf(textUsernameProfile));
            bundle.putString("email", String.valueOf(textEmailProfile));
            bundle.putString("alamat", String.valueOf(textAlamatProfile));
            bundle.putString("telepon", String.valueOf(textTeleponProfile));
            bundle.putString("created_at",intent.getStringExtra("created_at"));
            bundle.putString("updated_at",intent.getStringExtra("updated_at"));

        }


        btnBackEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(EditDetailProfileActivity.this,DetailProfileActivity.class);
                intentBack.putExtra("id",intent.getStringExtra("id"));
                intentBack.putExtra("username",intent.getStringExtra("username"));
                intentBack.putExtra("email",intent.getStringExtra("email"));
                intentBack.putExtra("alamat",intent.getStringExtra("alamat"));
                intentBack.putExtra("telepon",intent.getStringExtra("telepon"));
                intentBack.putExtra("created_at",intent.getStringExtra("created_at"));
                intentBack.putExtra("updated_at",intent.getStringExtra("updated_at"));

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
                            successAlert("Data berhasil di ubah",profile);
                        } else {
                            Toast.makeText(EditDetailProfileActivity.this, "Gagal memperbarui data profil", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();


    }
    private void successAlert(String msg,ProfileModel profile){
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setIcon(R.drawable.ic_check)
                .setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        returnToDetailProfile(profile);
                    }
                }).show();
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