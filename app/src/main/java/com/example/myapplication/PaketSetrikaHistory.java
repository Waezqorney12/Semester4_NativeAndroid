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

public class PaketSetrikaHistory extends AppCompatActivity {

    ImageView btnBackPaketSetrika;
    List<ModelHistory> model;
    AdapterHistory adapterHistory;
    RecyclerView orderST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_setrika_history);
        getSupportActionBar().hide();

        btnBackPaketSetrika = findViewById(R.id.btnBackPaketSetrikaHistory);
        btnBackPaketSetrika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaketSetrikaHistory.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        model = new ArrayList<>();
//        model.add(new ModelHistory(R.drawable.cardboard, "Order No.02", "Setrika","Rp.12000"));


        orderST = findViewById(R.id.order_ST);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderST.setLayoutManager(layoutManager);

        adapterHistory = new AdapterHistory(this, model);
        orderST.setAdapter(adapterHistory);
    }
}