package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryActivity extends AppCompatActivity {
    ImageView btnBack,btnSetrikaHistory,btnCuciBasahHistory,btnDryCleaningHistory;
    List<ModelHistory> model;
    AdapterHistory adapterHistory;
    RecyclerView orderRV;

    SharedPreferences sharedPreferences;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();

        model = new ArrayList<>();

        sharedPreferences = getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");
        Log.d("TAGS","Token SharedPreferences History: " + token);

        orderRV = findViewById(R.id.order_RV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderRV.setLayoutManager(layoutManager);

        adapterHistory = new AdapterHistory(this, model);
        orderRV.setAdapter(adapterHistory);

        getHistory();

        btnBack = findViewById(R.id.btnbackHistory);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory = new Intent(HistoryActivity.this,MainActivity.class);
                startActivity(intentHistory);
            }
        });

        btnSetrikaHistory = findViewById(R.id.setrikaHistory);
        btnCuciBasahHistory = findViewById(R.id.cuciBasahHistory);
        btnDryCleaningHistory = findViewById(R.id.dryingCleaningHistory);

    }


    private void getHistory() {
        String BASE_URL = getString(R.string.api_server) + "/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHistory apiService = retrofit.create(ApiHistory.class);
        Call<List<ModelHistory>> call = apiService.getHistory("Bearer " + token);
        call.enqueue(new Callback<List<ModelHistory>>() {
            @Override
            public void onResponse(Call<List<ModelHistory>> call, Response<List<ModelHistory>> response) {
                Log.d("TAGS","BODY: " + response);
                if (response.isSuccessful()) {
                    List<ModelHistory> historyList = response.body();
                    model.addAll(historyList);
                    adapterHistory.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ModelHistory>> call, Throwable t) {
                // Handle failure
            }
        });
    }


}