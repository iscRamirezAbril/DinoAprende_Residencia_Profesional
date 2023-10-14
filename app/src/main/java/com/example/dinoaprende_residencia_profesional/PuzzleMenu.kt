package com.example.dinoaprende_residencia_profesional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_puzzle_menu.*

class PuzzleMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_puzzle_menu)

        btnPuzzle1.setOnClickListener {
            val intent = Intent(this, Puzzle::class.java)
            intent.putExtra("PUZZLE_INDEX", 0)
            startActivity(intent)
        }

        btnPuzzle2.setOnClickListener {
            val intent = Intent(this, Puzzle::class.java)
            intent.putExtra("PUZZLE_INDEX", 1)
            startActivity(intent)
        }

        btnPuzzle3.setOnClickListener {
            val intent = Intent(this, Puzzle::class.java)
            intent.putExtra("PUZZLE_INDEX", 2)
            startActivity(intent)
        }

        btnPuzzle4.setOnClickListener {
            val intent = Intent(this, Puzzle::class.java)
            intent.putExtra("PUZZLE_INDEX", 3)
            startActivity(intent)
        }

        btnClose.setOnClickListener {
            val intent = Intent(this, PrincipalMenu::class.java)
            startActivity(intent)
        }
    }
}