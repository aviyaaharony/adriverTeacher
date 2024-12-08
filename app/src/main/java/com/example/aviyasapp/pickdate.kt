package com.example.aviyasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class pickdate : AppCompatActivity() {
    lateinit var calanderView: CalendarView
    lateinit var hour1: Button
    lateinit var hour2: Button
    lateinit var hour3: Button
    lateinit var hour4: Button
    lateinit var hour5: Button
    lateinit var hour6: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pickdate)
        calanderView = findViewById(R.id.calendarView)
        hour1 = findViewById(R.id.hour1)
        hour2 = findViewById(R.id.hour2)
        hour3 = findViewById(R.id.hour3)
        hour4 = findViewById(R.id.hour4)
        hour5 = findViewById(R.id.hour5)
        hour6 = findViewById(R.id.hour6)

        calanderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // כאן אפשר להוסיף לוגיקה לטיפול בבחירת תאריך
        }

        val hourButtons = listOf(hour1, hour2, hour3, hour4, hour5, hour6)
        hourButtons.forEach { button ->
            button.setOnClickListener {
                val intent = Intent(this, teacher::class.java)
                startActivity(intent)
            }
        }
    }
}