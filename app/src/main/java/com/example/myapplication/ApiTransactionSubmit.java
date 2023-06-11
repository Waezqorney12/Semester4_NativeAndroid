package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiTransactionSubmit {
    @POST("transaksi")
    Call<List<ModelSubmitTransaction>> sendTransaksi(@Header("Authorization")String token);

}
