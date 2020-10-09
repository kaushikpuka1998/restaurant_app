package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FoodActivity extends AppCompatActivity {


    String name,price,rating,imageUrl;
    TextView itemname,itemprice,itemrating;
    RatingBar ratingBar;
    Button addtocart;
    ImageView itempic,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();

        itemname = findViewById(R.id.fooditemname);
        itemprice = findViewById(R.id.foodItemprice);
        itemrating = findViewById(R.id.foodRating);
        itempic =  findViewById(R.id.fooditempic);
        ratingBar = findViewById(R.id.foodratingBar);
        addtocart = findViewById(R.id.FoodCartaddButton);
        back = findViewById(R.id.foodBackbutton);


        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");

        Glide.with(getApplicationContext()).load(imageUrl).into(itempic);
        itemname.setText(name);
        itemprice.setText(price);
        itemrating.setText(rating);
        ratingBar.setRating(Float.valueOf(rating));



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}