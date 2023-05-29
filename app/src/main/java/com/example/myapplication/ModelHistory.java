package com.example.myapplication;

public class ModelHistory {
    private int image;
    private String name;
    private String order_status;

    public ModelHistory(int image, String name, String order_status) {
        this.image = image;
        this.name = name;
        this.order_status = order_status;
    }

    public int getImage() {

        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNamaPaket() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
