package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MissMinigame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_minigame);

        Button btnTryAgain = findViewById(R.id.btnTryAgain);
        Button btnPrincipalMenu = findViewById(R.id.btnPrincipalMenu);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissMinigame.this, MathQuizMenu.class);
                startActivity(intent);
            }
        });

        btnPrincipalMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissMinigame.this, PrincipalMenu.class);
                startActivity(intent);
            }
        });
    }
}