package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
    ImageView btnHistory,btnMap,btnSetting,btnWhatsapp,btnCloseMain;
    String username,token,email,telepon,alamat,created_at,updated_at;
    int id;
    SharedPreferences sharedPreferences;
    TextView tUsername;
    int lastPosition = 0;
    boolean isScrolling = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Token
        sharedPreferences = getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");
        Log.d("TAGS","Token SharedPreferences: " + token);


        getOwner(new OwnerCallback() {
            @Override
            public void onOwnerReceived(String phone) {

                Log.d("TAGS", "Owner Phone: " + phone);
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
                t.printStackTrace();
            }
        });
        tUsername = findViewById(R.id.tUsernameMain);

        btnHistory = findViewById(R.id.btnHistoryMain);
        btnMap = findViewById(R.id.btnMapMain);
        btnSetting = findViewById(R.id.btnSettingMain);
        btnWhatsapp = findViewById(R.id.btnWhatsappMain);
        btnCloseMain = findViewById(R.id.buttonCloseMain);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                sendUser(intent);
                startActivity(intent);
            }
        });
        btnCloseMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (token!=null){
                    System.exit(0);
                }else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

        getUser(new UserCallBack(){

            @Override
            public void onReceive(int id,String username,String email,String alamat,String telepon,String created_at,String updated_at) {
                tUsername.setText(username);

                btnSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                        sendUser(intent);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onErrorResponse(Throwable t) {

            }
        });



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
                            Intent paketSetrika = new Intent(MainActivity.this,ProfileActivity.class);
                            startActivity(paketSetrika);
                            break;
                        case 1:
                            Intent paketCuciBasah = new Intent(MainActivity.this,DetailProfileActivity.class);
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
                // Request HTTP ke server fungsi dari CAll
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


    private void getUser(UserCallBack userCallBack){
        String url = getString(R.string.api_server)+"/user";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(MainActivity.this,url);

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

                                userCallBack.onReceive(id,username,email,alamat,telepon,created_at,updated_at);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (code == 401) {
                            Toast.makeText(MainActivity.this, "Unauthorized access. Please login again.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Data tidak terdeteksi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }


public interface UserCallBack{
        void onReceive(int id,String username,String email,String alamat,String telepon,String created_at,String updated_at);
        void onErrorResponse(Throwable t);
}

    public interface OwnerCallback {
        void onOwnerReceived(String phone);
        void onOwnerError(Throwable t);
    }

    private void sendUser(Intent intent){
        intent.putExtra("id",id);
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        intent.putExtra("alamat",alamat);
        intent.putExtra("telepon",telepon);
        intent.putExtra("updated_at",updated_at);
        intent.putExtra("created_at",created_at);
    }


//    private void logout(){
//        String url = getString(R.string.api_server)+"/logout";
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Http http = new Http(MainActivity.this,url);
//                http.setMethod("POST");
//                http.setToken(true);
//                http.send();
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Integer code = http.getStatusCode();
//                        if (code == 200){
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
////                            Log.d("Tag","Editor: " + editor);
//                            editor.remove("TOKEN");
//                            editor.apply();
//                            Intent intents = new Intent(MainActivity.this, LoginActivity.class);
//                            startActivity(intents);
//                            finish();
//                            Toast.makeText(MainActivity.this,"Logout Berhasil",Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }
//        }).start();
//
//
//    }

//    private void sendUser(){
//        String url = getString(R.string.api_server)+"/user";
//
////        Log.d("Tag","Http:"+url);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Http http = new Http(MainActivity.this,url);
//
//                http.setToken(true);
//                http.send();
////                Log.d("Tag","Http:"+http);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Integer code = http.getStatusCode();
//                        if (code == 200){
//                            try {
//                                JSONObject response = new JSONObject(http.getResponse());
//                                int id = Integer.parseInt(response.getString("id"));
//                                Log.d("TAGS", String.valueOf(id));
//                                String username = response.getString("username");
//                                String email = response.getString("email");
//                                String alamat = response.getString("alamat");
//                                String telepon = response.getString("telepon");
//
//                                String created_at = response.getString("created_at");
//                                String updated_at = response.getString("updated_at");
//
//                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                                intent.putExtra("id",id);
//                                intent.putExtra("username",username);
//                                intent.putExtra("email",email);
//                                intent.putExtra("alamat",alamat);
//                                intent.putExtra("telepon",telepon);
//
//                                intent.putExtra("created_at",created_at);
//                                intent.putExtra("updated_at",updated_at);
//                                startActivity(intent);
//                                finish();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        else if (code == 401) {
//                            Toast.makeText(MainActivity.this, "Unauthorized access. Please login again.", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                            finish();
//                        }
//                        else {
//                            Toast.makeText(MainActivity.this, "Data tidak terdeteksi", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        }).start();
//    }

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