package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DinoFriendInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dino_friend_info);

        ImageButton btnClose = findViewById(R.id.btnClose);
        findViewById(R.id.lblSpecie);
        TextView lblSpecie, lblPeriod, lblDiet, lblTemperament, lblDescription;
        ImageView imgDinoFriendPhoto;

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        lblSpecie = findViewById(R.id.lblSpecie);
        lblPeriod = findViewById(R.id.lblPeriod);
        lblDiet = findViewById(R.id.lblDiet);
        lblTemperament = findViewById(R.id.lblTemperament);
        lblDescription = findViewById(R.id.lblDescription);
        imgDinoFriendPhoto = findViewById(R.id.imgDinoFriendPhoto);

        int dinoId = getIntent().getIntExtra("DINO_ID", -1);

        DinoFriend dinoFriend = dbHelper.getDinoFriendFromDatabase(dinoId);

        if (dinoFriend != null) {
            lblSpecie.setText(dinoFriend.getDinoSpecie());
            lblPeriod.setText(dinoFriend.getDinoPeriod());
            lblDiet.setText(dinoFriend.getDinoDiet());
            lblTemperament.setText(dinoFriend.getDinoTemperament());
            lblDescription.setText(dinoFriend.getDinoDescription());

            String imgName = dinoFriend.getDinoPhoto();
            Resources res = getResources();
            int resID = res.getIdentifier(imgName, "drawable", getPackageName());
            if (resID != 0) {
                imgDinoFriendPhoto.setImageResource(resID);
            } else {
                Toast.makeText(this, "Imagen no encontrada", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "DinoFriend no encontrado", Toast.LENGTH_SHORT).show();
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinoFriendInfo.this, DinoFriendsMenu.class);
                startActivity(intent);
            }
        });
    }
}