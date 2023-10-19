package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class DinoFriendsMenu extends AppCompatActivity {
    ImageView imgUserProfilePicture;
    TextView lblUserName, lblScore;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dino_friends_menu);

        imgUserProfilePicture = findViewById(R.id.imgUserProfilePicture);
        lblUserName = findViewById(R.id.lblUserName);
        lblScore = findViewById(R.id.lblScore);

        db = new DatabaseHelper(this);
        loadUserInfo();

        ImageButton btnClose = findViewById(R.id.btnClose);
        CardView btnDinoFriend1 = findViewById(R.id.btnDinoFriend1);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinoFriendsMenu.this, PrincipalMenu.class);
                startActivity(intent);
            }
        });
    }

    public void onDinoButtonPressed(View view) {
        // Suponiendo que el tag del botón es el ID del dinosaurio en tu base de datos
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
}