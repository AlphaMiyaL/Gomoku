package com.alphamiyal.gomoku

import android.graphics.Canvas

//For drawing sprites onto the canvas
interface Sprite {
    // Draw the sprite onto the canvas
    fun draw(canvas: Canvas)
}