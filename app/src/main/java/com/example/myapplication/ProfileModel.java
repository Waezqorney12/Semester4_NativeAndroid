package com.example.myapplication;

public class ProfileModel {

    private String id;
    private String username;
    private String email;
    private String alamat;
    private String telepon;

    public ProfileModel(String id, String username, String email, String alamat, String telepon){

        this.id = id;
        this.username = username;
        this.email = email;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public boolean getUpdatedAt() {return getUpdatedAt();
    }

    public boolean getCreatedAt() {return  getCreatedAt();
    }
}
