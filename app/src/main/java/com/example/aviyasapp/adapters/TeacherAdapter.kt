package com.example.aviyasapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aviyasapp.Model.TeacherModel
import com.example.aviyasapp.R

class TeacherAdapter(
    private val teacherList: List<TeacherModel>,
    private val onRegisterClick: (TeacherModel) -> Unit // פעולה שמופעלת בלחיצה על כפתור
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    class TeacherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teacherName: TextView = itemView.findViewById(R.id.student_name)
        val teacherPrice: TextView = itemView.findViewById(R.id.teacher_location)
        val registerButton: Button = itemView.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardveiw, parent, false)
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teacherList[position]
        holder.teacherName.text = teacher.name.toString()
        holder.teacherPrice.text = teacher.price.toString()
        holder.registerButton.setOnClickListener {
            onRegisterClick(teacher)
        }
    }

    override fun getItemCount(): Int = teacherList.size
}