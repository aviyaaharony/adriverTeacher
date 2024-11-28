package com.example.aviyasapp

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class choise  : AppCompatActivity() {
    lateinit var setLesson: EditText
    lateinit var state:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.choise)
        setLesson=findViewById(R.id.setLesson)
        state=findViewById(R.id.state)


    }
}