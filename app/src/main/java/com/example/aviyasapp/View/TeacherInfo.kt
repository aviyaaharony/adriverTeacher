package com.example.aviyasapp.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
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
    lateinit var textView9: TextView
    lateinit var textView10: TextView
    lateinit var textView11: TextView
    lateinit var textView12: TextView
    lateinit var textView13: TextView
    lateinit var textView14: TextView
    lateinit var checkBox5: CheckBox
    lateinit var checkBox6: CheckBox
    lateinit var checkBox7: CheckBox
    lateinit var checkBox8: CheckBox
    lateinit var checkBox9: CheckBox
    lateinit var checkBox: CheckBox

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
            textView6 = findViewById<EditText>(R.id.textView6)
            textView9 = findViewById<EditText>(R.id.textView9)
            textView10 = findViewById<EditText>(R.id.textView10)
            textView11 = findViewById<EditText>(R.id.textView11)
            textView12 = findViewById<EditText>(R.id.textView12)
            textView13 = findViewById<EditText>(R.id.textView13)
            textView14 = findViewById<EditText>(R.id.textView14)
            checkBox5 = findViewById(R.id.checkBox5)
            checkBox6 = findViewById(R.id.checkBox6)
            checkBox7 = findViewById(R.id.checkBox7)
            checkBox8 = findViewById(R.id.checkBox8)
            checkBox9 = findViewById(R.id.checkBox9)
            checkBox = findViewById(R.id.checkBox)



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

