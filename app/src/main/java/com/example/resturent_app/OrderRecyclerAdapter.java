package com.example.resturent_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.resturent_app.model.orderdetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class OrderRecyclerAdapter extends FirebaseRecyclerAdapter<orderdetails, OrderRecyclerAdapter.myViewHolder> {
    public OrderRecyclerAdapter(@NonNull FirebaseRecyclerOptions<orderdetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull orderdetails model) {


        holder.item.setText(""+model.getItem());
        holder.quantity.setText(""+model.getQuantity());

        holder.date.setText(""+model.getDate());

        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclelayout,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {

        ImageView img;
        TextView item,quantity,date;
        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);

            img = itemView.findViewById(R.id.itemimage);
            item = itemView.findViewById(R.id.Order_Name);
            quantity = itemView.findViewById(R.id.No_of_quantity);
            date = itemView.findViewById(R.id.orderdate);
        }
    }
}
