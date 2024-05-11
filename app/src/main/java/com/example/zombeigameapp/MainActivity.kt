// MainActivity.kt
package com.example.zombeigameapp

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), GameTask {

    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView
    lateinit var score: TextView
    lateinit var backgroundMediaPlayer: MediaPlayer
    lateinit var gameOverMediaPlayer: MediaPlayer
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        backgroundMediaPlayer = MediaPlayer.create(this, R.raw.audio)
        gameOverMediaPlayer = MediaPlayer.create(this, R.raw.gameover)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.score.observe(this, Observer { score ->
            this@MainActivity.score.text = "Score: $score"
        })

        startBtn.setOnClickListener {
            if (::mGameView.isInitialized) {
                rootLayout.removeView(mGameView)
            }
            mGameView = GameView(this@MainActivity, this@MainActivity)
            mGameView.setBackgroundResource(R.drawable.zback3)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE

            if (backgroundMediaPlayer.isPlaying) {
                backgroundMediaPlayer.pause()
                backgroundMediaPlayer.seekTo(0)
            }
            backgroundMediaPlayer.start()
        }

        backgroundMediaPlayer.setOnCompletionListener {
            backgroundMediaPlayer.seekTo(0)
            backgroundMediaPlayer.start()
        }
    }

    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        backgroundMediaPlayer.stop()
        backgroundMediaPlayer.prepare()
        gameOverMediaPlayer.start()

        // Save the high score in SharedPreferences
        viewModel.saveHighScore(mScore)
    }

    override fun startNewGame() {
        mGameView.startNewGame()
        score.text = "Score : 0"
        if (backgroundMediaPlayer.isPlaying) {
            backgroundMediaPlayer.pause()
            backgroundMediaPlayer.seekTo(0)
        }
        backgroundMediaPlayer.start()
    }

    fun onHighScoresBtnClick(view: View) {
        val intent = Intent(this, HighScoresActivity::class.java)
        startActivity(intent)
    }
}
