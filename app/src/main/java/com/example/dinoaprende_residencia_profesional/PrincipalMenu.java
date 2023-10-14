package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PrincipalMenu extends AppCompatActivity {
    ImageView imgUserProfilePicture;
    TextView lblUserName, lblScore;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_principal_menu);

        imgUserProfilePicture = findViewById(R.id.imgUserProfilePicture);
        lblUserName = findViewById(R.id.lblUserName);
        lblScore = findViewById(R.id.lblScore);

        db = new DatabaseHelper(this);
        loadUserInfo();

        ImageButton btnMathQuizMinigame = findViewById(R.id.btnQuizMinigame);
        ImageButton btnCardsMinigame = findViewById(R.id.btnCardMinigame);
        ImageButton btnPuzzleMinigame = findViewById(R.id.btnPuzzleMinigame);

        btnMathQuizMinigame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalMenu.this, InstructionsQuizMinigame.class);
                startActivity(intent);
            }
        });

        btnCardsMinigame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalMenu.this, InstructionsMemoryMinigame.class);
                startActivity(intent);
            }
        });

        btnPuzzleMinigame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalMenu.this, InstructionsPuzzleMinigame.class);
                startActivity(intent);
            }
        });
    }

    private void loadUserInfo() {
        Cursor cursor = db.getUserData();
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String profilePicture = cursor.getString(cursor.getColumnIndex(DatabaseHelper.UserProfileTable.COLUMN_PROFILE_PICTURE));
            @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.UserProfileTable.COLUMN_NAME));
            @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserProfileTable.COLUMN_SCORE));

            int resID = getResources().getIdentifier(profilePicture , "drawable", getPackageName());
            imgUserProfilePicture.setImageResource(resID);
            lblUserName.setText(userName);
            lblScore.setText("Puntos: " + String.valueOf(score));
        }
        cursor.close();
    }
}