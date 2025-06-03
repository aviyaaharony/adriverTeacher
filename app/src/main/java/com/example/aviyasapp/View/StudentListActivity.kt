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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
class StudentListActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var rv: RecyclerView
    private lateinit var adapter: StudentAdapter      // ← נשמר כדי שנוכל לקרוא ל-notify

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.student_list)

        auth = Firebase.auth
        rv   = findViewById(R.id.ll)
        rv.layoutManager = LinearLayoutManager(this)

        loadStudents()
    }

    private fun loadStudents() = CoroutineScope(Dispatchers.IO).launch {
        val students = getStudents().toMutableList()          // ← ‎Mutable
        withContext(Dispatchers.Main) {
            adapter = StudentAdapter(
                students,
                auth.currentUser!!.uid
            ) { pos -> adapter.notifyItemRemoved(pos) }       // callback
            rv.adapter = adapter
        }
    }

    private suspend fun getStudents(): List<StudentModel> {
        val doc = Firebase.firestore.collection("teacher")
            .document(auth.currentUser!!.uid).get().await()

        return doc.toObject<TeacherModel>()?.students ?: emptyList()
    }
}
