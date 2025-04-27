package com.example.aviyasapp.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.TeacherAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class pickTeacher : AppCompatActivity() {
    private lateinit var teachersRecyclerView: RecyclerView
    private val teacherCollectionRef = Firebase.firestore.collection("teacher")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pick_teacher)

        teachersRecyclerView = findViewById(R.id.teachers)
        teachersRecyclerView.layoutManager = LinearLayoutManager(this)

        // קריאה לפונקציה שמביאה מורים
        fetchTeachers()
    }

    private fun fetchTeachers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val teacherList = retrieveTeachers()
                withContext(Dispatchers.Main) {
                    val adapter = TeacherAdapter(teacherList) { teacher ->
                        Toast.makeText(this@pickTeacher, "נרשמת ל-${teacher.name}", Toast.LENGTH_SHORT).show()
                        // כאן אפשר להוסיף לוגיקה אמיתית לרישום
                    }
                    teachersRecyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@pickTeacher, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun retrieveTeachers(): List<TeacherModel> {
        val teacherList = mutableListOf<TeacherModel>()
        val querySnapshot = teacherCollectionRef.get().await()

        for (document in querySnapshot.documents) {
            val teacher = document.toObject<TeacherModel>()
            teacher?.let {
                teacherList.add(it)
            }
        }
        return teacherList
    }
}
