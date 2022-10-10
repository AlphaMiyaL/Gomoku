package com.alphamiyal.gomoku

import android.graphics.Bitmap

//used for Sprite Sheets
// class is passed large bitmap, num of columns and rows
class TiledBitmap(private val image: Bitmap, private val numCols: Int, numRows: Int) {
    //size of bitmap in pixels
    private val bitmapWid: Int = image.width
    private val bitmapHgt: Int = image.height
    //size of icons in pixels
    val iconWid = bitmapWid / numCols
    val iconHgt = bitmapHgt / numRows
    // as each icon is created, stored in spot in array
    private val subImages = Array<Bitmap?>(numCols * numRows){null}

    //fetches icon by column and row in original img
    fun getImage(col: Int, row: Int) : Bitmap {
        val idx = col + numCols * row
        return subImages[idx] ?: makeImage(col, row)
    }

    //fetches icon by index in array
    fun getImageByIndex(idx: Int) : Bitmap {
        val col = idx % numCols
        val row = idx / numCols
        return subImages[idx] ?: makeImage(col, row)
    }

    //creates new bitmap for a portion of the large img
    private fun makeImage(col: Int, row: Int) : Bitmap {
        val idx = col + numCols * row
        val subImage = Bitmap.createBitmap(
            image,
            col * iconWid,
            row * iconHgt,
            iconWid,
            iconHgt)
        subImages[idx] = subImage
        return subImage
    }
}