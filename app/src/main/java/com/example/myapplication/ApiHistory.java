package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiHistory {
    @GET("showauth")
    Call<List<ModelHistory>> getHistory(@Header("Authorization") String token);
}

