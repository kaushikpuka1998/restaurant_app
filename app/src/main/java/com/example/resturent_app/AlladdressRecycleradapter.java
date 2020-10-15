package com.example.resturent_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resturent_app.model.address;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AlladdressRecycleradapter extends FirebaseRecyclerAdapter<address,AlladdressRecycleradapter.myViewholder> {

    public AlladdressRecycleradapter(@NonNull FirebaseRecyclerOptions<address> options, Context applicationContext) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AlladdressRecycleradapter.myViewholder holder, int position, @NonNull address model) {

        holder.ad1.setText(""+model.getAddresslineone());
        holder.ad2.setText(""+model.getAddresslinetwo());
        holder.pinc.setText(""+model.getPincode());
        holder.phn.setText(""+model.getPhone());

    }

    @NonNull
    @Override
    public AlladdressRecycleradapter.myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_single_layout,parent,false);

        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder
    {
        TextView ad1,ad2,pinc,phn;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            ad1 = itemView.findViewById(R.id.addf);
            ad2 = itemView.findViewById(R.id.adds);
            phn = itemView.findViewById(R.id.phnview);
            pinc = itemView.findViewById(R.id.pind);

        }
    }
}
