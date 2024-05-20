package com.example.motus.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.random.Random

/**
 * Class for managing data: reads data from a .txt file, saves it in a string array, and checks if a word exists in the database.
 */
class DataMons {
    companion object {
        val TAG:String= "DataMons"
        var Mons:MutableState<Array<String>> = mutableStateOf(arrayOf())
        /**
         * Function to randomly select a word from the database. (myfile.txt)
         * Input:
         *
         * Output:
         *      1. selected Word,
         * */
        fun getMon(): String {
            val selectedMon:String = Mons.value.get(Random.nextInt(1, Mons.value.size + 1))
            Log.i(TAG, "selectedMon: $selectedMon")
            return  selectedMon
        }
        /**
         * Function to check if the word exists in the database. */
        fun hasValue(newMon:String):Boolean{
            return Mons.value.contains(newMon)
        }
        /**
         * Function to prepare the database for the game process.
         * Input:
         *      1. resourceId: path of .txt file
         *      2. context: Activity context
         *  Output:
         *      1. Array of strings containing all words in the .txt file.*/
        fun readTextFile(resourceId: Int, context:Context): String {
            val inputStream: InputStream = context.getResources().openRawResource(resourceId)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            try {
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    reader.close()
                    inputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            Mons.value= stringBuilder.toString().split("\n").toTypedArray()
            Log.i(TAG,"Mons numbers: ${Mons.value.size}")
            return stringBuilder.toString()
        }
    }

}