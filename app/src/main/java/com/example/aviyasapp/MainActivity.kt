package com.example.aviyasapp

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var id_number:EditText
    lateinit var username:EditText
    lateinit var password:EditText
    lateinit var birthdate:EditText
    lateinit var email:EditText
    lateinit var alreadyHave:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        id_number=findViewById(R.id.id_number)
        username=findViewById(R.id.username)
        password=findViewById(R.id.password)
        birthdate=findViewById(R.id.birthdate)
        email=findViewById(R.id.email)
        alreadyHave=findViewById(R.id.alreadyHave)


    }
}