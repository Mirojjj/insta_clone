package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragments.database.AppDatabase;
import com.example.fragments.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    EditText lUsername, lPassword;

    AppDatabase appDb;

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lUsername = findViewById(R.id.username_login);
        lPassword = findViewById(R.id.password_login);

        appDb = AppDatabase.getInstance(getApplicationContext());

        Button login_btn = findViewById(R.id.login_btn);
        TextView txtView_signup = findViewById(R.id.signup);
        txtView_signup.setClickable(true);
        login_btn.setOnClickListener(v-> {


            String identifier = lUsername.getText().toString().trim();
            String password = lPassword.getText().toString().trim();

            if(identifier.isEmpty() || password.isEmpty()){
                Toast.makeText(MainActivity.this, "Please fill all the fields" , Toast.LENGTH_SHORT).show();
                return;
            }

            executorService.execute(()->{
                User user = appDb.userDao().login(identifier, password);
                runOnUiThread(()->{
                    if(user != null && user.password.equals((password))){
                        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            });

        });

        txtView_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent signupIntent = new Intent( MainActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}