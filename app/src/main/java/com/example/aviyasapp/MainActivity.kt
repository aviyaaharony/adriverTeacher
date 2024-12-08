package com.example.aviyasapp

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var id_number: EditText
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var birthdate: EditText
    lateinit var email: EditText
    lateinit var makeAcount: Button
    lateinit var alreadyHave: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dateFormat = DateFormat.getDateFormat(
            applicationContext
        )

        setContentView(R.layout.activity_main)
        id_number = findViewById(R.id.id_number)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        birthdate = findViewById(R.id.birthdate)
        email = findViewById(R.id.email)
        makeAcount = findViewById(R.id.makeAcount)
        alreadyHave = findViewById(R.id.alreadyHave)
        makeAcount.setOnClickListener {
            val intent = Intent(this, choise::class.java)
            startActivity(intent)
        }
        alreadyHave.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}