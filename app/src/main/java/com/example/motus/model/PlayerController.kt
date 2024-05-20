package com.example.motus.model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.motus.controller.DataMons

/**
* Class for managing the player's state.
 * */
class PlayerController : ViewModel() {

    val TAG:String= "LogPlayer"
    /**
     * selectedWords:MutableState List of words selected by the player
     * selectedWord:  A word randomly selected from the database (myfile.txt). The player must find it within 7 attempts.
     * newWord: MutableState string of the word newly entered by the player
     * canComplete: canComplete: a boolean value that determines whether the player can complete the round or not. The player cannot complete the round if:
     *     1. they select a word that does not exist in the database (error: Spelling error),
     *     2. they select the same word twice (error: You chose this word before),
     *     3. the number of attempts exceeds 7 (error: You used all your attempts).
     * isWin: a boolean indicating whether the player has won or not. It's true if the newWord is equal to the selectedWord.
     *  messageResult: It's a MutableState string containing the result of the round. It includes a message indicating whether the player won or not, as well as the reason for the player's loss.
     *  **/

    val selectedWords:MutableState<Array<String>> = mutableStateOf(arrayOf())
    var selectedWord:MutableState<String> = mutableStateOf("")
    var newWord:MutableState<String> = mutableStateOf("")
    var canComplete:Boolean=true
    var isWin:Boolean=false
    var messageResult:MutableState<String> = mutableStateOf("")

    init {
        selectedWord.value= DataMons.getMon()
        selectedWords.value= arrayOf()
        Log.i(TAG,"init PlayerController,  selectedMon: ${selectedWord.value}")
    }
    companion object {
        const val attemptsNumber: Int= 7
    }


}