package com.example.resturent_app.model;

import androidx.annotation.NonNull;

public class address {
    String Addresslineone,Addresslinetwo,Pincode,Phone;

    public address() {
    }

    public address(String addresslineone, String addresslinetwo, String pincode, String phone) {
        Addresslineone = addresslineone;
        Addresslinetwo = addresslinetwo;
        Pincode = pincode;
        Phone = phone;
    }

    public String getAddresslineone() {
        return Addresslineone;
    }

    public void setAddresslineone(String addresslineone) {
        Addresslineone = addresslineone;
    }

    public String getAddresslinetwo() {
        return Addresslinetwo;
    }

    public void setAddresslinetwo(String addresslinetwo) {
        Addresslinetwo = addresslinetwo;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return Addresslineone+","+Addresslinetwo+",Pincode:"+Pincode+"\n"+"Phone:"+Phone;
    }
}
