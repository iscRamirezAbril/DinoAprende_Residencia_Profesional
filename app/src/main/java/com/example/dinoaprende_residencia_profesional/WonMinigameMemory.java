package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class WonMinigameMemory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_won_minigame_memory);

        TextView lblTotalPoints = findViewById(R.id.lblTotalPoints);
        int score = getIntent().getIntExtra("SCORE", 0);
        lblTotalPoints.setText("¡Has ganado " + score + " puntos!");

        Button btnPrincipalMenu = findViewById(R.id.btnPrincipalMenu);
        btnPrincipalMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WonMinigameMemory.this, PrincipalMenu.class);
                startActivity(intent);
            }
        });
    }
}