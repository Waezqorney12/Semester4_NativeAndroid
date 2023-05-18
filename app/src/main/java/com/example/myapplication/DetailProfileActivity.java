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
    String emailDetail,usernameDetail,alamatDetail,teleponDetail,createdDetail,updatedDetail;
    int userId;
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
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            userId = bundle.getInt("id");
            usernameDetail = bundle.getString("username");
            emailDetail = bundle.getString("email");
            alamatDetail = bundle.getString("alamat");
            teleponDetail = bundle.getString("telepon");
            updatedDetail = bundle.getString("crated_at");
            createdDetail = bundle.getString("updated_at");

        }

        tIdDetailProfile.setText(String.valueOf(userId));
        tUsernameDetailProfile.setText(usernameDetail);
        tEMailDetailProfile.setText(emailDetail);
        tAlamatDetailProfile.setText(alamatDetail);
        tTeleponDetailProfile.setText(teleponDetail);






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