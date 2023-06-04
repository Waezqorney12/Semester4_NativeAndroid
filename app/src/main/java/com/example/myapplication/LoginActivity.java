package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail,editPassword;
    Button btnLogin,btnRegist;
    SharedPreferences pref;
    LocalStorage localStorage;

    boolean showPassword = false;

    String email,password;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        localStorage = new LocalStorage(LoginActivity.this);
        // Check if user is already logged in


        editEmail = findViewById(R.id.txtemail);
        editPassword = findViewById(R.id.txtpassword);
        btnLogin = findViewById(R.id.btnlogin);
        btnRegist=findViewById(R.id.btnRegist);
        TextView forgot = findViewById(R.id.forgotPassword2);

        Drawable eyeIcon = getResources().getDrawable(R.drawable.ic_baseline_remove_red_eye_24);
        eyeIcon.setBounds(0,0,eyeIcon.getIntrinsicWidth(),eyeIcon.getIntrinsicHeight());

        editPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    Drawable drawable = editPassword.getCompoundDrawables()[DRAWABLE_RIGT];
                    if (drawable != null) {
                        int drawableWidht = drawable.getBounds().width();
                        int ClickExtra = 15;
                        if (event.getRawX() >= (editPassword.getRight() - drawableWidht - ClickExtra)) {
                            if (showPassword) {
                                editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                showPassword = false;
                                editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, R.drawable.ic_baseline_remove_red_eye_24, 0);
                            } else {
                                editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                showPassword = true;
                                editPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, R.drawable.ic_baseline_remove_red_eye_24_on, 0);
                            }
                        }
                    }
                    editPassword.setSelection(editPassword.getText().length());
                    editPassword.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editPassword, InputMethodManager.SHOW_IMPLICIT);

                    return true;
                }
                return false;
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }
    private void checkLogin(){
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            alertFail("Isi Email atau Password");
        }else {
            sendLogin();
        }
    }



    private void sendLogin() {
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = params.toString();
        Log.d("TAG", "Data: " + data);
        String url = getString(R.string.api_server) + "/login";
        Log.d("TAG", "URL: " + url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(LoginActivity.this, url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Integer code = http.getStatusCode();
                        Log.d("TAG", "Code: " + code);
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                Log.d("TAG", "response: " + response);
                                String token = response.getString("token");
                                Log.d("TAG", "token: " + token);
                                localStorage.setToken(token);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Log.d("TAG", "Intent: " + intent);
                                Toast.makeText(LoginActivity.this, "Selamat Datang di Aplikasi Laundry", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if (code == 422) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                if(response.has("message")){
                                    String msg = response.getString("message");
                                    alertFail(msg);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Data tidak dapat di proses", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (code == 401) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("Error");
                                alertFail(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error" + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void alertFail(String s){
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
