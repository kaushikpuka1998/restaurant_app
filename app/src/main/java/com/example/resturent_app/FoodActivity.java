package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FoodActivity extends AppCompatActivity {


    String name,price,rating,imageUrl;
    TextView itemname,itemprice,itemrating;
    RatingBar ratingBar;
    Button addtocart;
    ImageView itempic,back;

    FirebaseAuth firebaseAuth;
    GoogleSignInAccount gacc;//for google
    AccessToken accessToken;//for facebook
    FirebaseUser firebaseUser;//For phone sign

    FirebaseDatabase mDatabase;




    GoogleSignInClient mgoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        accessToken = AccessToken.getCurrentAccessToken();
        final boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        mDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        gacc = GoogleSignIn.getLastSignedInAccount(this);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mgoogleSignInClient = GoogleSignIn.getClient(this,gso);

        final Intent intent = getIntent();

        itemname = findViewById(R.id.fooditemname);
        itemprice = findViewById(R.id.foodItemprice);
        itemrating = findViewById(R.id.foodRating);
        itempic =  findViewById(R.id.fooditempic);
        ratingBar = findViewById(R.id.foodratingBar);
        addtocart = findViewById(R.id.foodaddtocart);
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

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FoodActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FoodActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottomsheetdialog);
                bottomSheetDialog.show();//If  .show not used then bottomsheet will not come
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                final TextView bottomitemname = bottomSheetDialog.findViewById(R.id.bottomitemname);
                final TextView bottomprice = bottomSheetDialog.findViewById(R.id.bottomprice);
                ImageButton bottomplus =  bottomSheetDialog.findViewById(R.id.bottomplus);
                ImageButton bottomminus =  bottomSheetDialog.findViewById(R.id.bottomminus);
                final TextView bottomcount =bottomSheetDialog.findViewById(R.id.bottomcount);

                final TextView bottomsheet_grantotal = bottomSheetDialog.findViewById(R.id.bottomsheet_grantotal);

                Button pay_here = bottomSheetDialog.findViewById(R.id.bottom_pay_here);

                bottomitemname.setText(intent.getStringExtra("name"));
                bottomprice.setText(intent.getStringExtra("price"));
                bottomsheet_grantotal.setText(intent.getStringExtra("price"));

                bottomplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(FoodActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        String count = bottomcount.getText().toString();

                        String itemvalue =  intent.getStringExtra("price");
                        int itempvalint = Integer.parseInt(itemvalue);
                        int cnt = Integer.parseInt(count);
                        if(cnt<=6)
                        {
                            cnt++;
                            int tot =(cnt*itempvalint);
                            bottomcount.setText(String.valueOf(cnt));
                            //System.out.println(tot);
                            bottomsheet_grantotal.setText(String.valueOf(tot));
                        }else
                        {
                            Toast.makeText(FoodActivity.this, "You can Buy Highest 7 Quantity", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomminus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(FoodActivity.this, "Discarded One", Toast.LENGTH_SHORT).show();
                        String count = bottomcount.getText().toString();

                        String itemvalue =  intent.getStringExtra("price");
                        int itempvalint = Integer.parseInt(itemvalue);
                        int cnt = Integer.parseInt(count);
                        if(cnt>=2)
                        {
                            cnt--;
                            int tot =(cnt*itempvalint);
                            bottomcount.setText(String.valueOf(cnt));
                            //System.out.println(tot);
                            bottomsheet_grantotal.setText(String.valueOf(tot));
                        }else
                        {
                            Toast.makeText(FoodActivity.this, "You have to Buy Atleast 1 Quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                String userid ="";
                if(isLoggedIn)//Facebook done
                {
                    userid = accessToken.getUserId();
                }
                else if(firebaseUser!=null)//Phone
                {
                    userid = firebaseUser.getUid();
                }
                else if(gacc!=null)
                {
                    userid = gacc.getId();
                }
                final String finalUserid = userid;
                pay_here.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent u = new Intent(getApplicationContext(),Payment_Gateway_activity.class);
                        u.putExtra("val",bottomsheet_grantotal.getText().toString());
                        u.putExtra("item",bottomitemname.getText().toString());
                        u.putExtra("Quantity:",bottomcount.getText().toString());
                        u.putExtra("Username", finalUserid);
                        u.putExtra("Image",imageUrl);
                        startActivity(u);
                    }
                });









            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}