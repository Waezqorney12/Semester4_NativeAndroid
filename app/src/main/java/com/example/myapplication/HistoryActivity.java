package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HistoryActivity extends AppCompatActivity {
    ImageView btnBack,btnSetrikaHistory,btnCuciBasahHistory,btnDryCleaningHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.btnbackHistory);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory = new Intent(HistoryActivity.this,DashboardActivity.class);
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