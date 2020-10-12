package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.resturent_app.Retrofit.ApiInterface;
import com.example.resturent_app.Retrofit.RetrofitClient;
import com.example.resturent_app.model.Allmenu;
import com.example.resturent_app.model.FoodDatum;
import com.example.resturent_app.model.Popular;
import com.example.resturent_app.model.Recommended;
import com.facebook.AccessToken;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    ApiInterface apiInterface;
    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;
    CircularImageView profile;
    RecyclerView popularRecyclerView,recommendedRecyclerView,Allmenurecycleview ;
    EditText search;

    String Username;
    GoogleSignInClient mgoogleSignInClient;

    FirebaseAuth firebaseAuth;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        firebaseAuth = FirebaseAuth.getInstance();




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mgoogleSignInClient = GoogleSignIn.getClient(this,gso);




        profile = findViewById(R.id.cart);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(g);

            }
        });
        GoogleSignInAccount gacc = GoogleSignIn.getLastSignedInAccount(this);




        if(gacc!=null)
        {
            Username = gacc.getDisplayName();
            Uri photourl = gacc.getPhotoUrl();

            Glide.with(this).load(String.valueOf(photourl)).into(profile);
        }else
        {

                if(isLoggedIn)
                {

                    String photourl =  "http://graph.facebook.com/"+accessToken.getUserId() +"/picture?type=large";
                    Picasso.get().load(photourl).into(profile);
                }else
                {
                    Intent y = new Intent(getApplicationContext(),LoginPanelActivity.class);
                    startActivity(y);
                }

        }

        search = (EditText)findViewById(R.id.searchbar);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<List<FoodDatum>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<FoodDatum>>() {
            @Override
            public void onResponse(Call<List<FoodDatum>> call, Response<List<FoodDatum>> response) {


                List<FoodDatum> foodDataList = response.body();

                getRecommendedData(foodDataList.get(0).getRecommended());
                getPopulateData(foodDataList.get(0).getPopular());
                getAllmenuData(foodDataList.get(0).getAllmenu());


            }

            @Override
            public void onFailure(Call<List<FoodDatum>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Server is not Responding", Toast.LENGTH_SHORT).show();
            }
        });
    }








    private  void getPopulateData(List<Popular> popularList)
    {
        popularRecyclerView = findViewById(R.id.recyclerView);
        popularAdapter = new PopularAdapter(this,popularList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);
    }


    private  void getRecommendedData(List<Recommended> recommendedList)
    {
        recommendedRecyclerView = findViewById(R.id.recomrecyclerView);
        recommendedAdapter= new RecommendedAdapter(this,recommendedList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);
    }

    private  void getAllmenuData(List<Allmenu> allmenuList)
    {
        Allmenurecycleview = findViewById(R.id.allmenurecycle);
        allMenuAdapter= new AllMenuAdapter(this,allmenuList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        Allmenurecycleview.setLayoutManager(layoutManager);
        Allmenurecycleview.setAdapter(allMenuAdapter);
    }



}