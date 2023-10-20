package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout layout;
    private MediaPlayer backgroundMusicPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        backgroundMusicPlayer = MediaPlayer.create(MainActivity.this, R.raw.jurassic_world_song2);
        backgroundMusicPlayer.setLooping(true);
        backgroundMusicPlayer.start();

        layout = (ConstraintLayout) findViewById(R.id.mainLayout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WelcomePage.class);
                startActivity(intent);
            }
        });
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