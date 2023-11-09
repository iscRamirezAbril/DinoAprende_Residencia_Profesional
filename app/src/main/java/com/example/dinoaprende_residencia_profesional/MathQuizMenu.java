package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class MathQuizMenu extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";
    private Spinner spinnerDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_math_quiz_menu);

        spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        ImageButton btnStartAddQuiz = findViewById(R.id.btnAddQuiz);
        ImageButton btnStartSubsQuiz = findViewById(R.id.btnSubsQuiz);
        ImageButton btnStartMultQuiz = findViewById(R.id.btnMultiQuiz);
        ImageButton btnStartDivQuiz = findViewById(R.id.btnDivQuiz);
        ImageButton btnStartMixed = findViewById(R.id.btnMixedQuiz);
        ImageButton btnClose = findViewById(R.id.btnClose4);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, Question.getAllDifficultyLevels());
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDifficulty.setAdapter(difficultyAdapter);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathQuizMenu.this, PrincipalMenu.class);
                startActivity(intent);
            }
        });

        btnStartAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(Category.ADDITIONS); // Usamos la constante Category.ADDITIONS para representar la categoría "Sumas"
            }
        });

        btnStartSubsQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(Category.SUBTRACTIONS); // Usamos la constante Category.SUBTRACTIONS para representar la categoría "Restas"
            }
        });

        btnStartMultQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(Category.MULTIPLICATIONS); // Usamos la constante Category.SUBTRACTIONS para representar la categoría "Restas"
            }
        });

        btnStartDivQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(Category.DIVISIONS); // Usamos la constante Category.SUBTRACTIONS para representar la categoría "Restas"
            }
        });

        btnStartMixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(Category.MIXED); // Usamos la constante Category.SUBTRACTIONS para representar la categoría "Restas"
            }
        });
    }

    private void startQuiz(int categoryID) {
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(MathQuizMenu.this, Quiz.class);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryID);
        startActivity(intent);
    }
}