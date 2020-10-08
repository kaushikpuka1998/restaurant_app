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
import com.example.resturent_app.model.Recommended;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Recommended> recommendedList;

    public RecommendedAdapter(Context context, List<Recommended> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }


    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended_recycler_items,parent,false);

        return new RecommendedViewHolder(view);

    }

    public static class RecommendedViewHolder extends  RecyclerView.ViewHolder {
        ImageView recommendedImage;
        TextView  recommendedItemName,recommendedRating,RecommendedDeliveryTime,Recommendedcharges,recommendedprize;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);

            recommendedImage = itemView.findViewById(R.id.recommendedItemPic);
            recommendedItemName = itemView.findViewById(R.id.recommendedItem);
            recommendedRating = itemView.findViewById(R.id.RecommendedRating);
            RecommendedDeliveryTime = itemView.findViewById(R.id.recommended_deliverytime);
            Recommendedcharges = itemView.findViewById(R.id.recommended_deliv_charges);
            recommendedprize = itemView.findViewById(R.id.RecommendedPrice);




        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {

        holder.recommendedItemName.setText(recommendedList.get(position).getName());
        holder.recommendedRating.setText(recommendedList.get(position).getRating());
        holder.RecommendedDeliveryTime.setText(recommendedList.get(position).getDeliveryTime());
        holder.recommendedprize.setText(recommendedList.get(position).getPrice());
        holder.Recommendedcharges.setText(recommendedList.get(position).getDeliveryCharges());
        Glide.with(context).load(recommendedList.get(position).getImageUrl()).into(holder.recommendedImage);



    }


    @Override
    public int getItemCount() {
        return recommendedList.size();
    }


}
