package com.evans.kk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private Button regist;
    private EditText email_address;
    private EditText phones;
    private EditText names;
    private EditText passwords;
    private EditText numbers;
    private EditText collect;
    private FirebaseAuth mAuth;
    ProgressDialog loading;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_design);
        regist = findViewById(R.id.register);
        login = findViewById(R.id.loginfromsignup);
        loading = new ProgressDialog(this);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        phones = findViewById(R.id.layout_mobile_Number);
        names = findViewById(R.id.layout_name);
        passwords = findViewById(R.id.layout_password);
        email_address = findViewById(R.id.layout_email);
        numbers = findViewById(R.id.layout_ktda_number);
        collect = findViewById(R.id.layout_collection);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }
    }

    private void registerUser() {

        loading.setTitle("Signing Up");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        String email, password, phone, name, number,collection;
        email = email_address.getText().toString().trim();
        password = passwords.getText().toString().trim();
        phone = phones.getText().toString().trim();
        name = names.getText().toString().trim();
        number = numbers.getText().toString().trim();
        collection = collect.getText().toString().trim();
        if (name.isEmpty() || name.length() < 2) {
            names.setError("invalid name");
        }
        if (password.isEmpty() || password.length() < 6) {
            passwords.setError("6 Characters and More Required");

        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_address.setError("wrong email address");


        }
        if (phone.length() != 10 || phone.isEmpty()) {
            phones.setError("invalid phone number");

        }
        if (number.isEmpty() || number.length() < 3) {
            numbers.setError("invalid ktda number");
        } else {
            loading.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String uuid = FirebaseAuth.getInstance().getCurrentUser()
                                .getUid();
                        User user = new User(number, name, phone,collection,email, uuid);

                        FirebaseDatabase.getInstance().getReference("users").child(number)
                                .setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent
                                                = new Intent(Registration.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(
                                        getApplicationContext(),
                                        "Registration failed!!"
                                                + " Please try again later" + message,
                                        Toast.LENGTH_LONG)
                                .show();

                    }

                }
            });
        }


    }


}