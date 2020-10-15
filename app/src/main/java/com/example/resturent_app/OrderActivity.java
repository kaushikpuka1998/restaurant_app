package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.resturent_app.model.orderdetails;
import com.facebook.AccessToken;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {


    RecyclerView recyclerViewl;
    ImageView back;
    String userid;
    AccessToken accessToken;
    FirebaseUser firebaseUser;
    GoogleSignInAccount gacc;
    OrderRecyclerAdapter orderRecyclerAdapter;

    @Override
    protected void onStop() {
        super.onStop();
        orderRecyclerAdapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerViewl = findViewById(R.id.recyclerViewoforder);
        back = findViewById(R.id.Orderback);

        recyclerViewl.setLayoutManager(new LinearLayoutManager(this));



        accessToken = AccessToken.getCurrentAccessToken();//Facebook
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();//For Mobile


        gacc = GoogleSignIn.getLastSignedInAccount(this);//for Google
        if(gacc!=null)
        {

            userid  = gacc.getId();//for google


        }else if(accessToken!=null && !accessToken.isExpired())
        {
            userid =accessToken.getUserId();//for facebook

        }else if(firebaseUser!=null)
        {
            userid = firebaseUser.getUid();//for Phone
        }


        FirebaseRecyclerOptions<orderdetails> options =
                new FirebaseRecyclerOptions.Builder<orderdetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").child(userid), orderdetails.class)
                        .build();


        orderRecyclerAdapter = new OrderRecyclerAdapter(options,getApplicationContext());
        recyclerViewl.setItemAnimator(new DefaultItemAnimator());

        recyclerViewl.setAdapter(orderRecyclerAdapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();
        orderRecyclerAdapter.startListening();
    }




}