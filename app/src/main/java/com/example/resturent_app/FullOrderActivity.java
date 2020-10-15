package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FullOrderActivity extends AppCompatActivity {


    TextView orno,am,gate,address,tot,name,qnt,itemname,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_order);

        orno = findViewById(R.id.Fullorderno);
        am = findViewById(R.id.FullOrderAmount);
        gate = findViewById(R.id.fullOrderGateway);
        address = findViewById(R.id.fullOrderAddress);
        tot = findViewById(R.id.FullOrderAmount);
        name = findViewById(R.id.fullOrdername);
        qnt = findViewById(R.id.FullOrderquantity);
        date =findViewById(R.id.fullOrderDate);

        Intent u = getIntent();

        orno.setText(u.getStringExtra("orderID"));

        am.setText(u.getStringExtra("tot"));

        gate.setText(u.getStringExtra("gate"));

        address.setText(u.getStringExtra("add"));

        qnt.setText(u.getStringExtra("qua"));

        date.setText(u.getStringExtra("date"));

        name.setText(u.getStringExtra("name"));







    }
}