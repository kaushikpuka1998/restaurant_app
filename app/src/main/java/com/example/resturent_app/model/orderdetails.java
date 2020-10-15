package com.example.resturent_app.model;

public class orderdetails {

     String Item;
    String Image;
    String Quantity;
    String Date ;
    String OrderID;
    String Name;
    String Gateway;
    String Totalamount;
    String Address;

    public orderdetails()
    {

    }

    public orderdetails(String item, String image, String quantity, String date, String orderID, String name, String gateway, String totalamount, String address) {
        Item = item;
        Image = image;
        Quantity = quantity;
        Date = date;
        OrderID = orderID;
        Name = name;
        Gateway = gateway;
        Totalamount = totalamount;
        Address = address;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getGateway() {
        return Gateway;
    }

    public void setGateway(String gateway) {
        Gateway = gateway;
    }

    public String getTotalamount() {
        return Totalamount;
    }

    public void setTotalamount(String totalamount) {
        Totalamount = totalamount;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
