package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionActivity extends AppCompatActivity {

    AdapterTransaction adapter;
    List<TransactionModel> models;
    RecyclerView orderTS;
    ImageView backButton;
    int IDOutlet;
    Button submit;

    TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket,subtotalTransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        getSupportActionBar().hide();

        idOutlet = findViewById(R.id.idOutlet);
        idPaket = findViewById(R.id.idPaket);
        namaPaket = findViewById(R.id.namaPaket);
        jenisPaket = findViewById(R.id.jenisPaket);
        hargaPaket = findViewById(R.id.hargaPaket);

        submit = findViewById(R.id.submitPayment);
        subtotalTransaksi = findViewById(R.id.subtotalTransaksi);




        backButton = findViewById(R.id.btnBackTransaction);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent!=null){
            String idString = intent.getStringExtra("id");
            if (idString != null){
                IDOutlet = Integer.parseInt(idString);
            }
        }

        orderTS = findViewById(R.id.order_TS);
        orderTS.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterTransaction(this);



        ApiTransaction.OutletApi apiTransaction = ApiTransaction.getApi();
        Call<List<TransactionModel>> call = apiTransaction.getPaket(IDOutlet);
        call.enqueue(new Callback<List<TransactionModel>>() {
            @Override
            public void onResponse(Call<List<TransactionModel>> call, Response<List<TransactionModel>> response) {
                if (response.isSuccessful()){

                    List<TransactionModel> paketModel = response.body();

                    adapter.setData(paketModel,subtotalTransaksi);
                    orderTS.setAdapter(adapter);
                    subtotalTransaksi.setText(String.valueOf(adapter.getTotalHargaTransaksi()));

                    Log.d("TAGS:","TAGS:" + subtotalTransaksi);
                }
            }
            @Override
            public void onFailure(Call<List<TransactionModel>> call, Throwable t) {

            }
        });
  }

}