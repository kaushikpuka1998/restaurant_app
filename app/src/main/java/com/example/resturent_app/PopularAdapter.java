package com.example.resturent_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.resturent_app.model.Popular;


import java.util.List;

public class PopularAdapter  extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

     Context context;
     List<Popular> popularList;

    public PopularAdapter(Context context, List<Popular> popularList) {
        this.context = context;
        this.popularList = popularList;
    }


    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //Here need  a layout for recyclerview Adapter Call Times
        View view = LayoutInflater.from(context).inflate(R.layout.popular_recycler_items,parent,false);

        return new PopularViewHolder(view);
    }
    public static class PopularViewHolder extends RecyclerView.ViewHolder
    {
        ImageView popularImageview;
        TextView popularName;




        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            popularName = itemView.findViewById(R.id.Popularname);
            popularImageview= itemView.findViewById(R.id.Popular_image);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, final int position) {


        holder.popularName.setText(popularList.get(position).getName());


        Glide.with(context).load(popularList.get(position).getImageUrl()).into(holder.popularImageview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,FoodActivity.class);
                i.putExtra("name",popularList.get(position).getName());
                i.putExtra("price",popularList.get(position).getPrice());
                i.putExtra("rating",popularList.get(position).getRating());
                i.putExtra("image",popularList.get(position).getImageUrl());

                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }


}
