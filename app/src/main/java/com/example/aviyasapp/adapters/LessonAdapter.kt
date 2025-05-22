package com.example.aviyasapp.adapters

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.LessonModel
import StudentModel
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LessonAdapter(
    private val teacherList: List<TeacherModel>,
    private val s : StudentModel,
    private val auth: FirebaseAuth
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teacherName: TextView = itemView.findViewById(R.id.student_name)
        val teacherPrice: TextView = itemView.findViewById(R.id.teacher_location)
        val registerButton: Button = itemView.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardveiw, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {

        val teacher = teacherList[position]
        holder.teacherName.text = teacher.name.toString()
        holder.teacherPrice.text = teacher.price.toString()
        holder.registerButton.setOnClickListener {
            teacher.students.add(s)
            updateTeacher(teacher)
        }
    }

    private val teacherCollectionRef = com.google.firebase.Firebase.firestore.collection("teacher")

    override fun getItemCount(): Int = teacherList.size
    private fun updateTeacher(t: TeacherModel) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    // עדכון חלקי של השדות (merge = true)
                    teacherCollectionRef.document(t.uid).set(t, SetOptions.merge()).await()


                }
            }
                catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d(TAG,"${e}")
                    }
                }

        }

}