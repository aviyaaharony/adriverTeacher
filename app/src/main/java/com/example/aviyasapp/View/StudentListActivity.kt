package com.example.aviyasapp.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import StudentModel
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.StudentAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class StudentListActivity  : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var studentsRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_list)
        enableEdgeToEdge()
        auth = Firebase.auth
        studentsRecyclerView = findViewById(R.id.ll)
        studentsRecyclerView.layoutManager = LinearLayoutManager(this)
        fetchStudents()
    }
    private fun fetchStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentList = retrieveStudents()
                withContext(Dispatchers.Main) {
                    val adapter = StudentAdapter(studentList)
                    studentsRecyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@StudentListActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun retrieveStudents(): List<StudentModel> {
        val teacherCollectionRef = com.google.firebase.Firebase.firestore.collection("teacher").document(
            auth.currentUser?.uid.toString())
        var studentList = mutableListOf<StudentModel>()
        val querySnapshot = teacherCollectionRef.get().await()

        val teacher = querySnapshot.toObject<TeacherModel>()
        if (teacher != null) {
            studentList = teacher.students

        }
        return studentList
    }

}