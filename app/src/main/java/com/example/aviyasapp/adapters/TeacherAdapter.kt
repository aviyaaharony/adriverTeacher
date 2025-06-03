package com.example.aviyasapp.adapters

import StudentModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class TeacherAdapter(
    private val teachers          : List<TeacherModel>,
    private val student           : StudentModel,
    private val assignedTeacherUid: String?,         // null → אין מורה
    private val auth              : FirebaseAuth,
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    /** ------------ ViewHolder ------------ */
    inner class TeacherViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nameTxt : TextView = v.findViewById(R.id.student_name)
        val priceTxt: TextView = v.findViewById(R.id.teacher_location)
        val chooseBt: Button   = v.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardveiw, parent, false)
        return TeacherViewHolder(v)
    }

    override fun getItemCount() = teachers.size

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teachers[position]

        holder.nameTxt.text  = teacher.name
        holder.priceTxt.text = teacher.price.toString()

        /* --- אם כבר משויך מורה --- */
        if (assignedTeacherUid != null) {
            holder.chooseBt.text =
                if (teacher.uid == assignedTeacherUid) "נרשמת" else "כבר יש לך מורה"
            holder.chooseBt.isEnabled = false
            return
        }

        /* --- אין מורה משויך → אפשר לבחור --- */
        holder.chooseBt.setOnClickListener {
            registerToTeacher(teacher, holder)
        }
    }

    /* ------------ לוגיקת בחירה ------------ */
    private fun registerToTeacher(teacher: TeacherModel, holder: TeacherViewHolder) =
        CoroutineScope(Dispatchers.IO).launch {

            // בדיקה מתגוננת: אולי בזמן הלחיצה נוסף מורה
            val exists = Firebase.firestore.collection("teacher")
                .whereArrayContains("students", mapOf("uid" to student.uid))
                .get().await().documents.isNotEmpty()
            if (exists) return@launch

            /* 1. הוספת התלמיד למערך students */
            val newStudents = teacher.students.toMutableList().apply { add(student) }
            Firebase.firestore.collection("teacher")
                .document(teacher.uid)
                .update("students", newStudents)
                .await()

            /* 2. עדכון UI */
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    holder.itemView.context,
                    "נרשמת בהצלחה למורה ${teacher.name}",
                    Toast.LENGTH_SHORT
                ).show()
                // נועל את כל הכפתורים בריסייקלר
                (holder.itemView.parent as? RecyclerView)?.adapter?.let {
                    (it as TeacherAdapter).assignedTeacherUidInternal = teacher.uid
                    it.notifyDataSetChanged()
                }
            }
        }

    /* משתנה פנימי כדי לנעול כפתורים אחרי ההוספה */
    private var assignedTeacherUidInternal: String? = assignedTeacherUid
}
