package com.alphamiyal.gomoku

interface ActionItem {
    fun doClick(px:Int, py:Int): Boolean
}