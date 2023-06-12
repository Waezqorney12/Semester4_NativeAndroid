package com.example.myapplication;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiSendTransaction {
    @POST("transaksi")
    Call<JsonObject> createTransaksi(
            @Header("Authorization") String accessToken,
            @Body JsonObject requestBody
    );
}

