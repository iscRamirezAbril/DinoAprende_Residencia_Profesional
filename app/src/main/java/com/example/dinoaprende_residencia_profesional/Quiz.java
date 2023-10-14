package com.example.dinoaprende_residencia_profesional;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import android.graphics.drawable.GradientDrawable;

public class Quiz extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private static final long COUNTDOWN_IN_MILIS = 30000;

    private TextView textViewQuestion;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private Button buttonOption1;
    private Button buttonOption2;
    private Button buttonOption3;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultB;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timerLeftInMilis;
    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;

    private Question currentQuestion;
    private int selectedOption = 0;
    private boolean answered;

    private int score = 0;

    private GradientDrawable defaultBackground;
    private GradientDrawable selectedBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.txtQuestion);
        textViewQuestionCount = findViewById(R.id.lblNumQuestion);
        textViewCountDown = findViewById(R.id.lblTimer);
        buttonOption1 = findViewById(R.id.btnOption1);
        buttonOption2 = findViewById(R.id.btnOption2);
        buttonOption3 = findViewById(R.id.btnOption3);
        buttonConfirmNext = findViewById(R.id.btnConfirm_Next);
        ImageButton btnClose = findViewById(R.id.btnClose);

        textColorDefaultB = buttonOption1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();


        Intent intent = getIntent();
        dbHelper = new DatabaseHelper(this);
        int categoryID = intent.getIntExtra(MathQuizMenu.EXTRA_CATEGORY_ID, 0);
        String categoryName = intent.getStringExtra(MathQuizMenu.EXTRA_CATEGORY_NAME);
        String difficulty = getIntent().getStringExtra(MathQuizMenu.EXTRA_DIFFICULTY);

        ArrayList<Question> questions = dbHelper.getQuestions(difficulty, categoryID);
        questionList = questions;
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonOption1.setOnClickListener(v -> selectedOption = 1);
        buttonOption2.setOnClickListener(v -> selectedOption = 2);
        buttonOption3.setOnClickListener(v -> selectedOption = 3);

        defaultBackground = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.borderbutton_default);

        selectedBackground = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.borderbutton_selected);

        resetButtonColors();  // Llamar a este método después de inicializar tus botones.

        buttonOption1.setBackground(defaultBackground);
        buttonOption2.setBackground(defaultBackground);
        buttonOption3.setBackground(defaultBackground);

        buttonOption1.setOnClickListener(v -> {
            resetButtonColors();
            buttonOption1.setBackground(selectedBackground);
            selectedOption = 1;
        });

        buttonOption2.setOnClickListener(v -> {
            resetButtonColors();
            buttonOption2.setBackground(selectedBackground);
            selectedOption = 2;
        });

        buttonOption3.setOnClickListener(v -> {
            resetButtonColors();
            buttonOption3.setBackground(selectedBackground);
            selectedOption = 3;
        });

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){
                    if (selectedOption != 0) {
                        checkAnswer();
                        answered = true;
                    } else {
                        Toast.makeText(Quiz.this, "Porfavor, selecciona una opción...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
    }

    private void showNextQuestion() {
        buttonOption1.setTextColor(textColorDefaultB);
        buttonOption2.setTextColor(textColorDefaultB);
        buttonOption3.setTextColor(textColorDefaultB);
        resetButtonColors();

        if (questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            buttonOption1.setText(currentQuestion.getOption1());
            buttonOption2.setText(currentQuestion.getOption2());
            buttonOption3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText(questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirmar");

            timerLeftInMilis = COUNTDOWN_IN_MILIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timerLeftInMilis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLeftInMilis = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                timerLeftInMilis = 0;
                updateCountDown();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDown() {
        int minutes = (int) (timerLeftInMilis / 1000) / 60;
        int seconds = (int) (timerLeftInMilis/ 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);

        if (timerLeftInMilis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer() {
        countDownTimer.cancel();
        if (selectedOption == currentQuestion.getAnswerNr()){
            score++;
        }
        selectedOption = 0;
        showSolution();
        answered = true;
    }

    private void showSolution() {
        Drawable incorrectAnswer = ContextCompat.getDrawable(this, R.drawable.borderbutton_wrong);
        Drawable correctAnswer = ContextCompat.getDrawable(this, R.drawable.borderbutton_correct);

        buttonOption1.setBackground(incorrectAnswer);
        buttonOption2.setBackground(incorrectAnswer);
        buttonOption3.setBackground(incorrectAnswer);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                buttonOption1.setBackground(correctAnswer);
                textViewQuestion.setText("¡La respuesta 1 es correcta!");
                break;
            case 2:
                buttonOption2.setBackground(correctAnswer);
                textViewQuestion.setText("¡La respuesta 2 es correcta!");
                break;
            case 3:
                buttonOption3.setBackground(correctAnswer);
                textViewQuestion.setText("¡La respuesta 3 es correcta!");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Siguiente");
        } else {
            buttonConfirmNext.setText("Temrinado");
        }
    }

    private void resetButtonColors() {
        Drawable defaultBackground = ContextCompat.getDrawable(this, R.drawable.borderbutton_default);

        buttonOption1.setBackground(defaultBackground);
        buttonOption2.setBackground(defaultBackground);
        buttonOption3.setBackground(defaultBackground);
    }

    private void finishQuiz() {
        Intent resultIntent;

        if (score > 0) {
            resultIntent = new Intent(Quiz.this, WonMinigameQuiz.class);
            resultIntent.putExtra("SCORE", score);
        } else {
            resultIntent = new Intent(Quiz.this, MissMinigame.class);
        }

        startActivity(resultIntent);
        finish();

        updateScoreInDatabase(score);

        startActivity(resultIntent);
        finish();
    }

    private void updateScoreInDatabase(int score) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateUserScore(score);
    }

    private void showExitDialog(){
        ConstraintLayout dialogConstraintLayout = findViewById(R.id.layoutDialog);
        View view = LayoutInflater.from(Quiz.this).inflate(R.layout.dialog_custom4, dialogConstraintLayout);
        Button btnExit = view.findViewById(R.id.btnExit);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(Quiz.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        btnExit.findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz.this, PrincipalMenu.class);
                startActivity(intent);
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