package com.example.appfunctions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() { // Changed superclass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Call the AppFunction
            val part1 = "Hello, "
            val part2 = "AppFunction!"
            val result = concatenateStrings(part1, part2)

            // Display the result using a Composable function
            MainScreen(message = result)
        }
    }
}
