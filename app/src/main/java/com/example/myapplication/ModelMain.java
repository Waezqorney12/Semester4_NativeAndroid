package com.example.myapplication;

//Model untuk pilihan paket
public class ModelMain {
    private int image;
    private String tittle;
    private String desc;
    private String price;

    public ModelMain(int image,String tittle,String desc,String price){
        this.image = image;
        this.tittle = tittle;
        this.desc = desc;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
