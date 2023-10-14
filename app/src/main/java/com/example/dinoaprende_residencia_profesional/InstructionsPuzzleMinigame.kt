package com.example.dinoaprende_residencia_profesional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton

class InstructionsPuzzleMinigame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_instructions_puzzle_minigame)

        val btnClose: ImageButton = findViewById(R.id.btnClose)
        btnClose.setOnClickListener {
            finish()
        }

        val btnStartPuzzle: Button = findViewById(R.id.btnStartPuzzle)
        btnStartPuzzle.setOnClickListener{
            val intent = Intent(this, PuzzleMenu::class.java)
            startActivity(intent)
        }
    }
}