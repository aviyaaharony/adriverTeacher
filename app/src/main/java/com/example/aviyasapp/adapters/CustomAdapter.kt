package com.example.aviyasapp.adapters

import StudentModel
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.LessonModel
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class CustomAdapter(
    private val mList       : MutableList<ItemsViewModel>,
    private val auth        : FirebaseAuth,
    private val student     : StudentModel,
    private val selectedDate: String
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val teacherRef = Firebase.firestore.collection("teacher")

    /** ViewHolder */
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val hourText  : TextView = v.findViewById(R.id.hourText)
        val bookButton: Button   = v.findViewById(R.id.bookButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.hour_aviable, parent, false)
    )

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.hourText.text = item.text
        holder.bookButton.setOnClickListener { bookLesson(item.text, position) }
    }

    /** קובע שיעור ושומר בפיירבייס */
    private fun bookLesson(hour: String, pos: Int) = CoroutineScope(Dispatchers.IO).launch {
        val teacherUid = student.teacherUid ?: return@launch
        val teacher    = getTeacher(teacherUid) ?: return@launch

        /* ----- מניעת כפילות ----- */
        val duplicate = teacher.lessons.any {
            it.hour == hour && it.date == selectedDate &&
                    it.student.email == student.email
        }

        /* ----- מניעת הזמנה אם תפוס ----- */
        val alreadyTaken = teacher.lessons.any {
            it.hour == hour && it.date == selectedDate && !it.isAvailable
        }

        /* ----- יצירת שיעור ----- */
        val newLesson = LessonModel(hour, selectedDate, false, student)

        val updated   = teacher.lessons.toMutableList().apply { add(newLesson) }

        teacherRef.document(teacherUid)
            .set(teacher.copy(lessons = updated), SetOptions.merge()).await()

        /* ----- עדכון RecyclerView ----- */
        withContext(Dispatchers.Main) {
            mList.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }

    private suspend fun getTeacher(uid: String): TeacherModel? = try {
        teacherRef.document(uid).get().await().toObject<TeacherModel>()
    } catch (e: Exception) { null }
}
