package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    EditText txtusername,txtpassword,txtemail,txtpasswordConfirmation;
    Button btnSignUp;
    String username,password,email,confirmation;
    ImageView btnBackRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();


        btnBackRegist = findViewById(R.id.btnBackRegis);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        txtemail = findViewById(R.id.txtemail);

        txtpasswordConfirmation = findViewById(R.id.txtpasswordConfirmation);

        txtusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                username = s.toString();
                if (username.length() < 6 || username.length() > 30) {
                    if (username.length() < 6) {
                        txtusername.setError("Minimum 6 Characters");

                    } else if (username.length() > 30) {
                        txtusername.setError("Maximum 30 Characters");
                    }
                }

            }
        });

        txtemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                email = s.toString();
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtemail.setError("Invalid Email Address");
                }
            }
        });

        txtpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
                if (password.isEmpty() || password.length() < 6) {
                    txtpassword.setError("Fill your Password (Minimum 6 Characters)");

                }
            }
        });

        txtpasswordConfirmation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                confirmation = s.toString();
                if (!confirmation.equals(password)) {
                    txtpasswordConfirmation.setError("Passwords Don't Match");


                }
            }
        });
        btnBackRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputAuth() == true){
                    sendRegister();
                }else{

                }
            }
        });

    }


    private void sendRegister() {

        JSONObject params = new JSONObject();
        try {
            params.put("username",username);
            params.put("email",email);
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
                                alertFail("Failed");
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
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    private boolean inputAuth() {
        String typeUsername = txtusername.getText().toString();
        String typeEmail = txtemail.getText().toString();
        String typePassword = txtpassword.getText().toString();
        String typeConfirmation = txtpasswordConfirmation.getText().toString();

        if (typeUsername.length() < 6 || typeUsername.length() > 30) {
            if (typeUsername.length() < 6) {
                txtusername.setError("Minimum 6 Characters");
                txtusername.requestFocus();
                return false;

            } else if (typeUsername.length() > 30) {
                txtusername.setError("Maximum 30 Characters");
                txtusername.requestFocus();
                return false;

            }

        }else if (typeEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(typeEmail).matches()) {
            txtemail.setError("Invalid Email Address");
            txtemail.requestFocus();
            return false;
        }else if (typePassword.isEmpty() || typePassword.length() < 6) {
            txtpassword.setError("Fill your Password (Minimum 6 Characters)");
            txtpassword.requestFocus();
            return false;
        }else if (!typePassword.equals(typeConfirmation)) {
            txtpasswordConfirmation.setError("Passwords Don't Match");
            txtpasswordConfirmation.requestFocus();
            return false;
        }

        return true;
    }


}