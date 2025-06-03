package com.example.aviyasapp.View

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.LessonModel
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.ItemsViewModel      // (אם אתה כבר לא משתמש בזה – אפשר למחוק)
import com.example.aviyasapp.adapters.LessonAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class HourOfWork : AppCompatActivity() {

    private lateinit var rvLessons : RecyclerView
    private lateinit var auth      : FirebaseAuth
    private lateinit var calendar  : CalendarView
    private lateinit var titleTxt  : TextView

    /** התאריך שנבחר בקובץ – במבנה dd-MM-yyyy */
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.hours_of_work)

        // איתחול ממשק
        calendar   = findViewById(R.id.calendarView)
        rvLessons  = findViewById(R.id.ll)
        titleTxt   = findViewById(R.id.textView)
        rvLessons.layoutManager = LinearLayoutManager(this)

        auth = com.google.firebase.ktx.Firebase.auth

        /* ----- מאזין ללוח-שנה ----- */
        calendar.setOnDateChangeListener { _, year, month, day ->
            val cal = Calendar.getInstance().apply { set(year, month, day) }
            selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(cal.time)
            titleTxt.text = "שיעורים ל-$selectedDate"
            fetchLessonsForDate(selectedDate!!)
        }

        /* פתיחה ראשונית – היום הנוכחי */
        val today = Calendar.getInstance()
        selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(today.time)
        titleTxt.text = "שיעורים ל-$selectedDate"
        fetchLessonsForDate(selectedDate!!)
    }

    /** שליפה וסינון של שיעורי המורה בתאריך המבוקש */
    private fun fetchLessonsForDate(date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val lessonsList = retrieveLessons(date)        // ← מחזיר רק של התאריך הזה
                withContext(Dispatchers.Main) {
                    rvLessons.adapter = LessonAdapter(lessonsList.toMutableList(), auth)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HourOfWork, e.message, Toast.LENGTH_SHORT).show()
                    Log.e("HourOfWork", e.toString())
                }
            }
        }
    }

    /**
     *  קורא למסמך המורה ומחזיר רק שיעורים שתואמים ל-date
     */
    private suspend fun retrieveLessons(date: String): List<LessonModel> {
        val teacherDocRef = com.google.firebase.Firebase.firestore
            .collection("teacher")
            .document(auth.currentUser?.uid ?: return emptyList())

        val doc      = teacherDocRef.get().await()
        val teacher  = doc.toObject<TeacherModel>() ?: return emptyList()

        // פילטר לפי תאריך שנבחר (אפשר להוסיף !lesson.available אם רוצים רק שיעורים תפוסים)
        return teacher.lessons.filter { it.date == date }
    }
}
