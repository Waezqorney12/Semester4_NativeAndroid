package com.example.myapplication;

public class ModelHistory {
    private OutletData id_outlet_data;
    private String kd_invoice;
    private String status;
    private String tanggal_pesan;

    public ModelHistory(OutletData id_outlet_data, String kd_invoice, String status, String tanggal_pesan) {
        this.id_outlet_data = id_outlet_data;
        this.kd_invoice = kd_invoice;
        this.status = status;
        this.tanggal_pesan = tanggal_pesan;
    }

    public OutletData getId_outlet_data() {
        return id_outlet_data;
    }

    public void setId_outlet_data(OutletData id_outlet_data) {
        this.id_outlet_data = id_outlet_data;
    }

    public String getKd_invoice() {
        return kd_invoice;
    }

    public void setKd_invoice(String kd_invoice) {
        this.kd_invoice = kd_invoice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal_pesan() {
        return tanggal_pesan;
    }

    public void setTanggal_pesan(String tanggal_pesan) {
        this.tanggal_pesan = tanggal_pesan;
    }

    public class OutletData {
        private String nama;
        public OutletData(String nama) {

            this.nama = nama;

        }
        public String getNama() {
            return nama;
        }
        public void setNama(String nama) {
            this.nama = nama;
        }
    }
}

