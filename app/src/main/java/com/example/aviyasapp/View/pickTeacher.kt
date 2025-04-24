package com.example.aviyasapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.CustomAdapter
import com.example.aviyasapp.adapters.TeacherAdapter
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class pickTeacher : AppCompatActivity() {
    private lateinit var teachers : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pick_teacher)
        private fun retrieveTeacher () = CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = teacherCollectionRef.get().await()
                val sb = StringBuilder()
                for (document in querySnapshot.documents) {
                    val teacher = document.toObject<TeacherModel>()
                    sb.append("$teacher\n")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()

                }

            }
        }
        val data = timeSlots.map { ItemsViewModel(it)}
        teachers = findViewById(R.id.teachers)
        val adapter = TeacherAdapter(data)
        teachers.adapter = adapter





    }
}

