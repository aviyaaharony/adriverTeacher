package com.example.aviyasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class choise  : AppCompatActivity() {
    lateinit var setLesson: Button
    lateinit var state: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.choise)
        setLesson = findViewById(R.id.setLesson)
        state = findViewById(R.id.state)
        setLesson.setOnClickListener {
            val intent = Intent(this, pickdate::class.java)
            startActivity(intent)
        }

        state.setOnClickListener {
            val intent = Intent(this, page5::class.java)
            startActivity(intent)
        }
    }
}