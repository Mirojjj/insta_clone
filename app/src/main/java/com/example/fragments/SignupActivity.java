package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragments.database.AppDatabase;
import com.example.fragments.database.entities.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignupActivity extends AppCompatActivity {
    EditText username, eEmail, ePassword;
    Button singUpBtn;
    AppDatabase appDb;

    ExecutorService executorService = Executors.newSingleThreadExecutor(); //executes single thread on bg
//    ExecutorService executorService = Executors.newFixedThreadPool(3);  execute three threads on background
//ExecutorService executorService = Executors.newCachedThreadPool(); executes thread dynamically on bg when we dont know how many threads are there.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.edit_username_signup);
        eEmail = findViewById(R.id.email_singup);
        ePassword = findViewById(R.id.password_signup);
        singUpBtn = findViewById(R.id.btn_signup);

        appDb = AppDatabase.getInstance(getApplicationContext());

        appDb.userDao().getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // Log it in a background thread
                executorService.execute(() -> {
                    for (User u : users) {
                        Log.d("UserData", "Username: " + u.username + ", Email: " + u.email + ", Password: " + u.password);
                    }
                });
            }
        });

        singUpBtn.setOnClickListener(v->{
            String name = username.getText().toString().trim();
            String email = eEmail.getText().toString().trim();
            String password = ePassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.username = name;
            user.email = email;
            user.password = password;

            executorService.execute(()->{
                appDb.userDao().insert(user);

                runOnUiThread(()->{
                    Toast.makeText(SignupActivity.this, "User registered!", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                });
            });

        });



    }
}