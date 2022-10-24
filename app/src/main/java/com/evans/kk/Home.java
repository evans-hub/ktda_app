package com.evans.kk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    Query reference;
    GraphView graphView;
    LineGraphSeries series;
    ImageView imageView;
    TextView name,cumulative,today;
    DatabaseReference reference_data;
    home_adapter adapter;
    ArrayList<Model_data> list;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RecyclerView recyclerVieww=findViewById(R.id.last_recyclervieww);
        graphView=findViewById(R.id.graph);
        series=new LineGraphSeries();
        graphView.addSeries(series);
        name=findViewById(R.id.profile_name);
        imageView=findViewById(R.id.notification);
        cumulative=findViewById(R.id.cumulate);
        today=findViewById(R.id.todd);
        BottomNavigationView navigationView=findViewById(R.id.navigation);
        reference= FirebaseDatabase.getInstance().getReference("dataset").orderByChild("xValue").limitToLast(6);
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(7);
        graphView.getViewport().setMinY(0);
        graphView.getGridLabelRenderer().setTextSize(30);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Time(Last 7 Days)");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Weight(kgs)");
        SharedPreferences shd = getSharedPreferences("pref", MODE_PRIVATE);
        String nam = shd.getString("name", "");
        name.setText(nam);
        reference_data= FirebaseDatabase.getInstance().getReference("high");
        recyclerVieww.setHasFixedSize(true);
        recyclerVieww.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new home_adapter(this,list);
        recyclerVieww.setAdapter(adapter);
        recyclerVieww.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    navigationView.setVisibility(View.VISIBLE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 ||dy<0 && navigationView.isShown())
                {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });
        reference_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    Model_data model_data = dataSnapshot.getValue(Model_data.class);
                    list.add(model_data);

                }

                adapter.notifyDataSetChanged();
                double sum=getSum();
                cumulative.setText(String.valueOf(sum)+"Kgs");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        break;
                    case R.id.action_profile:
                        Intent a = new Intent(Home.this,ProfileActivity.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.action_feed:
                        Intent b = new Intent(Home.this,Feed.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.action_more:
                        Intent bc= new Intent(Home.this, Notifications.class);
                        startActivity(bc);
                        finish();
                        break;
                }
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bc= new Intent(Home.this, Notifications.class);
                startActivity(bc);
                finish();
            }
        });



    }

    private double getSum() {
        double sum = 0;
        for( int i = 0; i < list.size(); i++)
        {
            sum += list.get(i).getyValue();
        }
        return sum;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataPoint[] dp=new DataPoint[(int) snapshot.getChildrenCount()];
                int index=0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PointValue pointValue=dataSnapshot.getValue(PointValue.class);
                    dp[index]=new DataPoint(pointValue.getxValue(),pointValue.getyValue());

                    index++;
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}