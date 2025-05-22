package com.example.aviyasapp.View

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aviyasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class loginTeacher : AppCompatActivity() {

    lateinit var create_acount_link: TextView
    private lateinit var auth: FirebaseAuth
    lateinit var button: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        create_acount_link=findViewById(R.id.create_account_link)
        button=findViewById(R.id.button)


    button.setOnClickListener {
        var email = findViewById<EditText?>(R.id.email).text
        var password = findViewById<EditText?>(R.id.full_name).text
        if (!email.isEmpty()){
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, TeacherMain::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
            create_acount_link.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

    }
        else{
            button.setOnClickListener {
                Toast.makeText(
                    baseContext,
                    "empty value",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

}
}