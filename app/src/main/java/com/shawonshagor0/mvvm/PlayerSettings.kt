package com.shawonshagor0.mvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer

class PlayerSettings : AppCompatActivity() {

    lateinit var playerOneName: EditText
    lateinit var playerTwoName: EditText
    lateinit var startButton: Button
    var nameViewModel: NameViewModel = NameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playerOneName = findViewById(R.id.etNameOne)
        playerTwoName = findViewById(R.id.etNameTwo)
        startButton = findViewById(R.id.button)




        startButton.setOnClickListener {
            var pNameOne = playerOneName.text.toString()
            var pNameTwo = playerTwoName.text.toString()
            nameViewModel.setNames(pNameOne, pNameTwo)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}