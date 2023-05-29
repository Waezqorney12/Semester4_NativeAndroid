package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
        btnBackPaketCuciBasah = findViewById(R.id.btnBackPaketCuciHistory);
        btnBackPaketCuciBasah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaketCuciBasahHistory.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}