package com.example.motus

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.motus.model.PlayerController

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.motus", appContext.packageName)
    }
    @Test
    fun testWordChoseBefore(){
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
        // Assert that the text in the TextInputField has changed to "Button Clicked"
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.text_input)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).assertIsDisplayed()

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput("ABAQUE")
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number)).assertIsDisplayed()
        //composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number_text)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number)).assertTextEquals("Attempts number: 2/${PlayerController.attemptsNumber}")

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput("ABAQUE")
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()

        //Check exist message_widget
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget)).assertIsDisplayed()
        //Check error message
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_text)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_text)).assertTextEquals(
            "You have lost the game. Better luck next time! \n" +
                "Reason: You chose this word before. \n"+
                "Selected Word: ABAQUE. \n"+
                "Click OK to start again.")
        //Check exist button
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).performClick()
    }
    @Test
    fun testWordSpellingError(){
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
        // Assert that the text in the TextInputField has changed to "Button Clicked"
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.text_input)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).assertIsDisplayed()

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput("ABAQUE")
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number)).assertIsDisplayed()
        //composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number_text)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number)).assertTextEquals("Attempts number: 2/${PlayerController.attemptsNumber}")

        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput("XXXXXX")
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()

        //Check exist message_widget
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget)).assertIsDisplayed()
        //Check error message
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_text)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_text)).assertTextEquals(
            "You have lost the game. Better luck next time! \n" +
                    "Reason: Spelling error. \n"+
                    "Selected Word: XXXXXX. \n"+
                    "Click OK to start again.")
        //Check exist button
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).performClick()
    }
    @Test
    fun testAttemptError(){
        val inputWords:Array<String> = arrayOf("AbSIDE", "ABSOLu", "ABSOUS", "ABSOUT", "ABUSAI", "aBUSAS")
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).assertIsDisplayed()
        for(i in 0 until inputWords.size){
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput(inputWords.get(i))
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number)).
            assertTextEquals("Attempts number: ${i+2}/${PlayerController.attemptsNumber}")

        }
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput("ABUSAT")
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_text)).assertTextEquals(
            "You have lost the game. Better luck next time! \n" +
                    "Reason: You used all your attempts. \n"+
                    "Selected Word: ABUSAT. \n"+
                    "Click OK to start again.")
        //Check exist button
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).performClick()
    }
    @Test
    fun testWin(){
        val inputWords:Array<String> = arrayOf("ABSIDE", "ABSOLU", "ABSOUS", "ABSOUT", "ABUSAI", "ABUSAS")
        val correctWord:String= composeTestRule.activity.player.value!!.selectedWord.value
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).assertIsDisplayed()
        for(i in 0 until inputWords.size){
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput(inputWords.get(i))
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()
            composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number)).
            assertTextEquals("Attempts number: ${i+2}/${PlayerController.attemptsNumber}")

        }
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput(correctWord)
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_text)).assertTextEquals(
            "Congratulations, you won this round!\n "+
                    "Selected Word: $correctWord. \n"+
                    "Click OK to start again.")
        //Check exist button
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).performClick()
    }
    @Test
    fun testPlayNext(){
        val correctWord:String= composeTestRule.activity.player.value!!.selectedWord.value
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.play_button)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.edit_text_input)).performTextInput(correctWord)
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.button_text_input)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_text)).assertTextEquals(
            "Congratulations, you won this round!\n "+
                    "Selected Word: $correctWord. \n"+
                    "Click OK to start again.")
        //Check exist button
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.message_widget_button)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.attempts_number)).assertTextEquals("Attempts number: 1/${PlayerController.attemptsNumber}")
    }
}