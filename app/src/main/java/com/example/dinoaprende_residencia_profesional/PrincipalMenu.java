package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.ColorDrawable;

public class PrincipalMenu extends AppCompatActivity {
    ImageView imgUserProfilePicture;
    TextView lblUserName, lblScore;
    DatabaseHelper db;
    private SharedPreferences sharedPreferences;
    private MediaPlayer soundPlayer;
    private MediaPlayer backgroundMusicPlayer;

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
        ImageButton btnDinoFriends = findViewById(R.id.btnDinoFriends);

        sharedPreferences = getSharedPreferences("DinoPrefs", MODE_PRIVATE);

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

        btnDinoFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalMenu.this, DinoFriendsMenu.class);
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

            int resID = getResources().getIdentifier(profilePicture, "drawable", getPackageName());
            imgUserProfilePicture.setImageResource(resID);
            lblUserName.setText(userName);
            lblScore.setText("Puntos: " + String.valueOf(score));
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = db.getUserData();
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserProfileTable.COLUMN_SCORE));
            for (int i = 1; i <= 30; i++) {
                DinoFriend dino = dbHelper.getDinoFriendFromDatabase(i);
                if (dino != null) {
                    int dinoRequiredScore = dino.getDinoScore();

                    boolean wasLocked = sharedPreferences.getBoolean("DinoLocked" + i, true);
                    boolean isNowUnlocked = score >= dinoRequiredScore;

                    if (wasLocked && isNowUnlocked) {
                        showDinoUnlockedDialog(dino);
                        sharedPreferences.edit().putBoolean("DinoLocked" + i, false).apply();
                    }
                }
            }
        }
        cursor.close();

        backgroundMusicPlayer = MediaPlayer.create(PrincipalMenu.this, R.raw.jurassic_world_song2);
        backgroundMusicPlayer.setLooping(true);
        backgroundMusicPlayer.start();
    }

    private void showDinoUnlockedDialog(DinoFriend dino) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom5);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView lblDinoFriendName = dialog.findViewById(R.id.lblDinoFriendName);
        ImageView imgDinoFriendPhoto = dialog.findViewById(R.id.imgDinoFriendPhoto);

        lblDinoFriendName.setText("¡Has desbloqueado a:\n" + "¡" + dino.getDinoName() + "!");
        int resID = getResources().getIdentifier(dino.getDinoPhoto(), "drawable", getPackageName());
        imgDinoFriendPhoto.setImageResource(resID);

        soundPlayer = MediaPlayer.create(this, R.raw.new_dino_friend);
        soundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                soundPlayer = null;
            }
        });
        soundPlayer.start();

        Button btnUnderstand = dialog.findViewById(R.id.btnUnderstand);
        btnUnderstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPlayer != null) {
                    soundPlayer.stop();
                    soundPlayer.release();
                    soundPlayer = null;
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.release();
            backgroundMusicPlayer = null;
        }
    }
}