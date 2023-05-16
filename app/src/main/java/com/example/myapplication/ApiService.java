package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

//Interface untuk mengkomunikasikan API yang berisi bagian akses endpoint API
public interface ApiService {
    @PUT("profile")
    Call<Void> updateProfile(@Body ProfileModel profile);
}
