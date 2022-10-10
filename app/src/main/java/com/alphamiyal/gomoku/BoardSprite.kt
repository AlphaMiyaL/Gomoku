package com.alphamiyal.gomoku

import android.graphics.Canvas
import android.util.Log

private val TAG = "BoardSprite"

//icons to draw, contents of each square on board, screen coords to draw upper left corner of board
class BoardSprite(private val tiles: TiledBitmap, private val gameViewModel: GameViewModel,
                  private val offsetX: Float, private val offsetY: Float) : Sprite, ActionItem {
    //iterates through every square on board and computes coords of square on screen,
    // then draws appropriate icon on screen location
    override fun draw(canvas: Canvas) {
        val iconWid = tiles.iconWid
        val iconHgt = tiles.iconHgt
        val numRows = gameViewModel.numRows
        val numCols = gameViewModel.numCols
        val contents = gameViewModel.contents
        for(y in 0 until numRows){
            for(x in 0 until numCols){
                val index = x + numCols * y
                canvas.drawBitmap(tiles.getImageByIndex(contents[index]),
                              offsetX + x * iconWid, offsetY+y*iconHgt, null)

            }
        }
    }

    override fun doClick(px: Int, py: Int): Boolean {
        Log.d(TAG, "Clicked")
        //see which square is clicked; If off the board, return false
        val col = (px - offsetX.toInt()) /tiles.iconWid
        val row = (py - offsetY.toInt()) /tiles.iconHgt
        if(col<0 || row<0
            || col>=gameViewModel.numCols
            || row>=gameViewModel.numRows){
            Log.d(TAG, "False + $col + ${gameViewModel.numCols}")
            return false
        }
        //click given square
        Log.d(TAG, "True + $col + $row")
        gameViewModel.clickSquare(col, row)
        return true
    }
}