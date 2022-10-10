package com.alphamiyal.gomoku

import android.content.res.Resources
import android.graphics.Canvas
import androidx.lifecycle.ViewModel

//holds state of game
//holds size of board, arrangement of pieces on board, indication of whose turn it is, etc
class GameViewModel: ViewModel() {
    //Screen dimensions set
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()
    private  var loaded = false

    fun load(resources: Resources?){
        if(!loaded){
            loaded = true
        }
    }

    //For when redraw is needed
    fun doClick(x: Int, y: Int): Boolean {
        return false
    }

    fun draw(canvas: Canvas) {
    }

    fun update() {
    }
}