package com.example.motus.controller

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.motus.model.PlayerController

class ControllerPlayer {

    companion object {
        private val TAG: String = "ControllerPlayer"

        /**
         * Function to add a new word and update player state
         * Input:
         *      1. newWord: string of the word newly entered by the player
         * Output:
         *      1. If necessary, update canComplete flag,
         *      2. If necessary, update isWin flag,
         *      3. If necessary, update messageResult is
         * */
        fun updatePlayerState(newWord: String, player: MutableState<PlayerController?>) {
            Log.i(TAG, "New Mon: $newWord")
            if ((player.value!!.selectedWords.value.size >= PlayerController.attemptsNumber - 1
                        && newWord != player.value!!.selectedWord.value) ||
                !DataMons.hasValue(newWord) ||
                player.value!!.selectedWords.value.contains(newWord)
            ) {
                Log.i(TAG, "Data base has mon: ${DataMons.hasValue(newWord)}")
                player.value!!.messageResult.value =
                    when {
                        player.value!!.selectedWords.value.size >= PlayerController.attemptsNumber - 1 -> "You have lost the game. Better luck next time! \n" +
                                "Reason: You used all your attempts. \n" +
                                "Selected Word: $newWord. \n" +
                                "Click OK to start again."

                        !DataMons.hasValue(newWord) -> "You have lost the game. Better luck next time! \n" +
                                "Reason: Spelling error. \n" +
                                "Selected Word: $newWord. \n" +
                                "Click OK to start again."

                        else -> "You have lost the game. Better luck next time! \n" +
                                "Reason: You chose this word before. \n" +
                                "Selected Word: $newWord. \n" +
                                "Click OK to start again."
                    }
                player.value!!.canComplete = false
            }
            player.value!!.selectedWords.value += newWord

            if (newWord == player.value!!.selectedWord.value) {
                player.value!!.messageResult.value = "Congratulations, you won this round!\n " +
                        "Selected Word: $newWord. \n" +
                        "Click OK to start again."
                player.value!!.isWin = true
            }
            Log.i(
                TAG,
                "Player is win: ${player.value!!.isWin}, canComplete: ${player.value!!.canComplete}"
            )
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
        fun clear(player: MutableState<PlayerController?>) {
            // Clear player state
            player.value!!.selectedWords.value = arrayOf()
            player.value!!.selectedWord.value = DataMons.getMon()
            Log.i(TAG, "selectedMon: ${player.value!!.selectedWord.value}")
            player.value!!.canComplete = true
            player.value!!.isWin = false
            player.value!!.messageResult.value = ""
        }
    }
}