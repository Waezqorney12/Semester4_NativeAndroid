package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionActivity extends AppCompatActivity {

    AdapterTransaction adapterTransaction;
    List<TransactionModel> transactionModels;
    RecyclerView orderTS;
    int IDOutlet;
    TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        idOutlet = findViewById(R.id.idOutlet);
        idPaket = findViewById(R.id.idPaket);
        namaPaket = findViewById(R.id.namaPaket);
        jenisPaket = findViewById(R.id.jenisPaket);
        hargaPaket = findViewById(R.id.hargaPaket);

        Intent intent = getIntent();
        if (intent!=null){
            String idString = intent.getStringExtra("id");
            if (idString != null){
                IDOutlet = Integer.parseInt(idString);
            }
            Log.d("TAGS","ID Outlet:" + IDOutlet);
        }


        orderTS = findViewById(R.id.order_TS);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderTS.setLayoutManager(layoutManager);

        adapterTransaction = new AdapterTransaction(this, transactionModels);
        orderTS.setAdapter(adapterTransaction);

        getPaket();


  }

    private void getPaket(){
        String BASE_URL = getString(R.string.api_server)+"/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiTransaction apiTransaction = retrofit.create(ApiTransaction.class);
        Call<TransactionModel[]> call = apiTransaction.getTransactions(IDOutlet);

        call.enqueue(new Callback<TransactionModel[]>() {
            @Override
            public void onResponse(Call<TransactionModel[]> call, Response<TransactionModel[]> response) {
                if (response.isSuccessful()) {
                    TransactionModel[] responseModels = response.body();

                    if (responseModels != null) {
                        // Mengisi data ke variabel transactionModels
                        transactionModels.addAll(Arrays.asList(responseModels));
                        adapterTransaction.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionModel[]> call, Throwable t) {

            }
        });
    }
}