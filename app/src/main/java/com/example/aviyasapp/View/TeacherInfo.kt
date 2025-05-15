package com.example.aviyasapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aviyasapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TeacherInfo : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val teacherCollectionRef = Firebase.firestore.collection("teacher") // עם t קטנה

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_info)
        enableEdgeToEdge()
        auth = Firebase.auth

        val registerButton = findViewById<Button>(R.id.register_button12)
        val email = intent.getStringExtra("email") ?: ""

        registerButton.setOnClickListener {
            val fullName = findViewById<EditText>(R.id.full_name).text.toString()
            val price = findViewById<EditText>(R.id.price).text.toString()
            val place = findViewById<EditText>(R.id.place).text.toString()

            val checkBox5 = findViewById<CheckBox>(R.id.checkBox5)
            val checkBox6 = findViewById<CheckBox>(R.id.checkBox6)
            val checkBox7 = findViewById<CheckBox>(R.id.checkBox7)
            val checkBox8 = findViewById<CheckBox>(R.id.checkBox8)
            val checkBox9 = findViewById<CheckBox>(R.id.checkBox9)
            val checkBox = findViewById<CheckBox>(R.id.checkBox)

            val selectedDays = mutableListOf<String>()
            if (checkBox9.isChecked) selectedDays.add("ראשון")
            if (checkBox8.isChecked) selectedDays.add("שני")
            if (checkBox7.isChecked) selectedDays.add("שלישי")
            if (checkBox6.isChecked) selectedDays.add("רביעי")
            if (checkBox5.isChecked) selectedDays.add("חמישי")
            if (checkBox.isChecked) selectedDays.add("שישי")

            if (fullName.isBlank() || price.isBlank() || place.isBlank()) {
                Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val priceInt = price.toIntOrNull()
            if (priceInt == null) {
                Toast.makeText(this, "המחיר חייב להיות מספר", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = auth.currentUser
            if (currentUser == null) {
                Toast.makeText(this, "אירעה שגיאה בזיהוי המשתמש", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val teacherId = currentUser.uid

            // שמירה של כל הנתונים, כולל הימים, בפעולה אחת
            saveTeacherToDocument(teacherId, fullName, email, priceInt, place, selectedDays)

            val intent = Intent(this@TeacherInfo, TeacherMain::class.java)
            startActivity(intent)
        }
    }

    private fun saveTeacherToDocument(
        uid: String,
        name: String,
        email: String,
        price: Int,
        place: String,
        selectedDays: List<String>
    ) = CoroutineScope(Dispatchers.IO).launch {
        val teacherData = hashMapOf(
            "fullName" to name,
            "email" to email,
            "price" to price,
            "place" to place,
            "hourOfWork" to selectedDays
        )

        try {
            teacherCollectionRef.document(uid).set(teacherData).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@TeacherInfo, "הפרטים נשמרו בהצלחה", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@TeacherInfo, "שגיאה: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
