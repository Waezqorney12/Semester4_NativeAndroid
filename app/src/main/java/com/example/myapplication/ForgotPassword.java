package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPassword extends AppCompatActivity {
    ImageView btnforgot;
    EditText tEmail;
    Button bSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Untuk kembali ke halaman login
        btnforgot = findViewById(R.id.btnforgotPassword);

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this,Login.class);
                startActivity(intent);
            }
        });

        tEmail = findViewById(R.id.txtEmails);
        bSubmit = findViewById(R.id.btnSubmitPassword);

        bSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = tEmail.getText().toString();
                Log.d("TAGS","Email: "+email);
                //Validasi Email
                if (TextUtils.isEmpty(email)|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Log.d("TAGS","ur email:"+email);
                    tEmail.setError("Invalid Email");
                    tEmail.requestFocus();
                    return;
                }
                //Method untuk mengirim email melalui API
                sendEmailToApi(email);
            }
        });

    }

    private void sendEmailToApi(String email) {
        String url = getString(R.string.api_server)+"/";
        //Retrofit merupakan library yang mengirim dan menerima data dari API
        //Jadi retrofit disini dipakai untuk mengirim email ke user dalam perubahan password bedasarkan dengan api_server
        //
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                //dengan menggunakan GsonConverter ini kita bisa mengkonversi objek berbahasa java ke JSON
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("TAGS","Status Retrofit" + retrofit);
        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<ResponseBody> call = api.forgotPassword(email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Jika berhasil
                Log.d("TAGS","Response: "+response);
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Toast.makeText(ForgotPassword.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d("TAGS","Status: " + jsonObject.getString("message"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Log.e("TAGS","Status:" + e);
                    }
                } else {
                    String message = "Failed to send email";
                    try {
                        String error = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(error);
                        message = jsonObject.getString("error");
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Log.e("TAGS","Status:" + e);
                    }
                    Toast.makeText(ForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            //Jika gagal
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAGS", "Status: onFailure", t);
                Toast.makeText(ForgotPassword.this, "Failed to send email", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
