package com.example.aviyasapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextClock
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class login : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var create_acount_link: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        username=findViewById(R.id.username)
        password=findViewById(R.id.password)
        create_acount_link=findViewById(R.id.create_account_link)

        create_acount_link.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}