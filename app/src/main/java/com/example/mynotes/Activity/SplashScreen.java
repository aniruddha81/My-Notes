package com.example.mynotes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotes.MainActivity;
import com.example.mynotes.databinding.ActivitySplashScreenBinding;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000); // 2 seconds delay
    }
}