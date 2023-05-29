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

public class PaketCuciBasahHistory extends AppCompatActivity {

    ImageView btnBackPaketCuciBasah;
    List<ModelHistory> model;
    AdapterHistory adapterHistory;
    RecyclerView orderCB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_cuci_basah_history);
        getSupportActionBar().hide();

        btnBackPaketCuciBasah = findViewById(R.id.btnBackPaketCuciHistory);
        btnBackPaketCuciBasah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaketCuciBasahHistory.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

        model = new ArrayList<>();
        model.add(new ModelHistory(R.drawable.cardboard, "Order No.01", "Cuci Basah","Rp.15000"));


        orderCB = findViewById(R.id.order_CB);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderCB.setLayoutManager(layoutManager);

        adapterHistory = new AdapterHistory(this, model);
        orderCB.setAdapter(adapterHistory);
    }
}