package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    List<ModelTransaction> models;
    List<ModelDetailTransaction> modelDetail;
    RecyclerView orderTS;
    ImageView backButton;
    int IDOutlet;
    Button submit;
    SharedPreferences sharedPreferences;
    String token;

    TextView idOutlet,idPaket,namaPaket,jenisPaket,hargaPaket,subtotalTransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        getSupportActionBar().hide();


        Log.d("TAGS","TOKEN: " + token);
        idOutlet = findViewById(R.id.idOutlet);
        idPaket = findViewById(R.id.idPaket);
        namaPaket = findViewById(R.id.namaPaket);
        jenisPaket = findViewById(R.id.jenisPaket);
        hargaPaket = findViewById(R.id.hargaPaket);

        submit = findViewById(R.id.submitPayment);
        subtotalTransaksi = findViewById(R.id.subtotalTransaksi);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendTransaksi();

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
        }

        orderTS = findViewById(R.id.order_TS);
        orderTS.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterTransaction(this);



        ApiTransaction.OutletApi apiTransaction = ApiTransaction.getApi();
        Call<List<ModelTransaction>> call = apiTransaction.getPaket(IDOutlet);
        call.enqueue(new Callback<List<ModelTransaction>>() {
            @Override
            public void onResponse(Call<List<ModelTransaction>> call, Response<List<ModelTransaction>> response) {
                if (response.isSuccessful()){

                    List<ModelTransaction> paketModel = response.body();

                    adapter.setData(paketModel,subtotalTransaksi);
                    orderTS.setAdapter(adapter);
                    subtotalTransaksi.setText(String.valueOf(adapter.getTotalHargaTransaksi()));

                    Log.d("TAGS:","TAGS:" + subtotalTransaksi);
                }
            }
            @Override
            public void onFailure(Call<List<ModelTransaction>> call, Throwable t) {

            }
        });
  }

    private void SendTransaksi() {
        String baseUrl = getString(R.string.api_server)+"/transaksi";
        sharedPreferences = getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");

        int subtotal = Integer.parseInt(subtotalTransaksi.getText().toString());
        JSONObject params = new JSONObject();
        try {
            params.put("id_outlet",IDOutlet);
            params.put("total_pesanan",subtotal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = params.toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(TransactionActivity.this,baseUrl);
                http.setMethod("POST");
                if (token!=null){
                    http.setToken(true);
                }
                Log.d("AUTH:","AUTH:"+token);
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        Log.d("TAGS","Code: " + code);
                        if (code == 201 || code == 200){
                            alertSuccess("Transaction Successful");
                        }
                        else if (code == 422){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail("Failed");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(TransactionActivity.this,"Errors" + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();


    }
    private void alertSuccess(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setIcon(R.drawable.ic_check)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                }).show();
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Fail")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void SendDetail(List<ModelDetailTransaction> detailTransactions){
        String BASE = getString(R.string.api_server)+"/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDetailTransaction apiDetailTransaction = retrofit.create(ApiDetailTransaction.class);
        Call<Void> call = apiDetailTransaction.sendDetail(detailTransactions);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(TransactionActivity.this, "Transaksi Berhasil", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TransactionActivity.this, "Transaksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

  }

}