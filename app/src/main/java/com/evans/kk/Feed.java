package com.evans.kk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Feed extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private TextView collection;
    DatabaseReference reference,databaseReference;
    MyAdapter adapter;
    ArrayList<Model_fertilizer> list;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }
        collection=findViewById(R.id.collect);
progress=findViewById(R.id.prog);
progress.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.recycler);
        reference= FirebaseDatabase.getInstance().getReference("fertilizers");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new MyAdapter(this,list);
        recyclerView.setAdapter(adapter);
        SharedPreferences shd = getSharedPreferences("pref", MODE_PRIVATE);
        String nam = shd.getString("name", "");
        TextView name=findViewById(R.id.profile_name);
        name.setText(nam);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progress.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    Model_fertilizer modelFertilizer = dataSnapshot.getValue(Model_fertilizer.class);
                    list.add(modelFertilizer);
                }
                    adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progress.setVisibility(View.GONE);
                Toast.makeText(Feed.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("ktda_number", "");
        databaseReference=FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(s1).child("collection").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String centre=snapshot.getValue(String.class);
                collection.setText(centre);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Feed.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent a = new Intent(Feed.this, Home.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.action_profile:
                        Intent b = new Intent(Feed.this, ProfileActivity.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.action_feed:


                        break;
                    case R.id.action_more:

                        Intent c = new Intent(Feed.this, Notifications.class);
                        startActivity(c);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

}