package com.example.motus.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import com.example.motus.R
import com.example.motus.controller.ControllerPlayer
import com.example.motus.model.PlayerController
import com.example.motus.ui.theme.Pink40
/***
 * Class for manging the view of the main screen.*/
class ScreenView {
    companion object {
        private val shortInputMon: MutableState<Boolean> = mutableStateOf(false)
        private val shortMessage: MutableState<Boolean> = mutableStateOf(false)
        @Composable
        fun ScreenView(player: MutableState<PlayerController?>, context: Context) {
            Column {
                Row(
                    Modifier
                        .width(LocalConfiguration.current.screenWidthDp.dp)

                        .background(
                            Pink40
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    /***
                     * The player must click this button to show the EnterNewWord view, which is used to enter a new word.
                     */
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .testTag(context.getString(R.string.play_button)),
                        onClick = {
                            shortInputMon.value = true// Show input dialog
                            shortMessage.value = false
                        }
                    ) {
                        val text = when {
                            // Determine button text based on player state
                            player.value!!.selectedWords.value.isEmpty() -> "Star play"
                            else -> "Try again.!"
                        }
                        Text(text = text)
                    }
                    /***
                     * A view used to show the player's number of attempts.
                     */
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .testTag(context.getString(R.string.attempts_number)),
                        onClick = {}) {
                        Text(
                            text = "Attempts number: ${player.value!!.selectedWords.value.size + 1}/${PlayerController.attemptsNumber}",
                            modifier = Modifier.testTag(context.getString(R.string.attempts_number_text)),
                        )
                    }
                }
                /***
                 * MessageView.ShowChosenWordsList: It is a LazyColumn (ListView) view used to display the game process.
                 */
                MotsView.ShowChosenWordsList(
                    words = player.value!!.selectedWords, selectedWord = player.value!!.selectedWord
                )
                /***
                 * MessageView.EnterNewWord: It is an AlertDialog view used by the player to enter a new word.
                 */
                MessageView.EnterNewWord(
                    shouldShowDialog =shortInputMon,
                    word = player.value!!.newWord, context = context
                ) { newValue ->
                    run {
                        ControllerPlayer.updatePlayerState(newValue, player)
                        if (!player.value!!.canComplete || player.value!!.isWin) {
                            shortMessage.value =
                                true// Show short message if game is won or cannot be completed
                        }
                    }
                }

                /***
                 * MessageView.ShowMessage: It is an AlertDialog view used to display the round result.
                 */
                MessageView.ShowMessage(
                    shouldShowDialog = shortMessage, context = context,
                    message = player.value!!.messageResult
                ) { ->
                    run {
                        shortInputMon.value = false // Clear input dialog state
                        shortMessage.value = false // Clear message dialog state
                        ControllerPlayer.clear(player) // Clear player state
                    }
                }
            }
        }
    }
}