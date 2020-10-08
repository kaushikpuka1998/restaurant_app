package com.example.resturent_app.Retrofit;

import com.example.resturent_app.model.FoodDatum;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("fooddata.json")
    Call<List<FoodDatum>>getAllData();
}
