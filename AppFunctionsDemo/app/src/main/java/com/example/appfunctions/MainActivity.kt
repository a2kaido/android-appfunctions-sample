package com.example.appfunctions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appfunctions.databinding.ActivityMainBinding // Correct ViewBinding import

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Call the AppFunction
        val part1 = "Hello, "
        val part2 = "AppFunction!"
        val result = concatenateStrings(part1, part2)

        // Display the result in the TextView using ViewBinding
        binding.greetingTextView.text = result
    }
}
