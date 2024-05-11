//GameViewModel
package com.example.zombeigameapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesUtil = PreferencesUtil(application)

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    init {
        _score.value = 0 // Initialize score
    }

    fun saveHighScore(score: Int) {
        preferencesUtil.saveHighScore(score)
    }

    fun getHighScore(): Int {
        return preferencesUtil.getHighScore()
    }

    fun updateScore(newScore: Int) {
        _score.value = newScore
    }
}
