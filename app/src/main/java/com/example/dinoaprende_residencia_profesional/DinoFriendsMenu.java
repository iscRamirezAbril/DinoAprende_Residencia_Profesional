package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;

public class DinoFriendsMenu extends AppCompatActivity {
    ImageView imgUserProfilePicture;
    TextView lblUserName, lblScore;
    DatabaseHelper db;
    private static MediaPlayer mediaPlayer; // Haciéndolo estático

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dino_friends_menu);

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.jurassic_world_song1);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    mediaPlayer = null;
                }
            });
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        imgUserProfilePicture = findViewById(R.id.imgUserProfilePicture);
        lblUserName = findViewById(R.id.lblUserName);
        lblScore = findViewById(R.id.lblScore);

        db = new DatabaseHelper(this);
        loadUserInfo();
        updateDinoAccess();

        ImageButton btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                Intent intent = new Intent(DinoFriendsMenu.this, PrincipalMenu.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad
            }
        });
    }

    public void onDinoButtonPressed(View view) {
        int dinoId = Integer.parseInt(view.getTag().toString());

        Intent intent = new Intent(this, DinoFriendInfo.class);
        intent.putExtra("DINO_ID", dinoId);
        startActivity(intent);
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

    private void updateDinoAccess() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = db.getUserData();
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserProfileTable.COLUMN_SCORE));
            for (int i = 1; i <= 30; i++) {
                DinoFriend dino = dbHelper.getDinoFriendFromDatabase(i);
                if (dino != null) {
                    int dinoRequiredScore = dino.getDinoScore();
                    CardView cardView = findViewById(getResources().getIdentifier("btnDinoFriend" + i, "id", getPackageName()));
                    ImageView dinoImage = (ImageView) cardView.getChildAt(0);
                    TextView dinoName = (TextView) cardView.getChildAt(1);

                    if (score < dinoRequiredScore) {
                        dinoImage.setImageResource(R.drawable.ic_lock);
                        dinoName.setText("????");
                        cardView.setCardBackgroundColor(Color.parseColor("#A9A9A9"));
                        dinoName.setBackgroundColor(Color.parseColor("#565656"));
                        cardView.setOnClickListener(null);
                    } else {
                        int resID = getResources().getIdentifier(dino.getDinoPhoto(), "drawable", getPackageName());
                        dinoImage.setImageResource(resID);
                        dinoName.setText(dino.getDinoName() + "\n" + dinoRequiredScore + " puntos");
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onDinoButtonPressed(v);
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // El mediaPlayer ahora se maneja en otras partes del código, por lo que no necesitamos hacer nada aquí
    }
}