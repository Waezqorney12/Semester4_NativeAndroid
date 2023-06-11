package com.example.myapplication;

public class ModelDetailTransaction {
    private int id_transaksi;
    private int id_user;
    private int subtotal;

    public ModelDetailTransaction(int id_transaksi, int id_user, int subtotal) {
        this.id_transaksi = id_transaksi;
        this.id_user = id_user;
        this.subtotal = subtotal;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}

