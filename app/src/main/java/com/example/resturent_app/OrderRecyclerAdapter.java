package com.example.resturent_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.resturent_app.model.orderdetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class OrderRecyclerAdapter extends FirebaseRecyclerAdapter<orderdetails, OrderRecyclerAdapter.myViewHolder> {

    Context context;

    public OrderRecyclerAdapter(@NonNull FirebaseRecyclerOptions<orderdetails> options,Context context) {
        super(options);
        this.context = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull orderdetails model) {


        holder.item.setText(""+model.getItem());
        holder.quantity.setText(""+model.getQuantity());

        holder.date.setText(""+model.getDate());

        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);

        final String name =  model.getItem();
        final String qua = model.getQuantity();
        final String Date = model.getDate();
        final String orderID = model.getOrderID();
        final String deliver = model.getName();
        final String gate = model.getGateway();
        final String tot = model.getTotalamount();
        final String add = model.getAddress();
        final String ownername = model.getName();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent g = new Intent(context,FullOrderActivity.class);
                g.putExtra("item",name);
                g.putExtra("qua",qua);
                g.putExtra("date",Date);
                g.putExtra("orderID",orderID);
                g.putExtra("deliver",deliver);
                g.putExtra("gate",gate);
                g.putExtra("tot",tot);
                g.putExtra("add",add);
                g.putExtra("name",ownername);

                context.startActivity(g);
            }
        });
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
