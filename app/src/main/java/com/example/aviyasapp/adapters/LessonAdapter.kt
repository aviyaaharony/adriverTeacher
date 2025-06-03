package com.example.aviyasapp.adapters

import android.content.ContentValues.TAG
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
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LessonAdapter(
    private val lessonList: MutableList<LessonModel>, // Changed to MutableList
    private val auth: FirebaseAuth
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.student_name)
        val hour: TextView = itemView.findViewById(R.id.hour)
        val removeButton: Button = itemView.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lesson_card, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessonList[position]
        holder.studentName.text = lesson.student?.name ?: "Unknown Student"
        holder.hour.text = lesson.hour.toString()

        holder.removeButton.setOnClickListener {
            // Disable button to prevent multiple clicks
            holder.removeButton.isEnabled = false
            updateTeacher(lesson, position, holder)
        }
    }

    private val teacherCollectionRef = Firebase.firestore.collection("teacher")

    override fun getItemCount(): Int = lessonList.size

    private fun updateTeacher(lesson: LessonModel, position: Int, holder: LessonViewHolder) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val teacher = retrieveTeachers(currentUser.uid)
                    if (teacher != null) {
                        // Remove lesson from teacher's lessons list
                        val updatedLessons = teacher.lessons.toMutableList()
                        val lessonToRemove = updatedLessons.find {
                            it.student?.name == lesson.student?.name &&
                                    it.hour == lesson.hour
                        }

                        if (lessonToRemove != null) {
                            updatedLessons.remove(lessonToRemove)
                            teacher.lessons = updatedLessons

                            Log.d(TAG, "Updating teacher: $teacher")

                            // Update Firestore
                            teacherCollectionRef.document(currentUser.uid)
                                .set(teacher, SetOptions.merge())
                                .await()

                            // Update UI on main thread
                            withContext(Dispatchers.Main) {
                                // Remove from local list and notify adapter
                                lessonList.removeAt(position)
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, lessonList.size)

                                // Show success message
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Lesson removed successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                holder.removeButton.isEnabled = true
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Lesson not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            holder.removeButton.isEnabled = true
                            Toast.makeText(
                                holder.itemView.context,
                                "Teacher not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        holder.removeButton.isEnabled = true
                        Toast.makeText(
                            holder.itemView.context,
                            "User not authenticated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    holder.removeButton.isEnabled = true
                    Log.e(TAG, "Error removing lesson: ${e.message}", e)
                    Toast.makeText(
                        holder.itemView.context,
                        "Failed to remove lesson: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    private suspend fun retrieveTeachers(teacherUid: String): TeacherModel? {
        return try {
            Log.d(TAG, "Retrieving teacher with UID: $teacherUid")
            val querySnapshot = teacherCollectionRef.document(teacherUid).get().await()
            val teacher = querySnapshot.toObject<TeacherModel>()
            Log.d(TAG, "Retrieved teacher: $teacher")
            teacher
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving teacher: ${e.message}", e)
            null
        }
    }
}