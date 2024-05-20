package com.example.motus.model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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
    /**
     * Function to add a new word and update player state
     * Input:
     *      1. newWord: string of the word newly entered by the player
     * Output:
     *      1. If necessary, update canComplete flag,
     *      2. If necessary, update isWin flag,
     *      3. If necessary, update messageResult is
     * */
    fun addWordAndUpdateState(newWord: String) {
        Log.i(TAG, "New Mon: $newWord")
        if((selectedWords.value.size >= attemptsNumber-1 && newWord!=selectedWord.value) ||
            !DataMons.hasValue(newWord) ||
            selectedWords.value.contains(newWord)){
            Log.i(TAG, "Data base has mon: ${DataMons.hasValue(newWord)}")
            messageResult.value=
                when {
                    selectedWords.value.size >= attemptsNumber-1-> "You have lost the game. Better luck next time! \n" +
                            "Reason: You used all your attempts. \n"+
                            "Selected Word: $newWord. \n"+
                            "Click OK to start again."
                    !DataMons.hasValue(newWord)  ->"You have lost the game. Better luck next time! \n" +
                            "Reason: Spelling error. \n"+
                            "Selected Word: $newWord. \n"+
                            "Click OK to start again."
                    else -> "You have lost the game. Better luck next time! \n" +
                            "Reason: You chose this word before. \n"+
                            "Selected Word: $newWord. \n"+
                            "Click OK to start again."
                }
            canComplete=false
        }
        selectedWords.value += newWord

        if (newWord==selectedWord.value){
            messageResult.value="Congratulations, you won this round!\n "+
                    "Selected Word: $newWord. \n"+
                    "Click OK to start again."
            isWin=true
        }
        Log.i(TAG, "Player is win: $isWin, canComplete: $canComplete")
    }

    /**
     * Function to reset the player's state and prepare for the next round.
     * Input:
     *
     * Output:
     *       1. Reset the list of selectedWords.
     *       2. RSelect a new "selectedWord" from the database.
     *       3. Reset the flags: canComplete, isWin, and messageResult.
     * */
    fun clear(){
        // Clear player state
        selectedWords.value= arrayOf()
        selectedWord.value= DataMons.getMon()
        Log.i(TAG,"selectedMon: ${selectedWord.value}")
        canComplete=true
        isWin=false
        messageResult.value=""
    }

    companion object {
        const val attemptsNumber: Int= 7
    }


}