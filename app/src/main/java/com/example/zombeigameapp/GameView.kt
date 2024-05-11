//GameView
package com.example.zombeigameapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myBoyPosition = 0
    private val zombies = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            zombies.add(map)
        }
        time = time + 10 + speed
        val zombieWidth = viewWidth / 5
        val zombieHeight = zombieWidth + 10
        myPaint!!.style = Paint.Style.FILL

        // Drawing player
        val d = resources.getDrawable(R.drawable.boy6, null)
        d.setBounds(
            myBoyPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - zombieHeight,
            myBoyPosition * viewWidth / 3 + viewWidth / 15 + zombieWidth - 25,
            viewHeight - 2
        )
        d.draw(canvas!!)

        // Drawing zombies and checking collisions
        for (i in zombies.indices) {
            try {
                val zombieX = zombies[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var zombieY = time - zombies[i]["startTime"] as Int
                if (zombieY < viewHeight) {
                    val d2 = resources.getDrawable(R.drawable.zombei1, null)
                    d2.setBounds(
                        zombieX + 25, zombieY - zombieHeight, zombieX + zombieWidth - 25, zombieY
                    )
                    d2.draw(canvas)
                    if (zombies[i]["lane"] as Int == myBoyPosition && zombieY > viewHeight - 2 - zombieHeight && zombieY < viewHeight - 2) {
                        gameTask.closeGame(score)
                    }
                }
                if (zombieY > viewHeight + zombieHeight) {
                    zombies.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Drawing score and speed
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myBoyPosition > 0) {
                        myBoyPosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myBoyPosition < 2) {
                        myBoyPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    fun startNewGame() {
        score = 0
        time = 0
        speed = 1
        zombies.clear()
        invalidate()
    }




}
