package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LocalStorage {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String token;


    public LocalStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("STORAGE_LOGIN_API",Context.MODE_PRIVATE);
        Log.d("Tag","Shared:"+sharedPreferences);
        editor = sharedPreferences.edit();
    }
    public String getToken(){
        token = sharedPreferences.getString("TOKEN",null);
        Log.d("Tag","Token: "+token);
        return token;
    }
    public void setToken(String token){
        editor.putString("TOKEN",token);
        editor.commit();
        this.token = token;
    }

}
