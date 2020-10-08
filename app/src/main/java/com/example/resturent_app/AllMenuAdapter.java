package com.example.resturent_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.resturent_app.model.Allmenu;

import java.util.List;

public class AllMenuAdapter extends RecyclerView.Adapter<AllMenuAdapter.AllmenuViewHolder> {


    Context context;
    List<Allmenu> allmenuList;

    public AllMenuAdapter(Context context, List<Allmenu> allmenuList) {
        this.context = context;
        this.allmenuList = allmenuList;
    }

    @NonNull
    @Override
    public AllmenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.allmenu_recycler_items,parent,false);

        return new AllMenuAdapter.AllmenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllmenuViewHolder holder, int position) {
            holder.allmenuItemName.setText(allmenuList.get(position).getName());
            holder.Allmenuitemnote.setText(allmenuList.get(position).getNote());
            holder.allmenuRating.setText(allmenuList.get(position).getRating());
            holder.Allmenudeliverytime.setText(allmenuList.get(position).getDeliveryTime());
            holder.allmenucharges.setText(allmenuList.get(position).getDeliveryCharges());
            holder.allmenuPrices.setText(allmenuList.get(position).getPrice());


            Glide.with(context).load(allmenuList.get(position).getImageUrl()).into(holder.allmenuImage);
    }

    @Override
    public int getItemCount() {
        return allmenuList.size() ;
    }

    public static class AllmenuViewHolder extends  RecyclerView.ViewHolder
    {

        TextView allmenuItemName,Allmenuitemnote,allmenuRating,Allmenudeliverytime,allmenucharges,allmenuPrices;
        ImageView allmenuImage;
        public AllmenuViewHolder(@NonNull View itemView) {
            super(itemView);

            allmenuItemName = itemView.findViewById(R.id.AllmenuItemName);
            Allmenuitemnote = itemView.findViewById(R.id.Allmenu_note);
            allmenuRating = itemView.findViewById(R.id.Allmenurating);
            Allmenudeliverytime = itemView.findViewById(R.id.AllmenuDeliverytime);
            allmenucharges = itemView.findViewById(R.id.allmenudeliverycharge);
            allmenuPrices = itemView.findViewById(R.id.AllmenuPrice);

            allmenuImage = itemView.findViewById(R.id.allmenuitempic);

        }
    }
}
