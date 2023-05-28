package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;

//Interface untuk mengkomunikasikan API yang berisi bagian akses endpoint API
public interface ApiService {
    @GET("get")
    Call<List<OwnerModel>> getOwner();
}
