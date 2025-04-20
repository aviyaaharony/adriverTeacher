package com.example.aviyasapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aviyasapp.R

class choise  : AppCompatActivity() {
    lateinit var setLesson: Button
    lateinit var state: Button
    lateinit var setTeacher: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.choise)
        setLesson = findViewById(R.id.setLesson3)
        state = findViewById(R.id.state)
        setTeacher = findViewById(R.id.setTeacher)
        setLesson.setOnClickListener {
            val intent = Intent(this, pickdate::class.java)
            startActivity(intent)
        }

        state.setOnClickListener {
            val intent = Intent(this, page5::class.java)
            startActivity(intent)
        }

        setTeacher.setOnClickListener {
            val intent = Intent(this, pickTeacher::class.java)
            startActivity(intent)
        }
    }
}