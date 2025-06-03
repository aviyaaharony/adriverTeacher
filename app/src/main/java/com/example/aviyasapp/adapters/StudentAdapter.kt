package com.example.aviyasapp.adapters

import StudentModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class StudentAdapter(
    private val students   : MutableList<StudentModel>,
    private val teacherUid : String,
    private val onRemoved  : (Int) -> Unit              // callback ל-UI
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nameTxt : TextView = v.findViewById(R.id.student_name)
        val delBtn  : Button   = v.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) = ViewHolder(
        LayoutInflater.from(p.context).inflate(R.layout.student_card, p, false)
    )

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(h: ViewHolder, pos: Int) {
        val st = students[pos]
        h.nameTxt.text = st.name
        h.delBtn.setOnClickListener { removeStudent(pos, st) }
    }

    /** מוחק מהרשימה ב-UI ומ-Firestore */
    private fun removeStudent(pos: Int, st: StudentModel) = CoroutineScope(Dispatchers.IO).launch {
        val ref = Firebase.firestore.collection("teacher").document(teacherUid)

        // שליפת המסמך → סינון → עדכון
        val teach = ref.get().await().toObject(TeacherModel::class.java) ?: return@launch
        val newList = teach.students.filter { it.uid != st.uid }

        ref.update("students", newList).await()

        withContext(Dispatchers.Main) {
            students.removeAt(pos)
            onRemoved(pos)          // notifyItemRemoved
        }
    }
}
