package com.example.dinoaprende_residencia_profesional

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dinoaprende_residencia_profesional.GestureDetectGridView.OnSwipeListener
import kotlinx.android.synthetic.main.activity_puzzle.*
import java.util.Random

enum class SwipeDirections {
    UP, DOWN, LEFT, RIGHT
}

class Puzzle : AppCompatActivity() {
    companion object {
        private const val TOTAL_COLUMNS = 3
        private const val DIMENSIONS = TOTAL_COLUMNS * TOTAL_COLUMNS

        private var boardColumnWidth = 0
        private var boardColumnHeight = 0
    }

    private val tileListIndexes = mutableListOf<Int>()
    private var puzzleIndex: Int = 0

    private val isSolved: Boolean get(){
        var solved = false
        for (i in tileListIndexes.indices) {
            if (tileListIndexes[i] == i) {
                solved = true
            } else {
                solved = false
                break
            }
        }

        return solved
    }

    private val puzzlesImages = listOf(
        listOf(R.drawable.rompecabezas1_pieza1, R.drawable.rompecabezas1_pieza2, R.drawable.rompecabezas1_pieza3, R.drawable.rompecabezas1_pieza4, R.drawable.rompecabezas1_pieza5, R.drawable.rompecabezas1_pieza6, R.drawable.rompecabezas1_pieza7, R.drawable.rompecabezas1_pieza8, R.drawable.rompecabezas1_pieza9),
        listOf(R.drawable.rompecabezas2_pieza1, R.drawable.rompecabezas2_pieza2, R.drawable.rompecabezas2_pieza3, R.drawable.rompecabezas2_pieza4, R.drawable.rompecabezas2_pieza5, R.drawable.rompecabezas2_pieza6, R.drawable.rompecabezas2_pieza7, R.drawable.rompecabezas2_pieza8, R.drawable.rompecabezas2_pieza9),
        listOf(R.drawable.rompecabezas3_pieza1, R.drawable.rompecabezas3_pieza2, R.drawable.rompecabezas3_pieza3, R.drawable.rompecabezas3_pieza4, R.drawable.rompecabezas3_pieza5, R.drawable.rompecabezas3_pieza6, R.drawable.rompecabezas3_pieza7, R.drawable.rompecabezas3_pieza8, R.drawable.rompecabezas3_pieza9),
        listOf(R.drawable.rompecabezas4_pieza1, R.drawable.rompecabezas4_pieza2, R.drawable.rompecabezas4_pieza3, R.drawable.rompecabezas4_pieza4, R.drawable.rompecabezas4_pieza5, R.drawable.rompecabezas4_pieza6, R.drawable.rompecabezas4_pieza7, R.drawable.rompecabezas4_pieza8, R.drawable.rompecabezas4_pieza9)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_puzzle)

        // Recoge el índice del rompecabezas del intent
        puzzleIndex = intent.getIntExtra("PUZZLE_INDEX", 0)

        init()
        scrambleTileBoard()
        setTileBoardDimensions()
    }

    private fun init() {
        puzzle_grid_view.apply {
            numColumns = TOTAL_COLUMNS
            setOnSwipeListener(object : OnSwipeListener {
                override fun onSwipe(direction: SwipeDirections, position: Int) {
                    moveTiles(direction, position)
                }
            })
        }

        tileListIndexes += 0 until DIMENSIONS
    }

    private fun scrambleTileBoard() {
        var index: Int
        var tempIndex: Int
        val random = Random()

        for (i in tileListIndexes.size - 1 downTo 1) {
            index = random.nextInt(i + 1)
            tempIndex = tileListIndexes[index]
            tileListIndexes[index] = tileListIndexes[i]
            tileListIndexes[i] = tempIndex
        }
    }

    private fun setTileBoardDimensions() {
        val observer = puzzle_grid_view.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                puzzle_grid_view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val displayWidth = puzzle_grid_view.measuredWidth
                val displayHeight = puzzle_grid_view.measuredHeight
                val statusbarHeight = getStatusBarHeight(applicationContext)
                val requiredHeight = displayHeight - statusbarHeight

                boardColumnWidth = displayWidth / TOTAL_COLUMNS
                boardColumnHeight = requiredHeight / TOTAL_COLUMNS

                displayTileBoard()
            }
        })
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        return result
    }

    /**
     * Used for both init and every time a new swap move is made by the user.
     */
    private fun displayTileBoard() {
        val tileImages = mutableListOf<ImageView>()
        val currentPuzzleImages = puzzlesImages[puzzleIndex]

        tileListIndexes.forEach { i ->
            val tileImage = ImageView(this)
            tileImage.setBackgroundResource(currentPuzzleImages[i])
            tileImages.add(tileImage)
        }

        puzzle_grid_view.adapter = TileImageAdapter(tileImages, boardColumnWidth, boardColumnHeight)
    }

    private fun displayToast(@StringRes textResId: Int) {
        Toast.makeText(this, getString(textResId), Toast.LENGTH_SHORT).show()
    }

    private fun moveTiles(direction: SwipeDirections, position: Int) {
        // Upper-left-corner tile
        if (position == 0) {
            when (direction) {
                SwipeDirections.RIGHT -> swapTile(position, 1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                else -> displayToast(R.string.invalid_move)
            }
            // Upper-center tiles
        } else if (position > 0 && position < TOTAL_COLUMNS - 1) {
            when (direction) {
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.invalid_move)
            }
            // Upper-right-corner tile
        } else if (position == TOTAL_COLUMNS - 1) {
            when (direction) {
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                else -> displayToast(R.string.invalid_move)
            }
            // Left-side tiles
        } else if (position > TOTAL_COLUMNS - 1 && position < DIMENSIONS - TOTAL_COLUMNS && position % TOTAL_COLUMNS == 0) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                SwipeDirections.DOWN -> swapTile(position, TOTAL_COLUMNS)
                else -> displayToast(R.string.invalid_move)
            }
            // Right-side AND bottom-right-corner tiles
        } else if (position == TOTAL_COLUMNS * 2 - 1 || position == TOTAL_COLUMNS * 3 - 1) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.DOWN -> {
                    // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                    // right-corner tile.
                    if (position <= DIMENSIONS - TOTAL_COLUMNS - 1) {
                        swapTile(position, TOTAL_COLUMNS)
                    } else {
                        displayToast(R.string.invalid_move)
                    }
                }
                else -> displayToast(R.string.invalid_move)
            }
            // Bottom-left corner tile
        } else if (position == DIMENSIONS - TOTAL_COLUMNS) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.invalid_move)
            }
            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - TOTAL_COLUMNS) {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> displayToast(R.string.invalid_move)
            }
            // Center tiles
        } else {
            when (direction) {
                SwipeDirections.UP -> swapTile(position, -TOTAL_COLUMNS)
                SwipeDirections.LEFT -> swapTile(position, -1)
                SwipeDirections.RIGHT -> swapTile(position, 1)
                else -> swapTile(position, TOTAL_COLUMNS)
            }
        }
    }

    private fun swapTile(currentPosition: Int, swap: Int) {
        val newPosition = tileListIndexes[currentPosition + swap]
        tileListIndexes[currentPosition + swap] = tileListIndexes[currentPosition]
        tileListIndexes[currentPosition] = newPosition
        displayTileBoard()

        if (isSolved) {
            val dbHelper = DatabaseHelper(this)
            dbHelper.updateUserScore(5)

            val intent = Intent(this, WonMinigamePuzzle::class.java)

            intent.putExtra("SCORE", 5)

            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        showCustomDialog()
    }

    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.dialog_custom4, null)

        val btnExit = view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnExit)
        val btnCancel = view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnCancel)

        val dialog = builder.setView(view).create()

        btnExit.setOnClickListener {
            (it.context as AppCompatActivity).finish()
            val intent = Intent(this, PrincipalMenu::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}