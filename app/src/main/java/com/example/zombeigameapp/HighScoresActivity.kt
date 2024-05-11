// HighScoresActivity.kt
package com.example.zombeigameapp

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class HighScoresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        // Get the high score and display it in the highScoreTextView
        val highScore = PreferencesUtil(this).getHighScore()
        val highScoreTextView = findViewById<TextView>(R.id.highScoreTextView)
        highScoreTextView.text = "High Score: $highScore"


        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }


}
