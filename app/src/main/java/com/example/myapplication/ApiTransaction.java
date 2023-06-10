package com.example.myapplication;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ApiTransaction {
    private static final String BASE_URL = "http://192.168.1.5:8000/api/api/";

    public static OutletApi getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OutletApi.class);
    }

    public interface OutletApi {
        @GET("ambilpaketspesifik/{id_outlet}")
        Call<List<TransactionModel>> getPaket(@Path("id_outlet") int idOutlet);
    }
}
