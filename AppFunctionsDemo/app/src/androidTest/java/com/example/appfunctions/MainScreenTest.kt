package com.example.appfunctions

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainScreen_displaysCorrectMessage() {
        val testMessage = "Hello, Test!"
        // Set the content for the test
        composeTestRule.setContent {
            MainScreen(message = testMessage)
        }

        // Check if the text is displayed
        composeTestRule.onNodeWithText(testMessage).assertIsDisplayed()
    }
}
