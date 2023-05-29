package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PaketDryCleaningHistory extends AppCompatActivity {

    ImageView btnBackDry;
    List<ModelHistory> model;
    AdapterHistory adapterHistory;
    RecyclerView orderDC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_dry_cleaning_history);
        getSupportActionBar().hide();

        btnBackDry = findViewById(R.id.btnBackPaketDryHistory);
        btnBackDry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaketDryCleaningHistory.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        model = new ArrayList<>();
        model.add(new ModelHistory(R.drawable.cardboard, "Order No.03", "Dry Cleaning","Rp.10000"));


        orderDC = findViewById(R.id.order_DC);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderDC.setLayoutManager(layoutManager);

        adapterHistory = new AdapterHistory(this, model);
        orderDC.setAdapter(adapterHistory);
    }
}