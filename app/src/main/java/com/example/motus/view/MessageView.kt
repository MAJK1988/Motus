package com.example.motus.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.motus.R

class MessageView {
    companion object {
        private const val TAG:String= "MessageView"
        /***
         * EnterNewWord: It is an AlertDialog view used by the player to enter a new word.
         * Input:
         *      1. shouldShowDialog: It is a boolean MutableState used to control the visibility of the AlertDialog view.
         *      2. word: It is a string MutableState representing the newWord of the PlayerController.
         * Output:
         *      1.An AlertDialog view used by player to enter a new word.*/
        @Composable
        fun EnterNewWord(
            shouldShowDialog: MutableState<Boolean>,
            word: MutableState<String>,
            context: Context,
            onTextEntered: (String) -> Unit
        ) {

            if (shouldShowDialog.value) { // 2
                AlertDialog( //
                    modifier = Modifier.testTag(context.getString(R.string.text_input)),
                    onDismissRequest = { // 4
                        // shouldShowDialog.value = false
                    },
                    // 5
                    title = { Text(text = "Alert Dialog") },
                    text = {
                        TextField(
                            value = word.value,
                            onValueChange = { word.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .testTag(context.getString(R.string.edit_text_input))
                        )
                    },

                    confirmButton = { // 6
                        Button(
                            modifier = Modifier.testTag(context.getString(R.string.button_text_input)),
                            onClick = {
                                shouldShowDialog.value = false
                                onTextEntered(word.value.uppercase())
                                word.value = ""
                            }
                        ) {
                            Text(
                                text = "Confirm",
                                color = Color.White
                            )
                        }
                    }
                )
            }
        }
        /***
         * ShowMessage: It is an AlertDialog view used to display the round result.
         * Input:
         *      1. shouldShowDialog: It is a boolean MutableState used to control the visibility of the AlertDialog view.
         *      2. message: It is a string MutableState representing the messageResult of the PlayerController.
         * Output:
         *      1.An AlertDialog view used to display the result of the round and reset the player's state.*/
        @Composable
        fun ShowMessage(
            shouldShowDialog: MutableState<Boolean>,
            message: MutableState<String>, context: Context,
            onEntered: () -> Unit
        ) {
            Log.i(TAG, "message: $message")

            if (shouldShowDialog.value) { // 2
                AlertDialog(
                    modifier = Modifier.testTag(context.getString(R.string.message_widget)),
                    onDismissRequest = { // 4
                        // shouldShowDialog.value = false
                    },
                    // 5
                    title = { Text(text = "Game Over") },
                    text = {
                        Text(
                            text = message.value,
                            modifier = Modifier.testTag(context.getString(R.string.message_widget_text))
                        )
                    },

                    confirmButton = { // 6
                        Button(
                            modifier = Modifier.testTag(context.getString(R.string.message_widget_button)),
                            onClick = {
                                shouldShowDialog.value = false
                                onEntered()
                            }
                        ) {
                            Text(
                                text = "OK",
                                color = Color.White
                            )
                        }
                    }
                )
            }
        }
    }
}