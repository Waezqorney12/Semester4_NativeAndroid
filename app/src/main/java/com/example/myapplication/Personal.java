package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Personal extends AppCompatActivity {
    TextView perUsername,perEmail,perALamat,perTelepon,perCreated,perUpdated;
    Button perBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        perUsername = findViewById(R.id.perUsername);
        perEmail = findViewById(R.id.perEmail);
        perALamat = findViewById(R.id.perAlamat);
        perTelepon = findViewById(R.id.perTelepon);
        perCreated = findViewById(R.id.perCreated);
        perUpdated = findViewById(R.id.perUpdated);

        Intent perIntent = getIntent();
        perUsername.setText(perIntent.getStringExtra("username"));
        perEmail.setText(perIntent.getStringExtra("email"));
        perALamat.setText(perIntent.getStringExtra("alamat"));
        perTelepon.setText(perIntent.getStringExtra("telepon"));
        perCreated.setText(perIntent.getStringExtra("created_at"));
        perUpdated.setText(perIntent.getStringExtra("updated_at"));

        perBack = findViewById(R.id.perBack);

        perBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perIntent = new Intent(Personal.this, ProfileActivity.class);
                perIntent.putExtra("username",perUsername.getText().toString());
                perIntent.putExtra("email",perEmail.getText().toString());
                perIntent.putExtra("alamat",perALamat.getText().toString());
                perIntent.putExtra("telepon",perTelepon.getText().toString());
                perIntent.putExtra("created_at",perCreated.getText().toString());
                perIntent.putExtra("updated_at",perUpdated.getText().toString());
                startActivity(perIntent);
            }
        });
    }
}