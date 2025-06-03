package com.example.aviyasapp.View

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import StudentModel
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.TeacherAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.StringBuilder
class pickTeacher : AppCompatActivity() {

    private lateinit var rv      : RecyclerView
    private lateinit var auth    : FirebaseAuth
    private val teacherColRef    = Firebase.firestore.collection("teacher")
    private val studentColRef    = Firebase.firestore.collection("Student")

    private lateinit var student : StudentModel   // ← נטען ואז נמשיך

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pick_teacher)

        auth = Firebase.auth
        rv   = findViewById(R.id.teachers)
        rv.layoutManager = LinearLayoutManager(this)

        loadStudentAndThenTeachers()
    }


    private fun loadStudentAndThenTeachers() = CoroutineScope(Dispatchers.IO).launch {

        /* --- שליפת תלמיד --- */
        val stuDoc = studentColRef.document(auth.currentUser!!.uid).get().await()
        student    = stuDoc.toObject<StudentModel>() ?: StudentModel()

        /* --- שליפת מורים --- */
        val teacherDocs = teacherColRef.get().await().documents
        val teachers    = teacherDocs.mapNotNull { it.toObject<TeacherModel>() }

        /* --- מי מהם כבר מכיל את התלמיד? --- */
        val assignedTeacherUid = teachers
            .firstOrNull { t -> t.students.any { it.uid == student.uid } }
            ?.uid                                 // null => אין מורה

        withContext(Dispatchers.Main) {
            rv.adapter = TeacherAdapter(
                teachers,
                student,
                assignedTeacherUid,               // ← פרמטר חדש
                auth
            )
        }
    }

}
