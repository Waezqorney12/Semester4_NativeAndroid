package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    Context context;
    private String url,method = "GET", data=null,response=null;
    private Integer statusCode= 0;
    private Boolean token = false;
    private LocalStorage localStorage;

    public Http(Context context, String url) {
        this.context = context;
        this.url = url;
        localStorage = new LocalStorage(context);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    public String getResponse() {
        return response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
    public void send(){
        try {
            URL sUrl = new URL(url);
            Log.d("TAG","status: "+sUrl);
            HttpURLConnection connection = (HttpURLConnection) sUrl.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
            if (token){
                Log.d("TAG","laler: "+token);
                connection.setRequestProperty("Authorization","Bearer " + localStorage.getToken());


            }if (!method.equals("GET")){
                connection.setDoOutput(true);
            }if (data != null){
                OutputStream os = connection.getOutputStream();
                Log.d("TAG","laasdr: "+os);
                os.write(data.getBytes());
                os.flush();
                os.close();
            }
            statusCode = connection.getResponseCode();
            Log.d("TAG","status: "+statusCode);
            InputStreamReader isr;
            if (statusCode >= 200 && statusCode <= 299){
                //if response sukses
                isr = new InputStreamReader(connection.getInputStream());
                Log.d("TAG","Sukses: "+isr);
            }else{
                //if response gagal
                isr = new InputStreamReader(connection.getErrorStream());
                Log.d("TAG","Gagal: "+isr);
            }

            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ( (line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            response = sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
