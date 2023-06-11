package com.example.myapplication;

public class ModelSubmitTransaction {
    private int id_outlet;
    private int total_pesanan;

    public ModelSubmitTransaction(int id_outlet, int total_pesanan) {
        this.id_outlet = id_outlet;
        this.total_pesanan = total_pesanan;
    }

    public int getId_outlet() {
        return id_outlet;
    }

    public void setId_outlet(int id_outlet) {
        this.id_outlet = id_outlet;
    }

    public int getTotal_pesanan() {
        return total_pesanan;
    }

    public void setTotal_pesanan(int total_pesanan) {
        this.total_pesanan = total_pesanan;
    }
}
