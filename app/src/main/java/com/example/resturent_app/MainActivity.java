package com.example.resturent_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resturent_app.Retrofit.ApiInterface;
import com.example.resturent_app.Retrofit.RetrofitClient;
import com.example.resturent_app.model.Allmenu;
import com.example.resturent_app.model.FoodDatum;
import com.example.resturent_app.model.Popular;
import com.example.resturent_app.model.Recommended;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    ApiInterface apiInterface;
    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;
    RecyclerView popularRecyclerView,recommendedRecyclerView,Allmenurecycleview ;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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