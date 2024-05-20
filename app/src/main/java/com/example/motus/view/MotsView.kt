package com.example.motus.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.*
import com.example.motus.model.PlayerController
import com.example.motus.ui.theme.*

/**
 * Class for manging the view of the Mots game.**/
class MotsView {
    companion object {
        private const val lenWord: Int = 6
        private const val emptyWord: String = "      "

        /***
         * ShowChosenWordsList: It is a LazyColumn (ListView) view used to display the game process.
         * Input:
         *      1. words: It is a string array MutableState representing the selectedWords of the PlayerController.
         *      2. selectedWord: It is a string  MutableState representing the selectedWord of the PlayerController.
         * Output:
         *      1. ListView has:
         *         1. The list of words selected by the player
         *         2. A row showing the found letters
         *         3. An empty Row
         */
        @Composable
        fun ShowChosenWordsList(
            words: MutableState<Array<String>>,
            selectedWord: MutableState<String>
        ) {
            val width: Dp = LocalConfiguration.current.screenWidthDp.dp * 0.9f
            Box(
                modifier = Modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .background(Pink40),
                contentAlignment = Alignment.Center
            ) {
                val foundLetters = StringBuilder(emptyWord)
                for (w in words.value) {
                    for (i in 0 until selectedWord.value.length) {
                        if (i < w.length) {
                            if (selectedWord.value[i] == w[i]) {
                                foundLetters.setCharAt(i, w[i])
                            }
                        }
                    }
                }
                foundLetters.setCharAt(0, selectedWord.value[0])
                LazyColumn(
                ) {
                    itemsIndexed(words.value.asList()) { index, item ->

                        ShowWord(word = item, selectedWord = selectedWord.value, width = width)
                    }
                    item {
                        ShowWord(
                            word = foundLetters.toString(),
                            selectedWord = selectedWord.value,
                            width = width
                        )
                    }
                    if ((PlayerController.attemptsNumber - words.value.size - 1) > 0) {
                        items(PlayerController.attemptsNumber - words.value.size - 1) {
                            ShowWord(
                                word = emptyWord,
                                selectedWord = selectedWord.value,
                                width = width
                            )
                        }
                    }

                }
            }
        }
        /***
         * ShowWord: It is a LazyRow (ListView) view used to display a selected word in a row of boxes.
         * Input:
         *      1. word:  It is a string that must be presented in a row of boxes.
         *      2. selectedWord: It is a string representing the selectedWord of the PlayerController.
         * Output:
         *      1. Row contains boxes with the word's letters. A box is red if the letter is at the same index in the selectedWord,
         *      yellow if the letter is in the selectedWord but at a different index, and blue otherwise.
         */
        @Composable
        fun ShowWord(word: String, selectedWord: String, width: Dp) {
            LazyRow(
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .padding(3.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(word.toCharArray().asList()) { index, item ->
                    val color = when {
                        word[index] == selectedWord.getOrNull(index) -> Color.Red
                        selectedWord.contains(word[index]) -> Color.Yellow
                        else -> Color.Blue
                    }
                    ItemChar(item.toString(), color, width / lenWord)
                }
            }
        }

        @Composable
        fun ItemChar(letter: String, color: Color, width: Dp) {
            Card(
                modifier = Modifier
                    .size(width)
                    .padding(3.dp)
                    .background(color),

                // elevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color),

                    ) {
                    Text(
                        text = letter, fontSize = 16.sp, color = Pink40
                    )
                }
            }
        }

    }
}