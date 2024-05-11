//PreferencesUtil
package com.example.zombeigameapp

import android.content.Context

class PreferencesUtil(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveHighScore(score: Int) {
        sharedPreferences.edit().putInt("highScore", maxOf(score, sharedPreferences.getInt("highScore", 0))).apply()
    }

    fun getHighScore(): Int {
        return sharedPreferences.getInt("highScore", 0)
    }
}
