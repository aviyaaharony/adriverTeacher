package com.example.aviyasapp.View

import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.CustomAdapter

class pickdate : AppCompatActivity() {
    lateinit var calanderView: CalendarView
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pickdate)
        
        calanderView = findViewById(R.id.calendarView)
        recyclerview = findViewById(R.id.recyclerView)

        // הגדרת LayoutManager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // יצירת רשימת השעות
        val timeSlots = listOf(
            "08:00-09:00",
            "09:00-10:00",
            "10:00-11:00",
            "11:00-12:00",
            "12:00-13:00",
            "13:00-14:00"
        )

        // יצירת רשימת הפריטים
        val data = timeSlots.map { ItemsViewModel(it) }

        // הגדרת המתאם
        val adapter = CustomAdapter(data)
        recyclerview.adapter = adapter

        // הוספת מאזין לבחירת תאריך
        calanderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // כאן תוכלי להוסיף לוגיקה לטיפול בבחירת תאריך
        }
    }
}