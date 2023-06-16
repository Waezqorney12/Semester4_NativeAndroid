package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_LOCATION = 1001;

    private FusedLocationProviderClient fusedLocationProviderClient;

    ViewPager viewPager;
    AdapterMain adapter;
    List<ModelMain> models;
    ImageView btnHistory,btnMap,btnSetting,btnWhatsapp,btnCloseMain;
    String username,token,email,telepon,alamat,created_at,updated_at;
    int id;
    SharedPreferences sharedPreferences;
    TextView tUsername;
    int lastPosition = 0;
    boolean isScrolling = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Token
        sharedPreferences = getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");
        Log.d("TAGS","Token SharedPreferences: " + token);

        btnWhatsapp = findViewById(R.id.btnWhatsappMain);
        getOwner(new OwnerCallback() {
            @Override
            public void onOwnerReceived(String phone) {

                Log.d("TAGS", "Owner Phone: " + phone);
                btnWhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onOwnerError(Throwable t) {
                t.printStackTrace();
            }
        });
        tUsername = findViewById(R.id.tUsernameMain);

        btnHistory = findViewById(R.id.btnHistoryMain);
        btnMap = findViewById(R.id.btnMapMain);
        btnSetting = findViewById(R.id.btnSettingMain);

        btnCloseMain = findViewById(R.id.buttonCloseMain);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_LOCATION);
                } else {
                    getCurrentLocation();
                }
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                sendUser(intent);
                startActivity(intent);
                finish();
            }
        });
        btnCloseMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (token!=null){
                    System.exit(0);
                }else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

        getUser(new UserCallBack(){

            @Override
            public void onReceive(int id,String username,String email,String alamat,String telepon,String created_at,String updated_at) {
                tUsername.setText(username);

                btnSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                        sendUser(intent);
                        startActivity(intent);
                        finish();
                    }
                });


            }

            @Override
            public void onErrorResponse(Throwable t) {

            }
        });



        models = new ArrayList<>();
        ApiOutletClass.ApiOutlet apiOutlet = ApiOutletClass.getClient().create(ApiOutletClass.ApiOutlet.class);
        Call<List<ModelMain>> call = apiOutlet.getOutlet();

        call.enqueue(new Callback<List<ModelMain>>() {
            @Override
            public void onResponse(Call<List<ModelMain>> call, Response<List<ModelMain>> response) {
                if (response.isSuccessful()){
                    List<ModelMain> outletBody = response.body();
                    Log.d("TAGS","Response: " + outletBody);
                    if (outletBody!=null){
                        models.addAll(outletBody);

                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelMain>> call, Throwable t) {

            }
        });

        adapter = new AdapterMain(models,this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(10,0,250,50);

//
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
            }

            @Override
            public void onPageSelected(int position) {
                lastPosition = position;
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isScrolling = true;
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    isScrolling = false;
                }

            }
        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && !isScrolling){
                    int selectedPosition = viewPager.getCurrentItem();
                    adapter.openActivity(selectedPosition);
                }
                return false;
            }
        });
    }

    private void getOwner(OwnerCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String URL = getString(R.string.api_server)+"/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService api = retrofit.create(ApiService.class);
                // Request HTTP ke server fungsi dari CAll
                Call<List<OwnerModel>> call = api.getOwner();
                try {
                    Response<List<OwnerModel>> response = call.execute();
                    if (response.isSuccessful()) {
                        List<OwnerModel> owners = response.body();
                        if (owners != null && !owners.isEmpty()) {
                            OwnerModel owner = owners.get(0);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String phone = owner.getPhone();
                                    callback.onOwnerReceived(phone);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onOwnerError(e);
                }
            }
        }).start();
    }


    private void getUser(UserCallBack userCallBack){
        String url = getString(R.string.api_server)+"/user";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(MainActivity.this,url);

                http.setToken(true);
                http.send();
                Log.d("Tag","Http:"+http);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                id = response.getInt("id");
                                username = response.getString("username");
                                email = response.getString("email");
                                alamat = response.getString("alamat");
                                telepon = response.getString("telepon");
                                created_at = response.getString("created_at");
                                updated_at = response.getString("updated_at");

                                userCallBack.onReceive(id,username,email,alamat,telepon,created_at,updated_at);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (code == 401) {
                            Toast.makeText(MainActivity.this, "Unauthorized access. Please login again.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Data tidak terdeteksi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }


public interface UserCallBack{
        void onReceive(int id,String username,String email,String alamat,String telepon,String created_at,String updated_at);
        void onErrorResponse(Throwable t);
}

    public interface OwnerCallback {
        void onOwnerReceived(String phone);
        void onOwnerError(Throwable t);
    }

    private void sendUser(Intent intent){
        intent.putExtra("id",id);
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        intent.putExtra("alamat",alamat);
        intent.putExtra("telepon",telepon);
        intent.putExtra("updated_at",updated_at);
        intent.putExtra("created_at",created_at);
    }


    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        } else {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            getAddressFromLocation(latitude, longitude);
                        } else {
                            Toast.makeText(MainActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);
                Toast.makeText(MainActivity.this, "Current Address: " + fullAddress, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Address not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}