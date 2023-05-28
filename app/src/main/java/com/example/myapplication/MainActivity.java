package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    AdapterMain adapter;
    List<ModelMain> models;
    ImageView btnHistory,btnMap,btnSetting,btnWhatsapp;
    String phone;
    TextView textPhone;
    int lastPosition = 0;
    boolean isScrolling = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        getOwner(new OwnerCallback() {
            @Override
            public void onOwnerReceived(String phone) {
                // Di sini Anda dapat menggunakan nilai 'phone' yang telah diterima
                Log.d("TAGS", "TAGaers: " + phone);
                btnWhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onOwnerError(Throwable t) {
                // Tangani kesalahan jika terjadi
                t.printStackTrace();
            }
        });

        btnHistory = findViewById(R.id.cuciBasahHistory);
        btnMap = findViewById(R.id.mapUser);
        btnSetting = findViewById(R.id.SettingHistory);
        btnWhatsapp = findViewById(R.id.whatsappUser);


        models = new ArrayList<>();
        models.add(new ModelMain(R.drawable.setrika_foto,"Paket Setrika","Khusus untuk menyetrika pakaian","Rp.12000"));
        models.add(new ModelMain(R.drawable.cuci_basah_foto,"Paket Cuci Basah","Khusus untuk mencuci pakaian","Rp.15000"));
        models.add(new ModelMain(R.drawable.dry_cleaning_foto,"Paket Dry Cleaning","Khusus untuk mengeringkan pakaian","Rp.10000"));

        adapter = new AdapterMain(models,this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0,0,450,50);

//
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
            }

            @Override
            public void onPageSelected(int position) {
                lastPosition = position;
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isScrolling = true;
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    isScrolling = false;
                }

            }
        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && !isScrolling){
                    switch (lastPosition){
                        case 0:
                            Intent paketSetrika = new Intent(MainActivity.this,DashboardActivity.class);
                            startActivity(paketSetrika);
                            break;
                        case 1:
                            Intent paketCuciBasah = new Intent(MainActivity.this,ChangePassword.class);
                            startActivity(paketCuciBasah);
                            break;
                        case 2:
                            Intent paketDryCleaning = new Intent(MainActivity.this,HistoryActivity.class);
                            startActivity(paketDryCleaning);
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void getOwner(OwnerCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String URL = "http://192.168.1.5:8000/api/api/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService api = retrofit.create(ApiService.class);

                Call<List<OwnerModel>> call = api.getOwner();
                try {
                    Response<List<OwnerModel>> response = call.execute();
                    if (response.isSuccessful()) {
                        List<OwnerModel> owners = response.body();
                        if (owners != null && !owners.isEmpty()) {
                            OwnerModel owner = owners.get(0);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String phone = owner.getPhone();
                                    callback.onOwnerReceived(phone);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onOwnerError(e);
                }
            }
        }).start();
    }

    public interface OwnerCallback {
        void onOwnerReceived(String phone);
        void onOwnerError(Throwable t);
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