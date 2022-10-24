package com.evans.kk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    TextView phone, email, name, number, collection, editing;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button logout;
    ProgressDialog loading;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        phone = findViewById(R.id.profile_phonenumber);
        email = findViewById(R.id.profile_emailaddress);
        name = findViewById(R.id.profile_name);
        number = findViewById(R.id.profile_ktdanumber);
        collection = findViewById(R.id.profile_collection);
        logout = findViewById(R.id.logoutuser);
        editing = findViewById(R.id.edit_profile);
        loading = new ProgressDialog(this);
        loading.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        editing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editing();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(a);
                finish();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent a = new Intent(ProfileActivity.this, Home.class);
                        startActivity(a);
                        finish();

                        break;
                    case R.id.action_profile:

                        break;
                    case R.id.action_feed:
                        Intent b = new Intent(ProfileActivity.this, Feed.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.action_more:
                        Intent bc = new Intent(ProfileActivity.this, Notifications.class);
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

    }

    private void editing() {
        setContentView(R.layout.update);
        Button update = findViewById(R.id.updatess);
        EditText names = findViewById(R.id.update_name);
        EditText collections = findViewById(R.id.update_collection);
        EditText phones = findViewById(R.id.update_mobile_Number);
        ImageView profile = findViewById(R.id.edit_profile_image);
        ImageView dp=findViewById(R.id.edit_profile_image);
        SharedPreferences shd = getSharedPreferences("pref", MODE_PRIVATE);
        String nam = shd.getString("name", "");
        String phon = shd.getString("phone", "");
        String colle = shd.getString("location", "");
        names.setText(nam);
        collections.setText(colle);
        phones.setText(phon);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = names.getText().toString().trim();
                String collection = collections.getText().toString().trim();
                String phone = phones.getText().toString().trim();
                if (name.isEmpty() || name.length() < 2) {
                    names.setError("invalid name");
                }
                if (phone.length() != 10 || phone.isEmpty()) {
                    phones.setError("invalid phone number");

                }
                if (collection.isEmpty() || collection.length() < 3) {
                    collections.setError("invalid collection centre");
                } else {
                    SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    String s1 = sh.getString("ktda_number", "");
                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("collection", collection);
                    map.put("full_name", name);
                    map.put("phone_number", phone);
                    map.put("profile_dp", phone);
                    databaseReference.child(s1).updateChildren(map);
                    loading.dismiss();
                    Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();


                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        loading.show();
        ss();
    }


    private void ss() {

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("ktda_number", "");

        loading.setTitle("Updating ");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        databaseReference.child(s1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    loading.dismiss();
                    String emaill = snapshot.child("email_address").getValue(String.class);
                    String naming = snapshot.child("full_name").getValue(String.class);
                    String phonenumber = snapshot.child("phone_number").getValue(String.class);
                    String location = snapshot.child("collection").getValue(String.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("location", location);
                    myEdit.putString("phone", phonenumber);
                    myEdit.putString("name", naming);

                    myEdit.commit();

                    email.setText(emaill);
                    name.setText(naming);
                    phone.setText(phonenumber);
                    collection.setText(location);
                    number.setText(s1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.dismiss();
            }
        });


    }
}