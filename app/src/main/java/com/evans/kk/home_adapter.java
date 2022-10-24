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

public class home_adapter extends RecyclerView.Adapter<home_adapter.MyViewHolder> {
    DatabaseReference databaseReference;
    Context context;
    ArrayList<Model_data> list;

    public home_adapter(Context context, ArrayList<Model_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.last, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SharedPreferences sharedPref = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String ktda_number = sharedPref.getString("ktda_number", null);

        SharedPreferences shd = context.getSharedPreferences("pref", MODE_PRIVATE);
        String nam = shd.getString("name", "");
        String colle = shd.getString("location", "");
        Model_data model_data = list.get(position);
        Picasso.get()
                .load(model_data.getImageUrl())
                .placeholder(R.drawable.ktdalogo)
                .fit()
                .centerCrop()
                .into(holder.mimagee);
        String bb= String.valueOf(model_data.getDate());
        String cbb= String.valueOf(model_data.getyValue());
        holder.mcollection.setText(colle);
        holder.mname.setText(nam);
        holder.mquantity.setText(cbb+"Kg");
        holder.date.setText(bb);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mquantity, mcollection, mname,date;
        ImageView mimagee;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mimagee=itemView.findViewById(R.id.last_image);
            mquantity=itemView.findViewById(R.id.last_quantity);
            mname=itemView.findViewById(R.id.last_name);
            date=itemView.findViewById(R.id.last_date);
            mcollection=itemView.findViewById(R.id.last_collection);

        }
    }
}
