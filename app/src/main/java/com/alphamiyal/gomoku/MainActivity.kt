package com.alphamiyal.gomoku

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() { //does not have app bar, and allows ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Non-Deprecated method
        //window.insetsController and statusBars essentially do the same as bellow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else{
            //Deprecated method
            //window.setFlags call tells Android that app will be a fullscreen app
            //requestWindowFeature call indicates there should not be a title bar for app
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        //ViewModel to preserve game state though rotations
        val gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.load(resources)

        //View is set to GameView
        setContentView(GameView(this).attachViewModel(gameViewModel))
    }
}