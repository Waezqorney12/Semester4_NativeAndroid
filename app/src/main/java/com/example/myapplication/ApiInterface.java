package com.example.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    //disesuaikan dengan routes yang ada pada laravel
    @POST("forgot")
    Call<ResponseBody> forgotPassword(@Field("email") String email);
}

