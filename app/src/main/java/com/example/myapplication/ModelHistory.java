package com.example.myapplication;

public class ModelHistory {
    private int image;
    private String name;
    private String order_status;
    private String price;

    public ModelHistory(int image, String name, String order_status,String price) {
        this.image = image;
        this.name = name;
        this.order_status = order_status;
        this.price = price;
    }

    public int getImage() {

        return image;
    }



    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
