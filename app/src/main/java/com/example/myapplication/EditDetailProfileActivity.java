package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EditDetailProfileActivity extends AppCompatActivity {
    TextView textUsernameProfile,textEmailProfile,textAlamatProfile,textTeleponProfile;
    ImageView btnBackEditProfile,btnSubmitEditProfile;
    TextView textIdProfile;
    String id,username,email,alamat,telepon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_profile);
        getSupportActionBar().hide();

        textUsernameProfile = findViewById(R.id.tUsernameEdit);
        textEmailProfile = findViewById(R.id.tEmailEdit);
        textAlamatProfile = findViewById(R.id.tAlamatEdit);
        textTeleponProfile = findViewById(R.id.tPhoneEdit);

        textIdProfile = findViewById(R.id.tIdDetailProfileEdit);

        btnBackEditProfile = findViewById(R.id.btnDetailBackEdit);
        btnSubmitEditProfile = findViewById(R.id.btnSubmitCheckDetailProfile);

        Intent intent = getIntent();
        textIdProfile.setText(intent.getStringExtra("id"));
        textUsernameProfile.setText(intent.getStringExtra("username"));
        textEmailProfile.setText(intent.getStringExtra("email"));
        textAlamatProfile.setText(intent.getStringExtra("alamat"));
        textTeleponProfile.setText(intent.getStringExtra("telepon"));

        Bundle bundle = new Bundle();
        if(bundle!=null){
            bundle.putString("id",intent.getStringExtra("id"));
            bundle.putString("username",intent.getStringExtra("username"));
            bundle.putString("email",intent.getStringExtra("email"));
            bundle.putString("alamat",intent.getStringExtra("alamat"));
            bundle.putString("telepon",intent.getStringExtra("telepon"));
            bundle.putString("created_at",intent.getStringExtra("created_at"));
            bundle.putString("updated_at",intent.getStringExtra("updated_at"));

        }


        btnBackEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(EditDetailProfileActivity.this,DetailProfileActivity.class);
                intentBack.putExtras(bundle);
                startActivity(intentBack);
            }
        });

        btnSubmitEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}