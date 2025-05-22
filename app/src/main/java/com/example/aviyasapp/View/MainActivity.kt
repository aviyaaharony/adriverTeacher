package com.example.aviyasapp.View
import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import StudentModel
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.StringBuilder


class MainActivity<FirebaseUser> : AppCompatActivity() {
    lateinit var id_number: EditText
    lateinit var username: EditText
    lateinit var birthdate: EditText
    lateinit var makeAcount: Button
    lateinit var alreadyHave: TextView
    lateinit var alreadyHave2: TextView
    lateinit var checkBox: CheckBox
    private lateinit var auth: FirebaseAuth
    private val userCollectionRef = Firebase.firestore.collection("Student")
    private val teacherCollectionRef = Firebase.firestore.collection("teacher")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize Firebase Auth
        auth = Firebase.auth

        // getting the recyclerview by its id


        enableEdgeToEdge()
        val dateFormat = DateFormat.getDateFormat(
            applicationContext
        )

        setContentView(R.layout.activity_main)
        id_number = findViewById(R.id.price)
        username = findViewById(R.id.email)
        birthdate = findViewById(R.id.birthdate)
        makeAcount = findViewById(R.id.makeAcount)
        alreadyHave = findViewById(R.id.alreadyHave)
        alreadyHave2 = findViewById(R.id.alreadyHave2)

        checkBox = findViewById(R.id.checkBox)
         makeAcount.setOnClickListener {
            var email = findViewById<EditText?>(R.id.email).text.toString()
            var password = findViewById<EditText?>(R.id.full_name).text.toString()


            auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        if (!checkBox.isChecked) {
                            saveUser(email, isTeacher = false)
                            val intent = Intent(this, choise::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this,TeacherInfo::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()


                    }
                }

        }
        alreadyHave.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        alreadyHave2.setOnClickListener {
            val intent = Intent(this, loginTeacher::class.java)
            startActivity(intent)
        }
    }


    private fun saveUser(email: String, isTeacher: Boolean) =
        CoroutineScope(Dispatchers.IO).launch {
            val student = StudentModel(email, isTeacher)
            if (!isTeacher) {
                try {
                    userCollectionRef.add(student).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "successfully saved data.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    private fun saveTeacher(email: String, isTeacher: Boolean) =
        CoroutineScope(Dispatchers.IO).launch {
            val teacher = TeacherModel(email)
            if (isTeacher) {
                try {
                    teacherCollectionRef.add(teacher).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "successfully saved data.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    private fun retrieveStudent() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = userCollectionRef.get().await()
            val sb = StringBuilder()
            for (document in querySnapshot.documents) {
                val student = document.toObject<StudentModel>()
                sb.append("$student\n")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()

            }

        }
    }

        private fun retrieveTeacher () = CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = teacherCollectionRef.get().await()
                val sb = StringBuilder()
                for (document in querySnapshot.documents) {
                    val teacher = document.toObject<TeacherModel>()
                    sb.append("$teacher\n")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()

                }

            }
        }
}