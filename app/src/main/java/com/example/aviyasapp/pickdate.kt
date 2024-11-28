package com.example.aviyasapp

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class pickdate : AppCompatActivity() {
    lateinit var calanderView: EditText
    lateinit var hour1: EditText
    lateinit var hour2: EditText
    lateinit var hour3: EditText
    lateinit var hour4: EditText
    lateinit var hour5: EditText
    lateinit var hour6: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pickdate)
        calanderView=findViewById(R.id.calendarView)
        hour1=findViewById(R.id.hour1)
        hour2=findViewById(R.id.hour2)
        hour3=findViewById(R.id.hour3)
        hour4=findViewById(R.id.hour4)
        hour5=findViewById(R.id.hour5)
        hour6=findViewById(R.id.hour6)

    }
}