package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ImageView btnBack,btnSetrikaHistory,btnCuciBasahHistory,btnDryCleaningHistory;
    List<ModelHistory> model;
    AdapterHistory adapterHistory;
    RecyclerView orderRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();

        model = new ArrayList<>();
        model.add(new ModelHistory(R.drawable.cardboard, "Order No.01", "Cuci Basah"));
        model.add(new ModelHistory(R.drawable.cardboard, "Order No.02", "Setrika"));
        model.add(new ModelHistory(R.drawable.cardboard, "Order No.03", "Dry Cleaning"));

        orderRV = findViewById(R.id.order_RV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderRV.setLayoutManager(layoutManager);

        adapterHistory = new AdapterHistory(this, model);
        orderRV.setAdapter(adapterHistory);

        btnBack = findViewById(R.id.btnbackHistory);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory = new Intent(HistoryActivity.this,MainActivity.class);
                startActivity(intentHistory);
            }
        });

        btnSetrikaHistory = findViewById(R.id.setrikaHistory);
        btnSetrikaHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCuciBasahHistory = findViewById(R.id.cuciBasahHistory);
        btnCuciBasahHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDryCleaningHistory = findViewById(R.id.dryingCleaningHistory);
        btnDryCleaningHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}