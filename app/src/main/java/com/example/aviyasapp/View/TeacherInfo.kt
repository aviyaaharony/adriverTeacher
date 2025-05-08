package com.example.aviyasapp.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aviyasapp.Model.TeacherModel
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
    lateinit var price: String
    lateinit var place: String
    lateinit var full_name: String
    lateinit var register_button: Button
    lateinit var textView6: TextView
    private val teacherCollectionRef = Firebase.firestore.collection("teacher")
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_info)
        enableEdgeToEdge()
        auth = Firebase.auth
        register_button = findViewById<Button?>(R.id.register_button12)

        var email = intent.getStringExtra("email")
        register_button.setOnClickListener {
            full_name = findViewById<EditText>(R.id.full_name).text.toString()
            price = findViewById<EditText>(R.id.price).text.toString()
            place = findViewById<EditText>(R.id.place).text.toString()

            // בדיקה שהשדות לא ריקים
            if (full_name.isBlank() || price.isBlank() || place.isBlank()) {
                Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // נסיון להמיר מחיר למספר
            val priceInt = price.toIntOrNull()
            if (priceInt == null) {
                Toast.makeText(this, "המחיר חייב להיות מספר", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveTeacher(email?: "",full_name, price, place)
            val intent = Intent(this@TeacherInfo, TeacherMain::class.java)
            startActivity(intent)
        }

    }
    private fun saveTeacher(email: String,name:String, price: String,place :String) =
        CoroutineScope(Dispatchers.IO).launch {
            val teacher = TeacherModel(name,email,price.toInt(),place)
                try {
                    teacherCollectionRef.add(teacher).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@TeacherInfo,
                            "successfully saved data.",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@TeacherInfo, e.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

