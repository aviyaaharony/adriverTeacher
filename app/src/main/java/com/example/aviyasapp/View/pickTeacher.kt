package com.example.aviyasapp.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.CustomAdapter

class pickTeacher : AppCompatActivity() {
    private lateinit var teachers : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pickdate)
        val timeSlots = listOf(
            "08:00-09:00",
            "09:00-10:00",
            "10:00-11:00",
            "11:00-12:00",
            "12:00-13:00",
            "13:00-14:00"
        )
        val data = timeSlots.map { ItemsViewModel(it)}
        teachers = findViewById(R.id.recyclerView)
        val adapter = CustomAdapter(data)
        teachers.adapter = adapter





    }
}

