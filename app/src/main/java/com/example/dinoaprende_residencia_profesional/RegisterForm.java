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
import android.widget.ImageButton;

import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

public class RegisterForm extends AppCompatActivity {
    private EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_form);

        txtName = findViewById(R.id.txtName);
        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Intent intent = new Intent(RegisterForm.this, PrincipalMenu.class);
                startActivity(intent);
            }
        });

        ImageButton btnClose = findViewById(R.id.btnClose3);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
    }

    private void registerUser() {
        String name = txtName.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(RegisterForm.this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] profileImages = {
                "profile_bronto",
                "profile_parasaur",
                "profile_rex",
                "profile_trike"
        };

        Random random = new Random();
        String assignedProfileImage = profileImages[random.nextInt(profileImages.length)];

        DatabaseHelper dbHelper = new DatabaseHelper(RegisterForm.this);
        boolean isSuccess = dbHelper.addUser(name, assignedProfileImage);

        if (isSuccess) {
            Toast.makeText(RegisterForm.this, "¡Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(RegisterForm.this, "Error al registrar al usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExitDialog() {
        ConstraintLayout dialogConstraintLayout = findViewById(R.id.layoutDialog);
        View view = LayoutInflater.from(RegisterForm.this).inflate(R.layout.dialog_custom3, dialogConstraintLayout);
        Button btnUnderstand = view.findViewById(R.id.btnUnderstand);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterForm.this);
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

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}