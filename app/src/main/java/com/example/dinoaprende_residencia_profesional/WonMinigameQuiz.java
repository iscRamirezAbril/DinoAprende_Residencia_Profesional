package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WonMinigameQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_minigame_quiz);

        TextView lblTotalPoints = findViewById(R.id.lblTotalPoints);
        int score = getIntent().getIntExtra("SCORE", 0);
        lblTotalPoints.setText("¡Has ganado " + score + " puntos!");

        Button btnPrincipalMenu = findViewById(R.id.btnPrincipalMenu);
        btnPrincipalMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WonMinigameQuiz.this, PrincipalMenu.class);
                startActivity(intent);
            }
        });
    }
}