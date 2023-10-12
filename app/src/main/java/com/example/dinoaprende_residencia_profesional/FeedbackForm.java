package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class FeedbackForm extends AppCompatActivity {

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

        RadioButton radioButton1 = findViewById(R.id.option1Q1);
        RadioButton radioButton2 = findViewById(R.id.option2Q1);
        RadioButton radioButton3 = findViewById(R.id.option3Q1);
        EditText editText = findViewById(R.id.txtComment);
        Button btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackForm.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnUnderstand.findViewById(R.id.btnUnderstand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancel.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}