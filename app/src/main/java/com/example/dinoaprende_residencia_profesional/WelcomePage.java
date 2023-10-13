package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {
    TextView lblUserName, lblScore;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_page);

        ImageButton btnAbout = findViewById(R.id.btnAbout);
        ImageButton btnFeedback = findViewById(R.id.btnFeddback);
        Button btnStart = findViewById(R.id.btnStart);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, AboutThisApp.class);
                startActivity(intent);
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, FeedbackForm.class);
                startActivity(intent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("DinoAprende", MODE_PRIVATE);
                boolean isUserRegistered = sharedPreferences.getBoolean("isUserRegistered", false);

                if (isUserRegistered) {
                    // Si el usuario ya está registrado, redirigir a PrincipalMenu
                    Intent intent = new Intent(WelcomePage.this, PrincipalMenu.class);
                    startActivity(intent);
                } else {
                    // Si el usuario aún no está registrado, redirigir a RegisterForm
                    Intent intent = new Intent(WelcomePage.this, RegisterForm.class);
                    startActivity(intent);
                }
            }
        });
    }
}