package com.example.aviyasapp.View

import StudentModel
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.example.aviyasapp.adapters.CustomAdapter
import com.example.aviyasapp.adapters.ItemsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import java.text.SimpleDateFormat   // ← הוסף בראש הקובץ

class pickdate : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView
    private lateinit var auth: FirebaseAuth

    private val studentColRef = Firebase.firestore.collection("Student")
    private var teacherWorkDays: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pickdate)

        auth          = Firebase.auth
        calendarView  = findViewById(R.id.calendarView)
        recyclerView  = findViewById(R.id.recyclerView)
        textView      = findViewById(R.id.textView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // שליפת תלמיד + מורה
        CoroutineScope(Dispatchers.Main).launch {
            val student = getStudent() ?: return@launch
            val teacher = student.teacherUid?.let { getTeacher(it) } ?: return@launch

            teacherWorkDays = teacher.hourOfWork ?: emptyList()
            Toast.makeText(this@pickdate, "בחר תאריך להצגת שעות", Toast.LENGTH_SHORT).show()

            calendarView.setOnDateChangeListener { _, y, m, d -> onDatePicked(y, m, d, student) }
        }
    }

    /** הפונקציה שמופעלת בעת בחירת תאריך בלוח */
    private fun onDatePicked(year: Int, month: Int, day: Int, student: StudentModel) {
        val cal = Calendar.getInstance().apply { set(year, month, day) }

        val engDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)
        val hebNames = mapOf(
            "Sunday" to "ראשון", "Monday" to "שני", "Tuesday" to "שלישי",
            "Wednesday" to "רביעי", "Thursday" to "חמישי", "Friday" to "שישי", "Saturday" to "שבת"
        )
        val hebDay = hebNames[engDay] ?: return

        // ⬅️ תאריך מלא  dd-MM-yyyy   למשל  03-06-2025
        val dateFmt = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(cal.time)

        textView.text = "שעות פנויות ליום: $hebDay"

        if (!teacherWorkDays.contains(hebDay)) {
            Toast.makeText(this, "המורה לא עובד ביום $hebDay", Toast.LENGTH_SHORT).show()
            recyclerView.adapter = null
            return
        }

        val allHours = (9 until 18).map { "%02d:00-%02d:00".format(it, it + 1) }

        CoroutineScope(Dispatchers.Main).launch {
            val taken = getTakenHours(student.teacherUid!!, dateFmt)
            val free  = allHours.filterNot { taken.contains(it) }

            val data = free.map { ItemsViewModel(it) }
            recyclerView.adapter = CustomAdapter(data.toMutableList(), auth, student, dateFmt)
        }
    }


    /** ============ Helper Coroutines ============ */

    private suspend fun getStudent(): StudentModel? = try {
        auth.currentUser?.let {
            studentColRef.document(it.uid).get().await().toObject<StudentModel>()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "שגיאת תלמיד: ${e.message}", Toast.LENGTH_SHORT).show()
        null
    }

    private suspend fun getTeacher(uid: String): TeacherModel? = try {
        Firebase.firestore.collection("teacher")
            .document(uid).get().await().toObject<TeacherModel>()
    } catch (e: Exception) {
        Toast.makeText(this, "שגיאת מורה: ${e.message}", Toast.LENGTH_SHORT).show()
        null
    }

    /** שליפת שעות שכבר תפוסות בתאריך */
    private suspend fun getTakenHours(teacherUid: String, date: String): List<String> = try {
        val lessons = Firebase.firestore.collection("teacher")
            .document(teacherUid).get().await()
            .toObject<TeacherModel>()?.lessons ?: emptyList()

        lessons.filter { !it.isAvailable && it.date == date }
            .map   { it.hour }
    } catch (e: Exception) { emptyList() }

}
