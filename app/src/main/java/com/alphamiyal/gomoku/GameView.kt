package com.alphamiyal.gomoku

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.lifecycle.ViewModel

//Implements View widget for app
//  exposes Canvas that game can draw on

//SurfaceView is a view widget that provides a canvas where things can be drawn
//SurfaceHolder.Callback -> gameView notified when Surface created or destroyed
//  runs and pauses gameThread -> game is running only when visible on screen
class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback {
    //initialized in init
    private val gameThread: GameThread
    private lateinit var gameViewModel: GameViewModel

    //gameView is registered with SurfaceView to receive callback functions -> ie: SurfaceChanged
    //gameThread created
    //allows gameView to receive touch events via isFocusable
    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
        isFocusable = true
    }

    fun attachViewModel(model:GameViewModel):GameView{
        gameViewModel = model
        gameThread.attachViewModel(model)
        return this
    }

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int) {
    }

    //runs gameThread
    override fun surfaceCreated(holder: SurfaceHolder){
        gameThread.setRunning(true)
        gameThread.start()
    }

    //stops gameThread
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameThread.setRunning(false)
                gameThread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    //when user taps device, MotionEvent is sent to GameView
    // if ACTION_DOWN event, GameView extracts screen coords of event and passes info to gameThread
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN){
            val x = event.x.toInt()
            val y = event.y.toInt()
            return gameThread.doClick(x, y)
        }
        return false
    }

    //called whenever app decides view has changed and should be redrawn
    // fills screen with black pixels to wipe the previous frame image
    // asks gameViewModel to redraw all sprites onscreen
    override fun draw(canvas: Canvas){
        super.draw(canvas)
        canvas.drawColor(Color.BLACK)
        gameViewModel.draw(canvas)
    }



}