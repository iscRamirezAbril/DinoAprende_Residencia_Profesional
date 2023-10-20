package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.media.MediaPlayer;

public class FeedbackForm extends AppCompatActivity {
    private MediaPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_feedback_form);

        ImageButton btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton1 = findViewById(R.id.option1Q1);
        RadioButton radioButton2 = findViewById(R.id.option2Q1);
        RadioButton radioButton3 = findViewById(R.id.option3Q1);
        EditText editText = findViewById(R.id.txtComment);
        Button btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    // Ningún RadioButton está seleccionado
                    Toast.makeText(FeedbackForm.this, "Favor de seleccionar una opción...", Toast.LENGTH_LONG);
                    return;
                }

                String comment = editText.getText().toString();
                if (comment.equals("")) {
                    Toast.makeText(FeedbackForm.this, "Favor de escribir un comentario...", Toast.LENGTH_LONG);
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"soportedinoaprende@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Opinión sobre DinoAprende.");

                if (radioButton1.isChecked()){
                    intent.putExtra(Intent.EXTRA_TEXT, "¿A esta persona le agradan los juegos que ofrecemos?: Si, y mucho." +
                            "\n\n Su opinión sobre la aplicación: " + editText.getText());
                }
                else if (radioButton2.isChecked()){
                    intent.putExtra(Intent.EXTRA_TEXT, "¿A esta persona le agradan los juegos que ofrecemos?: Solo un poco." +
                            "\n\n Su opinión sobre la aplicación: " + editText.getText());
                }
                else if (radioButton3.isChecked()){
                    intent.putExtra(Intent.EXTRA_TEXT, "¿A esta persona le agradan los juegos que ofrecemos?: Para nada..." +
                            "\n\n Su opinión sobre la aplicación: " + editText.getText());
                }

                showSuccessEmailDialog();

                try {
                    startActivity(Intent.createChooser(intent, "Porfavor, selecciona un correo..."));
                } catch (android.content.ActivityNotFoundException exception) {
                    Toast.makeText(FeedbackForm.this, "No se encontró ningún correo...", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void showExitDialog(){
        ConstraintLayout dialogConstraintLayout = findViewById(R.id.layoutDialog);
        View view = LayoutInflater.from(FeedbackForm.this).inflate(R.layout.dialog_custom1, dialogConstraintLayout);
        Button btnUnderstand = view.findViewById(R.id.btnUnderstand);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Añade el sonido aquí:
        soundPlayer = MediaPlayer.create(this, R.raw.stop);
        soundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                soundPlayer = null;
            }
        });
        soundPlayer.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackForm.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnUnderstand.findViewById(R.id.btnUnderstand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPlayer != null) {
                    soundPlayer.stop();
                    soundPlayer.release();
                    soundPlayer = null;
                }
                finish();
            }
        });

        btnCancel.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPlayer != null) {
                    soundPlayer.stop();
                    soundPlayer.release();
                    soundPlayer = null;
                }
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccessEmailDialog(){
        ConstraintLayout dialogConstraintLayout = findViewById(R.id.layoutDialog);
        View view = LayoutInflater.from(FeedbackForm.this).inflate(R.layout.dialog_custom2, dialogConstraintLayout);
        Button btnGoBack = view.findViewById(R.id.btnGoBack);

        soundPlayer = MediaPlayer.create(this, R.raw.success);
        soundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                soundPlayer = null;
            }
        });
        soundPlayer.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackForm.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnGoBack.findViewById(R.id.btnGoBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundPlayer != null) {
                    soundPlayer.stop();
                    soundPlayer.release();
                    soundPlayer = null;
                }
                finish();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}