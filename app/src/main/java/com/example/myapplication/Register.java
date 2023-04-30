package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    EditText txtusername,txtpassword,txtemail,txtalamat,txttelepon,txtpasswordConfirmation;
    Button btnBackRegist,btnSignUp;
    String username,password,email,alamat,telepon,confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");

        btnBackRegist = findViewById(R.id.btnBackRegist);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        txtemail = findViewById(R.id.txtemail);
        txtalamat = findViewById(R.id.txtalamat);
        txttelepon = findViewById(R.id.txttelepon);
        txtpasswordConfirmation = findViewById(R.id.txtpasswordConfirmation);

        btnBackRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegist();
            }
        });

    }

    private void checkRegist() {
        username = txtusername.getText().toString();
        password = txtpassword.getText().toString();
        email = txtemail.getText().toString();
        alamat = txtalamat.getText().toString();
        telepon = txttelepon.getText().toString();
        confirmation = txtpasswordConfirmation.getText().toString();
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || alamat.isEmpty() || telepon.isEmpty()) {
            alertFail("Isi bagian yang kosong");
        }if (!password.equals(confirmation)){
            alertFail("Password tidak cocok");
        }else{
            sendRegister();
        }
    }

    private void sendRegister() {

        JSONObject params = new JSONObject();
        try {
            params.put("username",username);
            params.put("email",email);
            params.put("alamat",alamat);
            params.put("telepon",telepon);
            params.put("password",password);
            params.put("password_confirmation",confirmation);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = params.toString();
        String url = getString(R.string.api_server)+ "/register";
        if (url != null) {
            Log.d("URL", url);
        }


        new  Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(Register.this,url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 201 || code == 200){
                            alertSuccess("Register Successful");
                        }
                        else if (code == 422){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail("msg");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(Register.this,"Errors" + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void alertSuccess(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setIcon(R.drawable.ic_check)
                .setMessage(s)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                }).show();
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Fail")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}