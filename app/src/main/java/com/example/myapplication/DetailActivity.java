package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView username,alamat;
    Button keluar;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private  static  final String SHARED_PREF_NAME = "mypref";
    private  static  final String KEY_NAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        username = findViewById(R.id.username);
        alamat = findViewById(R.id.alamat);

        pref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = pref.getString(KEY_NAME,null);
        String alamats = "Mundu";
        if (name != null){
            username.setText(name);
            alamat.setText(alamats);
        }
//        keluar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intents = new Intent(DetailActivity.this,DashboardActivity.class);
//                startActivity(intents);
//            }
//        });

    }
}