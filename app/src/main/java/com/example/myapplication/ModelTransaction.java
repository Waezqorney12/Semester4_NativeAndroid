package com.example.myapplication;

public class ModelTransaction {
    private String id_outlet;
    private String id_paket;
    private String nama_paket;
    private String jenis;
    private String harga;
    private int qty;


    public ModelTransaction(String id_outlet, String id_paket, String nama_paket, String jenis, String harga) {
        this.id_outlet = id_outlet;
        this.id_paket = id_paket;
        this.nama_paket = nama_paket;
        this.jenis = jenis;
        this.harga = harga;
        this.qty = 5;

    }

    public String getId_outlet() {
        return id_outlet;
    }

    public void setId_outlet(String id_outlet) {
        this.id_outlet = id_outlet;
    }

    public String getId_paket() {
        return id_paket;
    }

    public void setId_paket(String id_paket) {
        this.id_paket = id_paket;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


}
