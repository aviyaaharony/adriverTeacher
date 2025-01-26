package com.example.aviyasapp.View

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aviyasapp.R

class page5 : AppCompatActivity() {
    lateinit var button10: Button
    lateinit var textView4: TextView
    lateinit var textView5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.page5)
        button10=findViewById(R.id.button10)
        textView4=findViewById(R.id.textView4)
        textView5=findViewById(R.id.textView5)
    }
}