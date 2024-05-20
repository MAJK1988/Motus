package com.example.motus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.motus.model.DataMons
import com.example.motus.model.PlayerController
import com.example.motus.ui.theme.MotusTheme
import com.example.motus.view.ScreenView

class MainActivity : ComponentActivity() {
    val TAG: String = "MainActivityLog"
    val player: MutableState<PlayerController?> = mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.i(TAG, "Read words from the text file and save them in the string array.!")
        DataMons.readTextFile(R.raw.myfile, this)
        player.value= PlayerController()
    }

    override fun onStart() {
        super.onStart()
        setContent {
            MotusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.i(TAG, "Show Motus View.!!")
                    ScreenView.ScreenView(player=player, this)
                }

            }
        }
    }
}

