package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailProfileActivity extends AppCompatActivity {

    TextView tTeleponDetailProfile,tAlamatDetailProfile,tEMailDetailProfile,textEditDetailProfile,tIdDetailProfile,tUsernameDetailProfile;
    ImageView btnEditProfile,btnDetailBack;
    String userId,emailDetail,usernameDetail,alamatDetail,teleponDetail,createdDetail,updatedDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
        getSupportActionBar().hide();

        tTeleponDetailProfile = findViewById(R.id.tTeleponDetailProfile);
        tAlamatDetailProfile = findViewById(R.id.tDetailAlamatProfile);
        tEMailDetailProfile = findViewById(R.id.tEmailDetailProfile);
        textEditDetailProfile = findViewById(R.id.textEditDetailProfile);
        tIdDetailProfile = findViewById(R.id.tIdDetailProfile);
        tUsernameDetailProfile = findViewById(R.id.tUsernameDetailProfile);

        btnDetailBack = findViewById(R.id.btnDetailBack);
        btnEditProfile = findViewById(R.id.btnEditDetailProfile);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        usernameDetail = intent.getStringExtra("username");
        alamatDetail = intent.getStringExtra("alamat");
        teleponDetail = intent.getStringExtra("telepon");
        updatedDetail = intent.getStringExtra("updated_at");
        createdDetail = intent.getStringExtra("created_at");
        emailDetail = intent.getStringExtra("email");

        tIdDetailProfile.setText(userId);
        tUsernameDetailProfile.setText(usernameDetail);
        tEMailDetailProfile.setText(emailDetail);
        tAlamatDetailProfile.setText(alamatDetail);
        tTeleponDetailProfile.setText(teleponDetail);

        Bundle bundle = new Bundle();
        if (bundle != null){
            bundle.putString("id",intent.getStringExtra("id"));
            bundle.putString("username",intent.getStringExtra("username"));
            bundle.putString("email",intent.getStringExtra("email"));
            bundle.putString("alamat",intent.getStringExtra("alamat"));
            bundle.putString("telepon",intent.getStringExtra("telepon"));
            bundle.putString("created_at",intent.getStringExtra("created_at"));
            bundle.putString("updated_at",intent.getStringExtra("updated_at"));
        }




        btnDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(DetailProfileActivity.this,ProfileActivity.class);
                intentBack.putExtras(bundle);
                startActivity(intentBack);

            }
        });
        textEditDetailProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditDetailProfile = new Intent(DetailProfileActivity.this,EditDetailProfileActivity.class);
                intentEditDetailProfile.putExtras(bundle);
                startActivity(intentEditDetailProfile);
            }
        });
    }
}