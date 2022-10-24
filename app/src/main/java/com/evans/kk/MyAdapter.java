package com.evans.kk;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    DatabaseReference databaseReference;
    Context context;
    ArrayList<Model_fertilizer> list;

    public MyAdapter(Context context, ArrayList<Model_fertilizer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SharedPreferences sharedPref = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String ktda_number = sharedPref.getString("ktda_number", null);
        Model_fertilizer modelFertilizer = list.get(position);
        holder.mname.setText(modelFertilizer.getFertilizerName());
        holder.mprice.setText("Sh." + modelFertilizer.getFertilizerPrice());
        holder.mquantity.setText(modelFertilizer.getFertilizerQuantity() + "Kgs");
        holder.quantityaddedToCart.setText(modelFertilizer.getQuantityOrdered());
        Picasso.get()
                .load(modelFertilizer.getFertilizerImage())
                .placeholder(R.drawable.ktdalogo)
                .fit()
                .centerCrop()
                .into(holder.mimage);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("cart").child("fertilizerid");
                databaseReference.child(ktda_number).child(modelFertilizer.getFertilizerId()).removeValue();
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("cart").child("fertilizerid");
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("fertilizerName", modelFertilizer.getFertilizerName());
                cartMap.put("fertilizerImage", modelFertilizer.getFertilizerImage());
                cartMap.put("fertilizerId", modelFertilizer.getFertilizerId());
                cartMap.put("fertilizerQuantity", modelFertilizer.getFertilizerQuantity());
                cartMap.put("fertilizerPrice", modelFertilizer.getFertilizerPrice());
                cartMap.put("quantityOrdered", modelFertilizer.getQuantityOrdered());
                cartMap.put("Total", modelFertilizer.getFertilizerPrice());
                databaseReference.child(ktda_number).child(modelFertilizer.getFertilizerId()).updateChildren(cartMap);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mquantity, mprice, mname, remove, add, quantityaddedToCart;
        ImageView mimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mquantity = itemView.findViewById(R.id.quantityl);
            mprice = itemView.findViewById(R.id.item_pricel);
            mname = itemView.findViewById(R.id.item_namel);
            mimage = itemView.findViewById(R.id.product_imagel);
            remove = itemView.findViewById(R.id.remove_item);
            add = itemView.findViewById(R.id.add_item);
            quantityaddedToCart = itemView.findViewById(R.id.item_amount);

        }
    }
}
