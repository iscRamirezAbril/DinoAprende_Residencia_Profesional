package com.example.dinoaprende_residencia_profesional

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_memory_cards.*
import com.example.dinoaprende_residencia_profesional.R.drawable.*

class MemoryCards : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_memory_cards)

        val images = mutableListOf(cardimage1, cardimage2, cardimage3, cardimage4, cardimage5)
        images.addAll(images)
        images.shuffle()

        buttons = listOf(imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8, imageButton9, imageButton10)

        cards = buttons.indices.map {index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed{ index, imageButton ->
            imageButton.setOnClickListener{
                Log.i(TAG, "¡El botón ha sido presionado!")
                updateModels(index)
                updateViews()
            }
        }

        btnClose2.setOnClickListener {
            showDialog()
        }
    }

    private fun updateViews() {
        cards.forEachIndexed{index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.huevito)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error checking
        if (card.isFaceUp){
            Toast.makeText(this, "¡Movimiento inválido!", Toast.LENGTH_SHORT).show()
            return
        }

        if (indexOfSingleSelectedCard == null){
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for(card in cards){
            if (!card.isMatched){
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier){
            Toast.makeText(this, "¡Has encontrado un par!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            checkForVictory()
        }
    }

    private fun checkForVictory() {
        if (cards.all { it.isMatched }) {
            val dbHelper = DatabaseHelper(this)
            dbHelper.updateUserScore(5)

            val intent = Intent(this, WonMinigameMemory::class.java)

            intent.putExtra("SCORE", 5)

            startActivity(intent)

            finish()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_custom4, null)

        val btnExit = dialogLayout.findViewById<AppCompatButton>(R.id.btnExit)
        val btnCancel = dialogLayout.findViewById<AppCompatButton>(R.id.btnCancel)

        lateinit var dialog: AlertDialog

        btnExit.setOnClickListener {
            val intent = Intent(this, PrincipalMenu::class.java)
            startActivity(intent)
            finish()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        builder.setView(dialogLayout)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}