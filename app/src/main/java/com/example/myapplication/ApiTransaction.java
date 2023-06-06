package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiTransaction {
    @GET("ambilpaketspesifik/{id_outlet}")
    Call<TransactionModel[]> getTransactions(@Path("id_outlet") int idOutlet);
}
