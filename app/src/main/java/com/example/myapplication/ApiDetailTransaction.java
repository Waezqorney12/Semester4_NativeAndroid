package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiDetailTransaction {
    @POST("sendingdetail")
    Call<Void> sendDetail(@Body List<ModelDetailTransaction>detailTransactions);
}
