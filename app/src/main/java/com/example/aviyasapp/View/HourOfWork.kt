package com.example.aviyasapp.View

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.LessonModel
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.CustomAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class HourOfWork : AppCompatActivity() {
    private lateinit var ll: RecyclerView
    private lateinit var auth: FirebaseAuth
    lateinit var calanderView: CalendarView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.hours_of_work)

        calanderView = findViewById(R.id.calendarView)
        ll = findViewById(R.id.ll)
        ll.layoutManager = LinearLayoutManager(this)
        auth = com.google.firebase.ktx.Firebase.auth
        textView = findViewById(R.id.textView)

        private suspend fun retrieveLesson(): List<LessonModel> {
            val teacherCollectionRef = com.google.firebase.Firebase.firestore.collection("teacher").document(
                auth.currentUser?.uid.toString())
            var LessonList = mutableListOf<LessonModel>()
            val querySnapshot = LessonListCollectionRef.get().await()

            val teacher = querySnapshot.toObject<TeacherModel>()
            if (teacher != null) {
                studentList = teacher.students

            }
            return studentList
        }

    }
        // יצירת רשימת השעות
        val timeSlots = listOf(
            "08:00-09:00",
            "09:00-10:00",
            "10:00-11:00",
            "11:00-12:00",
            "12:00-13:00",
            "13:00-14:00"
        )

        val data = timeSlots.map { ItemsViewModel(it) }

        // הגדרת המתאם
        val adapter = CustomAdapter(data)
        ll.adapter = adapter

        // הוספת מאזין לבחירת תאריך
        calanderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // כאן תוכלי להוסיף לוגיקה לטיפול בבחירת תאריך
        }

    }
}