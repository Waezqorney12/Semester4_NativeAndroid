package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    Button keluar;

    TextView username,alamat,email,telepon,createdAt,updatedAt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        keluar = findViewById(R.id.keluar);
        username = findViewById(R.id.pUsername);
        email = findViewById(R.id.pEmail);
        alamat = findViewById(R.id.pAlamat);
        telepon = findViewById(R.id.pTelepon);
        createdAt = findViewById(R.id.pcreatedAt);
        updatedAt = findViewById(R.id.pupdatedAt);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));
        email.setText(intent.getStringExtra("email"));
        alamat.setText(intent.getStringExtra("alamat"));
        telepon.setText(intent.getStringExtra("telepon"));
        createdAt.setText(intent.getStringExtra("created_at"));
        updatedAt.setText(intent.getStringExtra("updated_at"));

        if (keluar != null){
            keluar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent back = new Intent(DetailActivity.this,DashboardActivity.class);
                    startActivity(back);
                }
            });
        }else{
            Toast.makeText(DetailActivity.this, "Tidak bisa back", Toast.LENGTH_SHORT).show();
        }

    }

}