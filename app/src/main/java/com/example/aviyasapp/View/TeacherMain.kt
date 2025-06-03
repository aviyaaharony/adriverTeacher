package com.example.aviyasapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aviyasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TeacherMain  : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var studentList: Button
    lateinit var setLessons: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_main)
        enableEdgeToEdge()
        auth = Firebase.auth
        studentList = findViewById<Button?>(R.id.studentList)
        setLessons = findViewById<Button?>(R.id.setLessons)

        studentList.setOnClickListener {
            val intent = Intent(this, StudentListActivity::class.java)
            startActivity(intent)
        }
        setLessons.setOnClickListener {
            val intent = Intent(this, HourOfWork::class.java)
            startActivity(intent)
        }

    }


}