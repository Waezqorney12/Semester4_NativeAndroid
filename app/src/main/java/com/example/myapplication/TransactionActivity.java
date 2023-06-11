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

    TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket,totalPayment;
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
        totalPayment = findViewById(R.id.totalPayment);
        submit = findViewById(R.id.submitPayment);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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
            Log.d("TAGS","ID Outlet:" + IDOutlet);
        }

        orderTS = findViewById(R.id.order_TS);
        orderTS.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterTransaction();

        ApiTransaction.OutletApi apiTransaction = ApiTransaction.getApi();
        Call<List<TransactionModel>> call = apiTransaction.getPaket(IDOutlet);
        call.enqueue(new Callback<List<TransactionModel>>() {
            @Override
            public void onResponse(Call<List<TransactionModel>> call, Response<List<TransactionModel>> response) {
                if (response.isSuccessful()){
                    List<TransactionModel> paketModel = response.body();
                    adapter.setData(paketModel);
                    orderTS.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<TransactionModel>> call, Throwable t) {

            }
        });



  }

//    private void getPaket() {
//        String BASE_URL = getString(R.string.api_server) + "/";
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiTransaction apiTransaction = retrofit.create(ApiTransaction.class);
//        Call<JSONArray> call = apiTransaction.getTransactions(IDOutlet);
//
//        call.enqueue(new Callback<JSONArray>() {
//            @Override
//            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    JSONArray responseArray = response.body();
//
//                    for (int i = 0; i < responseArray.length(); i++) {
//                        try {
//                            JSONObject jsonObject = responseArray.getJSONObject(i);
//                            transactionModels.add(jsonObject);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    adapterTransaction.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSONArray> call, Throwable t) {
//
//            }
//        });
//    }

}