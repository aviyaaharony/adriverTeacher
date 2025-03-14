package com.example.aviyasapp.View
import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.aviyasapp.Model.StudentModel
import com.example.aviyasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MainActivity<FirebaseUser> : AppCompatActivity() {
    lateinit var id_number: EditText
    lateinit var username: EditText
    lateinit var birthdate: EditText
    lateinit var makeAcount: Button
    lateinit var alreadyHave: TextView
    private lateinit var auth: FirebaseAuth
    private val userCollectionRef = Firebase.firestore.collection("user")


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
        id_number = findViewById(R.id.id_number)
        username = findViewById(R.id.email)
        birthdate = findViewById(R.id.birthdate)
        makeAcount = findViewById(R.id.makeAcount)
        alreadyHave = findViewById(R.id.alreadyHave)
        makeAcount.setOnClickListener {
            var email = findViewById<EditText?>(R.id.email).text.toString()
            var password = findViewById<EditText?>(R.id.password).text.toString()
            saveUser(email, isTeacher = false)



            auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        saveUser(email, false)
                        val intent = Intent(this, choise::class.java)
                        startActivity(intent)
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

    }

    private fun saveUser(email: String, isTeacher: Boolean) =
        CoroutineScope(Dispatchers.IO).launch {
            if (!isTeacher) {
                try {
                    userCollectionRef.add(StudentModel).await()
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
}