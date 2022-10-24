package com.evans.kk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Notifications extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager=getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
Intent intent=new Intent(Notifications.this,Home.class);
startActivity(intent);
                        finish();
                        break;
                    case R.id.action_profile:
                        Intent intent2=new Intent(Notifications.this,ProfileActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.action_feed:
                        Intent intent3=new Intent(Notifications.this,Feed.class);
                        startActivity(intent3);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(Notifications.this,"My Notification");
        builder.setContentTitle("My title");
        builder.setContentText("Hello this is me");
        builder.setSmallIcon(R.drawable.ktdalogo);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(Notifications.this);
        managerCompat.notify(1,builder.build());
    }
}