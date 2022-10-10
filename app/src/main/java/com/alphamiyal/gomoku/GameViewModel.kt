package com.alphamiyal.gomoku

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.lifecycle.ViewModel

//holds state of game
//holds size of board, arrangement of pieces on board, indication of whose turn it is, etc
class GameViewModel: ViewModel() {
    //Screen dimensions set
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()
    private  var loaded = false
    private lateinit var tiles: TiledBitmap
    val numCols = 6
    val numRows = 6
    //board represented via array
    val contents = Array(numCols * numRows){0}
    //num 0-2, 9-11, 18-20 corresponding to location of square on board(border, middle of drawing board)
    val baseContents = Array(numCols * numRows){0}
    //contains 0(no stone), 3(black stone), 6(white stone) for each slot of the grid
    val stoneContents = Array(numCols * numRows){0}
    private var blacksTurn = true
    private lateinit var boardSprite: BoardSprite

    fun load(resources: Resources?){
        if(!loaded){
            //initializes all baseContents entries to 10(num of square in middle of board)
            // and stoneContents entries to 0(no stone)
            for(y in 0 until numRows){
                for(x in 0 until numCols){
                    val index = x+numCols*y
                    baseContents[index] = 10
                    stoneContents[index] = 0
                }
            }
            //decrements baseContents entries at start of row
            // increments baseContents entries at end of row
            for(y in 0 until numRows){
                baseContents[numRows*y] -= 1
                baseContents[numRows*y + numCols - 1] += 1
            }
            //same as above except with 9 and with columns
            for(x in 0 until numCols){
                baseContents[x] -=9
                baseContents[x+(numRows-1)*numCols]+=9
            }
            //copies values into contents board, which will correctly be an empty board
            for(y in 0 until numRows){
                for(x in 0 until numCols){
                    val index = x + numCols *y
                    contents[index] = baseContents[index]+stoneContents[index]
                }
            }
            //loads tileImage and initalizes tiles, specifying image should be cut 9 cols, 3 rows
            val tileImage = BitmapFactory.decodeResource(resources, R.drawable.gomoku)
            tiles = TiledBitmap(tileImage, 9, 3)
            //offset 100x100 (true position of board passed here)
            boardSprite = BoardSprite(tiles, this, 100.0f, 100.0f)
            //keeps code from loading a second time
            loaded = true
        }
    }

    //For when redraw is needed
    fun doClick(x: Int, y: Int): Boolean {
        return boardSprite.doClick(x, y)
    }

    fun draw(canvas: Canvas) {
        boardSprite.draw(canvas)
    }

    //could be moved to gameThread since this is a good part of the game logic
    //tap -> doClick -> boardSprite.doClick (determines square tapped) -> clickSquare
    fun clickSquare(col: Int, row: Int){
        val index = col + numCols *row
        var stone = stoneContents[index]
        if(blacksTurn){
            when(stone){
                //add black stone to empty square
                0-> {
                    stone=3
                    blacksTurn = false
                }
                //remove black square from location
                3-> {
                    stone = 0
                    blacksTurn = false
                }
            }
        }
        else{
            when(stone){
                //add white stone to empty square
                0-> {
                    stone = 6
                    blacksTurn = true
                }
                //remove white stone from location
                6->{
                    stone = 0
                    blacksTurn = true
                }
            }
        }
        //updates contents array
        stoneContents[index] = stone
        contents[index] = baseContents[index]+stoneContents[index]
    }

    fun update() {
    }
}