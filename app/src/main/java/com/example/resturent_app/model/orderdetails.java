package com.example.resturent_app.model;

public class orderdetails {

     String Item;
    String Image;
    String Quantity;
    String Date ;

    public orderdetails()
    {

    }

    public orderdetails(String item, String image, String quantity, String date) {
        this.Item = item;
        this.Image = image;
        this.Quantity = quantity;
        this.Date = date;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        this.Item = item;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        this.Quantity = quantity;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }
}
